package com.guiying.module.news.data.bean;

import java.util.List;

/**
 * <p>类说明</p>
 *
 * @author 张华洋 2017/4/20 23:04
 * @version V1.2.0
 * @name StoryList
 */
public class StoryList {

    private String date;
    private List<Story> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
