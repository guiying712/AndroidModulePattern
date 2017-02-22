package com.guiying.girls;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.github.mzule.activityrouter.annotation.Router;
import com.guiying.common.base.BaseActivity;

@Router("girls")
public class GirlsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girls);

        //标题栏设置
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.girls_activity_title);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
