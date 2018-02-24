package com.xiaomiquan.entity.bean.group;

/**
 * Created by 郭青枫 on 2018/2/12 0012.
 */

public class BannerEntity {
    /**
     * id : 3
     * pictureUrl : https://img.maijia.com/news/main/201607/08103905avbl_pressImage.jpg
     * turnUrl : https://www.baidu.com
     */

    private int id;
    private String pictureUrl;
    private String turnUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTurnUrl() {
        return turnUrl;
    }

    public void setTurnUrl(String turnUrl) {
        this.turnUrl = turnUrl;
    }
}
