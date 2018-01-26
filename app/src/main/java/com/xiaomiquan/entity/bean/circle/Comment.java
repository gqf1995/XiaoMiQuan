package com.xiaomiquan.entity.bean.circle;

/**
 * Created by Andy on 2018/1/21.
 */

public class Comment {

    /**
     * id : 1
     * topicId : null
     * commentUserId : null
     * content : sdfsdf
     * reUserId : null
     * images : null
     * createTime : 1516511597000
     * updateTime : null
     * nickName : 阿拉斯加
     * reUserNickName : null
     */

    private String id;
    private String topicId;
    private String commentUserId;
    private String content;
    private String reUserId;
    private String images;
    private String createTime;
    private String updateTime;
    private String nickName;
    private String reUserNickName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReUserId() {
        return reUserId;
    }

    public void setReUserId(String reUserId) {
        this.reUserId = reUserId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getReUserNickName() {
        return reUserNickName;
    }

    public void setReUserNickName(String reUserNickName) {
        this.reUserNickName = reUserNickName;
    }
}
