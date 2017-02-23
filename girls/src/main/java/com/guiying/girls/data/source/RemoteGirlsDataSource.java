package com.guiying.girls.data.source;


import com.guiying.common.http.HttpClient;
import com.guiying.common.http.OnResultListener;
import com.guiying.girls.Constants;
import com.guiying.girls.data.GirlsDataSource;
import com.guiying.girls.data.parser.GirlsParser;

import static com.guiying.common.http.HttpClient.OBJECT;


public class RemoteGirlsDataSource implements GirlsDataSource {

    @Override
    public void getGirls(int size, int page, final LoadGirlsCallback callback) {
        HttpClient client = new HttpClient.Builder()
                .baseUrl(Constants.GAN_HUO_API)
                .url("福利/" + size + "/" + page)
                .bodyType(OBJECT, GirlsParser.class)
                .build();
        client.get(new OnResultListener<GirlsParser>() {

            @Override
            public void onSuccess(GirlsParser result) {
                callback.onGirlsLoaded(result);
            }

            @Override
            public void onError(int code, String message) {
                callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(String message) {
                callback.onDataNotAvailable();
            }
        });
    }

}
