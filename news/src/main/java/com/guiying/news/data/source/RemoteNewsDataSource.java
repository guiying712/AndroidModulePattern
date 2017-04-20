package com.guiying.news.data.source;

import com.guiying.common.http.HttpClient;
import com.guiying.common.http.InfoCallback;
import com.guiying.common.http.OnResultListener;
import com.guiying.news.Constants;
import com.guiying.news.data.NewsDataSource;
import com.guiying.news.data.bean.StoryList;

import static com.guiying.common.http.HttpClient.OBJECT;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/4/20 23:32
 * @version V1.2.0
 * @name RemoteNewsDataSource
 */
public class RemoteNewsDataSource implements NewsDataSource {

    @Override
    public void getNewsList(String date, final InfoCallback<StoryList> callback) {
        HttpClient client = new HttpClient.Builder()
                .baseUrl(Constants.ZHIHU_DAILY_BEFORE_MESSAGE)
                .url(date)
                .bodyType(OBJECT, StoryList.class)
                .build();
        client.get(new OnResultListener<StoryList>() {

            @Override
            public void onSuccess(StoryList result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(int code, String message) {
                callback.onError(code, message);
            }

            @Override
            public void onFailure(String message) {
                callback.onError(0, message);
            }
        });
    }
}
