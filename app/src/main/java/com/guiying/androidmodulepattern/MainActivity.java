package com.guiying.androidmodulepattern;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mzule.activityrouter.router.Routers;
import com.guiying.common.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    protected Button newsButton;
    protected Button girlsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsButton = (Button) findViewById(R.id.news_button);
        newsButton.setOnClickListener(MainActivity.this);
        girlsButton = (Button) findViewById(R.id.girls_button);
        girlsButton.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.news_button) {
            Routers.open(MainActivity.this, "module://news");
        } else if (view.getId() == R.id.girls_button) {
            Routers.open(MainActivity.this, "module://girls");
        }
    }

}
