package com.guiying.girls.data.source;


import com.guiying.common.http.HttpClient;
import com.guiying.common.http.OnResultListener;
import com.guiying.girls.Constants;
import com.guiying.girls.data.GirlsDataSource;
import com.guiying.girls.data.parser.GirlsParser;


public class RemoteGirlsDataSource implements GirlsDataSource {

    @Override
    public void getGirls(int page, int size, final LoadGirlsCallback callback) {
        HttpClient client = new HttpClient.Builder()
                .baseUrl(Constants.GAN_HUO_API)
                .url("api/data")
                .params("type", "福利")
                .params("count", String.valueOf(page))
                .params("page", String.valueOf(size))
                .build();
        client.post(new OnResultListener<GirlsParser>() {

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

    @Override
    public void getGirl(final LoadGirlsCallback callback) {
        getGirls(1, 1, callback);
    }

}
