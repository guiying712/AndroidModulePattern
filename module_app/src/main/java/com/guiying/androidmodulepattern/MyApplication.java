package com.guiying.androidmodulepattern;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.github.mzule.activityrouter.annotation.Modules;
import com.guiying.common.base.BaseApplication;

/**
 * <p>这里是整个组件化项目管理各个组件的地方，所有需要使用的组件必须在此声明</p>
 *
 * @author 张华洋 2017/2/15 20:14
 * @version V1.2.0
 * @name MyApplication
 */
@Modules({"app", "main", "girls", "news"})
public class MyApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }
}
