package com.guiying.module.girls.main;

import com.guiying.module.common.base.BasePresenter;
import com.guiying.module.common.base.BaseView;
import com.guiying.module.girls.data.bean.Girls;

import java.util.List;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/2/22 20:33
 * @version V1.2.0
 * @name GirlsContract
 */
public interface GirlsContract {

    interface View extends BaseView<Presenter> {

        /**
         * View 的存活状态
         *
         * @return true or false
         */
        boolean isActive();

        void refresh(List<Girls> data);

        void load(List<Girls> data);

        void showError();

        void showNormal();

    }

    interface Presenter extends BasePresenter {

        void getGirls(int page, int size, boolean isRefresh);

    }


}
