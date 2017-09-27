package com.guiying.module.girls.data.bean;

import java.util.List;

public class GirlsParser {

    /**
     * error : false
     * results : [{"_id":"5771d5eb421aa931ddcc50d6","createdAt":"2016-06-28T09:42:03.761Z","desc":"Dagger2图文完全教程","publishedAt":"2016-06-28T11:33:25.276Z","source":"web","type":"Android","url":"https://github.com/luxiaoming/dagger2Demo","used":true,"who":"代码GG陆晓明"},{"_id":"5771c9ca421aa931ca5a7e59","createdAt":"2016-06-28T08:50:18.731Z","desc":"Android Design 设计模板","publishedAt":"2016-06-28T11:33:25.276Z","source":"chrome","type":"Android","url":"https://github.com/andreasschrade/android-design-template","used":true,"who":"代码家"}]
     */

    private boolean error;
    /**
     * _id : 5771d5eb421aa931ddcc50d6
     * createdAt : 2016-06-28T09:42:03.761Z
     * desc : Dagger2图文完全教程
     * publishedAt : 2016-06-28T11:33:25.276Z
     * source : web
     * type : Android
     * url : https://github.com/luxiaoming/dagger2Demo
     * used : true
     * who : 代码GG陆晓明
     */

    private List<Girls> results;

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(List<Girls> results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public List<Girls> getResults() {
        return results;
    }


}
