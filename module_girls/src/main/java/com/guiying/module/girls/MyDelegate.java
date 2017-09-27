package com.guiying.module.girls;

import com.guiying.module.common.base.ApplicationDelegate;
import com.guiying.module.common.base.ViewManager;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/9/20 22:29
 * @version V2.8.3
 * @name MyDelegate
 */
public class MyDelegate implements ApplicationDelegate {

    @Override
    public void onCreate() {
        ViewManager.getInstance().addFragment(0, new GirlsFragment());
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
