package com.guiying.module.girls.data.source;


import com.guiying.module.common.http.DataType;
import com.guiying.module.common.http.HttpClient;
import com.guiying.module.common.http.OnResultListener;
import com.guiying.module.girls.Constants;
import com.guiying.module.girls.data.GirlsDataSource;
import com.guiying.module.girls.data.bean.GirlsParser;



public class RemoteGirlsDataSource implements GirlsDataSource {

    @Override
    public void getGirls(int size, int page, final LoadGirlsCallback callback) {
        HttpClient client = new HttpClient.Builder()
                .baseUrl(Constants.GAN_HUO_API)
                .url("福利/" + size + "/" + page)
                .bodyType(DataType.JSON_OBJECT, GirlsParser.class)
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
