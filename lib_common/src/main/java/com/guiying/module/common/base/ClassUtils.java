package com.guiying.module.common.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Keep;
import android.util.Log;

import com.guiying.module.common.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dalvik.system.DexFile;

/**
 * Copy from galaxy sdk ${com.alibaba.android.galaxy.utils.ClassUtils}
 * Scanner, find out class with any conditions, copy from google source code.
 */
@Keep
public class ClassUtils {
    private static final String TAG = "ClassUtils";

    private static final String EXTRACTED_NAME_EXT = ".classes";
    private static final String EXTRACTED_SUFFIX = ".zip";

    private static final String SECONDARY_FOLDER_NAME = "code_cache" + File.separator + "secondary-dexes";

    private static final String PREFS_FILE = "multidex.version";
    private static final String KEY_DEX_NUMBER = "dex.number";

    private static final int VM_WITH_MULTIDEX_VERSION_MAJOR = 2;
    private static final int VM_WITH_MULTIDEX_VERSION_MINOR = 1;

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences(PREFS_FILE, Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB ? Context.MODE_PRIVATE : Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
    }


    /**
     * 获取单一路径下所有实现了接口的类对象
     *
     * @param context U know
     * @param clazz   接口
     * @param path    包路径
     * @param <T>     U know
     * @return 对象列表
     */
    public static <T> List<T>
    getObjectsWithInterface(Context context, Class<T> clazz, String path) {
        List<T> objectList = new ArrayList<>();
        try {
            //找出所有路径中的类名，主要用于各个组件根包名一致的情况
            List<String> classFileNames = getFileNameByPackageName(context, path);

            for (String className : classFileNames) {
                Class aClass = Class.forName(className);
                if (clazz.isAssignableFrom(aClass) && !clazz.equals(aClass) && !aClass.isInterface()) {
                    objectList.add((T) Class.forName(className).getConstructor().newInstance());
                }
            }

            if (objectList.size() == 0) {
                Log.e(TAG, "No files were found, check your configuration please!");
            }
        } catch (Exception e) {
            e.getStackTrace();
            Log.e(TAG, "getObjectsWithInterface error, " + e.getMessage());
        }

        return objectList;
    }


    /**
     * 获取多路径下所有实现了接口的类对象
     *
     * @param context  U know
     * @param clazz    接口
     * @param pathList 包路径列表
     * @param <T>      U know
     * @return 对象列表
     */
    public static <T> List<T> getObjectsWithInterface(Context context, Class<T> clazz, List<String> pathList) {
        List<T> objectList = new ArrayList<>();
        try {
            for (String path : pathList) {
                //找出所有路径中的类名，主要用于各个组件根包名不一致的情况
                List<String> classFileNames = getFileNameByPackageName(context, path);

                for (String className : classFileNames) {
                    Class aClass = Class.forName(className);
                    if (clazz.isAssignableFrom(aClass) && !clazz.equals(aClass) && !aClass.isInterface()) {
                        objectList.add((T) Class.forName(className).getConstructor().newInstance());
                    }
                }
            }

            if (objectList.size() == 0) {
                Log.e(TAG, "No files were found, check your configuration please!");
            }
        } catch (Exception e) {
            e.getStackTrace();
            Log.e(TAG, "getObjectsWithInterface error, " + e.getMessage());
        }

        return objectList;
    }


    /**
     * 通过指定包名，扫描包下面包含的所有的ClassName
     *
     * @param context     U know
     * @param packageName 包名
     * @return 所有class的集合
     */
    public static List<String> getFileNameByPackageName(Context context, String packageName) throws PackageManager.NameNotFoundException, IOException {
        List<String> classNames = new ArrayList<>();
        for (String path : getSourcePaths(context)) {
            DexFile dexfile = null;

            try {
                if (path.endsWith(EXTRACTED_SUFFIX)) {
                    //NOT use new DexFile(path), because it will throw "permission error in /data/dalvik-cache"
                    dexfile = DexFile.loadDex(path, path + ".tmp", 0);
                } else {
                    dexfile = new DexFile(path);
                }
                Enumeration<String> dexEntries = dexfile.entries();
                while (dexEntries.hasMoreElements()) {
                    String className = dexEntries.nextElement();
                    if (className.contains(packageName)) {
                        classNames.add(className);
                    }
                }
            } catch (Throwable ignore) {
                Log.e(TAG, "Scan map file in dex files made error.", ignore);
            } finally {
                if (null != dexfile) {
                    try {
                        dexfile.close();
                    } catch (Throwable ignore) {
                    }
                }
            }
        }

        Log.d(TAG, "Filter " + classNames.size() + " classes by packageName <" + packageName + ">");
        return classNames;
    }

    /**
     * get all the dex path
     *
     * @param context the application context
     * @return all the dex path
     * @throws PackageManager.NameNotFoundException Exception
     * @throws IOException                          Exception
     */
    public static List<String> getSourcePaths(Context context) throws PackageManager.NameNotFoundException, IOException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        File sourceApk = new File(applicationInfo.sourceDir);

        List<String> sourcePaths = new ArrayList<>();
        sourcePaths.add(applicationInfo.sourceDir); //add the default apk path

        //the prefix of extracted file, ie: test.classes
        String extractedFilePrefix = sourceApk.getName() + EXTRACTED_NAME_EXT;

        //如果VM已经支持了MultiDex，就不要去Secondary Folder加载 Classesx.zip了，那里已经么有了
        //通过是否存在sp中的multidex.version是不准确的，因为从低版本升级上来的用户，是包含这个sp配置的
        if (!isVMMultidexCapable()) {
            //the total dex numbers
            int totalDexNumber = getMultiDexPreferences(context).getInt(KEY_DEX_NUMBER, 1);
            File dexDir = new File(applicationInfo.dataDir, SECONDARY_FOLDER_NAME);

            for (int secondaryNumber = 2; secondaryNumber <= totalDexNumber; secondaryNumber++) {
                //for each dex file, ie: test.classes2.zip, test.classes3.zip...
                String fileName = extractedFilePrefix + secondaryNumber + EXTRACTED_SUFFIX;
                File extractedFile = new File(dexDir, fileName);
                if (extractedFile.isFile()) {
                    sourcePaths.add(extractedFile.getAbsolutePath());
                    //we ignore the verify zip part
                } else {
                    throw new IOException("Missing extracted secondary dex file '" + extractedFile.getPath() + "'");
                }
            }
        }

        if (Utils.isAppDebug()) {
            // Search instant run support only debuggable
            sourcePaths.addAll(tryLoadInstantRunDexFile(applicationInfo));
        }
        return sourcePaths;
    }

    /**
     * Get instant run dex path, used to catch the branch usingApkSplits=false.
     */
    private static List<String> tryLoadInstantRunDexFile(ApplicationInfo applicationInfo) {
        List<String> instantRunSourcePaths = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && null != applicationInfo.splitSourceDirs) {
            // add the splite apk, normally for InstantRun, and newest version.
            instantRunSourcePaths.addAll(Arrays.asList(applicationInfo.splitSourceDirs));
            Log.d(TAG, "Found InstantRun support");
        } else {
            try {
                // This man is reflection from Google instant run sdk, he will tell me where the dex files go.
                Class pathsByInstantRun = Class.forName("com.android.tools.fd.runtime.Paths");
                Method getDexFileDirectory = pathsByInstantRun.getMethod("getDexFileDirectory", String.class);
                String instantRunDexPath = (String) getDexFileDirectory.invoke(null, applicationInfo.packageName);

                File instantRunFilePath = new File(instantRunDexPath);
                if (instantRunFilePath.exists() && instantRunFilePath.isDirectory()) {
                    File[] dexFile = instantRunFilePath.listFiles();
                    for (File file : dexFile) {
                        if (null != file && file.exists() && file.isFile() && file.getName().endsWith(".dex")) {
                            instantRunSourcePaths.add(file.getAbsolutePath());
                        }
                    }
                    Log.d(TAG, "Found InstantRun support");
                }

            } catch (Exception e) {
                Log.e(TAG, "InstantRun support error, " + e.getMessage());
            }
        }

        return instantRunSourcePaths;
    }

    /**
     * Identifies if the current VM has a native support for multidex, meaning there is no need for
     * additional installation by this library.
     *
     * @return true if the VM handles multidex
     */
    private static boolean isVMMultidexCapable() {
        boolean isMultidexCapable = false;
        String vmName = null;

        try {
            if (isYunOS()) {    // YunOS需要特殊判断
                vmName = "'YunOS'";
                isMultidexCapable = Integer.valueOf(System.getProperty("ro.build.version.sdk")) >= 21;
            } else {    // 非YunOS原生Android
                vmName = "'Android'";
                String versionString = System.getProperty("java.vm.version");
                if (versionString != null) {
                    Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
                    if (matcher.matches()) {
                        try {
                            int major = Integer.parseInt(matcher.group(1));
                            int minor = Integer.parseInt(matcher.group(2));
                            isMultidexCapable = (major > VM_WITH_MULTIDEX_VERSION_MAJOR)
                                    || ((major == VM_WITH_MULTIDEX_VERSION_MAJOR)
                                    && (minor >= VM_WITH_MULTIDEX_VERSION_MINOR));
                        } catch (NumberFormatException ignore) {
                            // let isMultidexCapable be false
                        }
                    }
                }
            }
        } catch (Exception ignore) {

        }

        Log.i(TAG, "VM with name " + vmName + (isMultidexCapable ? " has multidex support" : " does not have multidex support"));
        return isMultidexCapable;
    }

    /**
     * 判断系统是否为YunOS系统
     */
    private static boolean isYunOS() {
        try {
            String version = System.getProperty("ro.yunos.version");
            String vmName = System.getProperty("java.vm.name");
            return (vmName != null && vmName.toLowerCase().contains("lemur"))
                    || (version != null && version.trim().length() > 0);
        } catch (Exception ignore) {
            return false;
        }
    }
}