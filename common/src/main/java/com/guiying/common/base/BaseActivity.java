package com.guiying.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * <p>Activity基类 </p>
 *
 * @author 2016/12/2 17:33
 * @version V1.0.0
 * @name BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 处理Intent，防止开发人员没做Intent判空
     */
    protected void handleIntent(Intent intent) {
    }

    /**
     * 封装的findViewByID方法
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getIns().addActivity(this);
        //强制在基类Intent判空
        if (null != getIntent()) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getIns().finishActivity(this);
    }

}
