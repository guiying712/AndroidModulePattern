package com.guiying.module.girls.main;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.guiying.module.common.base.BaseActionBarActivity;
import com.guiying.module.girls.R;

@Route(path = "/girls/list")
public class GirlsActivity extends BaseActionBarActivity {

    private GirlsView mView;
    private GirlsContract.Presenter mPresenter;

    @Override
    protected int setTitleId() {
        return R.string.girls_activity_title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = new GirlsView(this);
        setContentView(mView);
        mPresenter = new GirlsPresenter(mView);
        mPresenter.start();
    }


}
