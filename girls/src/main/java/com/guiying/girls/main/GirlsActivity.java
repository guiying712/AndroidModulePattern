package com.guiying.girls.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.github.mzule.activityrouter.annotation.Router;
import com.guiying.common.base.BaseActivity;
import com.guiying.girls.R;

@Router("girls")
public class GirlsActivity extends BaseActivity {

    private GirlsView mView;
    private GirlsContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new GirlsView(this);
        setContentView(mView);

        //标题栏设置
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.girls_activity_title);
        }

        mPresenter = new GirlsPresenter(mView);
        mPresenter.start();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
