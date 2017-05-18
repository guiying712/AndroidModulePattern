package com.guiying.news.data;

import com.guiying.common.http.InfoCallback;
import com.guiying.news.data.bean.StoryList;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/4/20 22:02
 * @version V1.2.0
 * @name NewsDataSource
 */
public interface NewsDataSource {


    /**
     * 获取
     *
     * @param date
     * @param callback 回调
     */
    void getNewsList(String date, InfoCallback<StoryList> callback);


}

