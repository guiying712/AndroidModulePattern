package com.guiying.girls.data;

import com.guiying.girls.data.parser.GirlsParser;

public interface GirlsDataSource {

    interface LoadGirlsCallback {

        void onGirlsLoaded(GirlsParser girlsParser);

        void onDataNotAvailable();
    }

    void getGirls(int page, int size, LoadGirlsCallback callback);

    void getGirl(LoadGirlsCallback callback);
}
