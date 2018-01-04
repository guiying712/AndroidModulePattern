package com.guiying.module.common.base;

import android.support.annotation.Keep;

/**
 * <p>数据回调接口</p>
 *
 * @author 张华洋 2017/3/22 13:36
 * @version V1.2.0
 * @name InfoCallback
 */
@Keep
public interface InfoCallback<T> {

    void onSuccess(T info);

    void onError(int code, String message);

}
