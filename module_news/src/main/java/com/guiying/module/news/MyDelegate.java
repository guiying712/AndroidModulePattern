package com.guiying.module.news;

import android.support.annotation.Keep;

import com.guiying.module.common.base.IApplicationDelegate;
import com.guiying.module.common.base.ViewManager;
import com.orhanobut.logger.Logger;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/9/20 22:29
 * @version V2.8.3
 * @name MyDelegate
 */
@Keep
public class MyDelegate implements IApplicationDelegate {

    @Override
    public void onCreate() {
        Logger.init("pattern");
        //主动添加
        ViewManager.getInstance().addFragment(0, NewsFragment.newInstance());
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
