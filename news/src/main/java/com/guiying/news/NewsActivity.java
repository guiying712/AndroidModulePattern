package com.guiying.news;

import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.guiying.common.base.BaseActivity;

@Router("news")
public class NewsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    }
}
