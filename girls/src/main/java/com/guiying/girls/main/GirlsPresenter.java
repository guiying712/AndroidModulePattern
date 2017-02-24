package com.guiying.girls.main;

import com.guiying.girls.data.GirlsDataSource;
import com.guiying.girls.data.parser.GirlsParser;
import com.guiying.girls.data.source.RemoteGirlsDataSource;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/2/22 20:33
 * @version V1.2.0
 * @name GirlsPresenter
 */
public class GirlsPresenter implements GirlsContract.Presenter {

    private GirlsContract.View mView;
    private GirlsDataSource mDataSource;

    public GirlsPresenter(GirlsContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mDataSource = new RemoteGirlsDataSource();
    }

    @Override
    public void getGirls(int size, int page, final boolean isRefresh) {
        mDataSource.getGirls(size, page, new GirlsDataSource.LoadGirlsCallback() {
            @Override
            public void onGirlsLoaded(GirlsParser girlsParser) {
                if (isRefresh) {
                    mView.refresh(girlsParser.getResults());
                } else {
                    mView.load(girlsParser.getResults());
                }
                mView.showNormal();
            }

            @Override
            public void onDataNotAvailable() {
                if (isRefresh) {
                    mView.showError();
                }
            }
        });
    }

    @Override
    public void start() {
        getGirls(10, 1, true);
    }
}
