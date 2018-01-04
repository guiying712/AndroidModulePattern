package com.guiying.module.common.base;


import android.support.annotation.Keep;

/**
 * <p>View接口的基类</p>
 *
 * @author 张华洋
 * @name BaseView
 */
@Keep
public interface BaseView<T> {

    void setPresenter(T presenter);

}
