package com.guiying.girls;

import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.guiying.common.base.BaseActivity;

@Router("girls")
public class GirlsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girls);
    }
}
