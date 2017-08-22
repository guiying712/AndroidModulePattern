package com.guiying.main;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.guiying.common.base.BaseActivity;
import com.guiying.common.base.BaseApplication;
import com.guiying.common.utils.ToastUtils;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/7/1 13:13
 * @version V1.2.0
 * @name MainActivity
 */
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
            //跳转到NewsCenterActivity
            ARouter.getInstance().build("/news/center").navigation();
        } else if (view.getId() == R.id.girls_button) {
            //跳转到GirlsActivity
            ARouter.getInstance().build("/girls/list").navigation();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //两秒之内按返回键就会退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showShortToast(getString(R.string.app_exit_hint));
                exitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getIns().exitApp(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
