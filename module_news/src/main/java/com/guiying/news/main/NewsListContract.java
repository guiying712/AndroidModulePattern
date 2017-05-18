package com.guiying.news.main;

import com.guiying.common.base.BasePresenter;
import com.guiying.common.base.BaseView;
import com.guiying.news.data.bean.StoryList;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/2/22 20:33
 * @version V1.2.0
 * @name NewsContract
 */
public interface NewsListContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void showNewsList(StoryList info);

    }

    interface Presenter extends BasePresenter {

        /**
         * 获取最新列表
         *
         * @param date
         */
        void getNewMessages(int page, int size, String date);

    }

}
