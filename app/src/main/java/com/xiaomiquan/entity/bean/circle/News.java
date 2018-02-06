package com.xiaomiquan.entity.bean.circle;

/**
 * Created by Andy on 2018/2/5.
 */

public class News {


    /**
     * id : 1
     * content : null
     * level : 5
     * goodNum : 0
     * badNum : 0
     * link : 123
     * from : 343434
     * createTime : 2018-01-31 12:15:31
     */

    private String id;
    private String content;
    private String level;
    private Integer goodNum;
    private Integer badNum;
    private String link;
    private String from;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public Integer getBadNum() {
        return badNum;
    }

    public void setBadNum(Integer badNum) {
        this.badNum = badNum;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
