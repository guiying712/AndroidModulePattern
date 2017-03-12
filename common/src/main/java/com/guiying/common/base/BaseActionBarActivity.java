/*
 *  @ProjectName: ISMS_Petrel_MCU
 *  @Copyright: 2017 HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
 *  @address: http://www.hikvision.com
 *  @Description: 本内容仅限于杭州海康威视系统技术公有限司内部使用，禁止转发.
 */

package com.guiying.common.base;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;

/**
 * BaseActionBarActivity继承于BaseActivity，封装了actionBar的逻辑；
 * 继承于ActionBarBaseActivity的Activity都将默认带有ActionBar，并且只能使用AppTheme主题；
 * 只有那些ActionBar只带有Title和返回按钮的Activity方可继承
 *
 * @author 张华洋 2017/3/7 18:36
 * @version V1.2.0
 * @name BaseActionBarActivity
 */
public abstract class BaseActionBarActivity extends BaseActivity {

    protected abstract
    @StringRes
    int setTitleId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //标题栏设置
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(setTitleId());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
