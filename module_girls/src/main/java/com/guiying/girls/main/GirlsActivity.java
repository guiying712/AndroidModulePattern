package com.guiying.girls.main;

import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.guiying.common.base.BaseActionBarActivity;
import com.guiying.girls.R;

@Router("girls")
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
