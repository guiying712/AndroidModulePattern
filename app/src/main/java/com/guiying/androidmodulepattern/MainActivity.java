package com.guiying.androidmodulepattern;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.github.mzule.activityrouter.router.Routers;
import com.guiying.common.base.BaseActivity;
import com.guiying.common.base.BaseApplication;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private long exitTime = 0;

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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar.make(girlsButton, getString(R.string.app_exit_hint), Snackbar.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getIns().exitApp(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
