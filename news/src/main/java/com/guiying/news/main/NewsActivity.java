package com.guiying.news.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.github.mzule.activityrouter.annotation.Router;
import com.guiying.common.base.BaseActivity;
import com.guiying.common.http.HttpClient;
import com.guiying.common.http.OnResultListener;
import com.guiying.common.utils.ToastUtils;
import com.guiying.news.Constants;
import com.guiying.news.R;
import com.orhanobut.logger.Logger;

@Router("news")
public class NewsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //标题栏设置
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.news_activity_title);
        }
        login();
    }


    /**
     * 在这里模拟登陆，然后拿到sessionId或者Token
     * 这样就能够在组件请求接口了
     */
    private void login() {
        HttpClient client = new HttpClient.Builder()
                .baseUrl(Constants.ZHIHU_DAILY_BEFORE_MESSAGE)
                .url("20170225")
                .build();
        client.get(new OnResultListener<String>() {

            @Override
            public void onSuccess(String result) {
                ToastUtils.showLongToast(result);
            }

            @Override
            public void onError(int code, String message) {
                Logger.e(message);
            }

            @Override
            public void onFailure(String message) {
                Logger.e(message);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
