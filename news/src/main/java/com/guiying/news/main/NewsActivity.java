package com.guiying.news.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mzule.activityrouter.annotation.Router;
import com.guiying.common.base.BaseActionBarActivity;
import com.guiying.common.http.HttpClient;
import com.guiying.common.http.OnResultListener;
import com.guiying.common.utils.ToastUtils;
import com.guiying.news.Constants;
import com.guiying.news.R;
import com.orhanobut.logger.Logger;

import es.dmoral.toasty.Toasty;

@Router("news")
public class NewsActivity extends BaseActionBarActivity implements View.OnClickListener {

    protected Button mButton;

    @Override
    protected int setTitleId() {
        return R.string.news_activity_title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_news);
        login();
        initView();
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
                Toasty.info(NewsActivity.this, "Here is some info for you.", Toast.LENGTH_SHORT, true).show();
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
    public void onClick(View view) {
        if (view.getId() == R.id.button1) {
            ToastUtils.showShortToast("kannidmfdsfsf ");
        }
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(NewsActivity.this);
    }
}
