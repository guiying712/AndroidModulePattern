package com.guiying.module.news;

import android.support.annotation.Keep;
import android.view.View;

import com.guiying.module.common.base.BaseFragment;
import com.guiying.module.common.base.IViewDelegate;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2018/1/4 22:16
 * @version V2.8.3
 * @name MyViewDelegate
 */
@Keep
public class MyViewDelegate implements IViewDelegate {

    @Override
    public BaseFragment getFragment(String name) {
        return NewsFragment.newInstance();
    }

    @Override
    public View getView(String name) {
        return null;
    }
}
