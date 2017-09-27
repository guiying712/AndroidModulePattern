package com.guiying.module.news.data.bean;

/**
 * 日报新闻实体类
 */
public class Story {

    /**
     * 新闻标题
     **/
    private String title;
    /**
     * 供 Google Analytics 使用
     **/
    private String ga_prefix;
    /**
     * 图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
     **/
    private String[] images;
    /**
     * 消息是否包含多张图片（仅出现在包含多图的新闻中）
     **/
    private String multipic;
    private String type;
    /**
     * url 与 share_url 中最后的数字（应为内容的 id）
     **/
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getMultipic() {
        return multipic;
    }

    public void setMultipic(String multipic) {
        this.multipic = multipic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
