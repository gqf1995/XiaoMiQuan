package com.xiaomiquan.entity.bean;

/**
 * Created by 郭青枫 on 2018/1/21 0021.
 */

public class ArticleData {

    /**
     * id : 1
     * userId : 1
     * title : 标题
     * titleImg : wwwsdsd
     * platform : 1
     * createTime : null
     * updateTime : null
     * content : 内容
     * nickName : null
     * avatar : null
     * goodCount : 0
     * badCount : 0
     * commentCount : 0
     * commentVos : null
     */

    private String id;
    private String userId;
    private String title;
    private String titleImg;
    private String platform;
    private String createTime;
    private String updateTime;
    private String content;
    private String nickName;
    private String avatar;
    private String goodCount;
    private String badCount;
    private String commentCount;
    private String commentVos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }

    public String getBadCount() {
        return badCount;
    }

    public void setBadCount(String badCount) {
        this.badCount = badCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getCommentVos() {
        return commentVos;
    }

    public void setCommentVos(String commentVos) {
        this.commentVos = commentVos;
    }
}
