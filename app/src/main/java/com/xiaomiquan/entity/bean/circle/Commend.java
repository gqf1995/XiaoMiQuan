package com.xiaomiquan.entity.bean.circle;

/**
 * Created by Andy on 2018/1/21.
 */

public class Commend {

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

    private int id;
    private Object topicId;
    private Object commentUserId;
    private String content;
    private Object reUserId;
    private Object images;
    private long createTime;
    private Object updateTime;
    private String nickName;
    private Object reUserNickName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getTopicId() {
        return topicId;
    }

    public void setTopicId(Object topicId) {
        this.topicId = topicId;
    }

    public Object getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(Object commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getReUserId() {
        return reUserId;
    }

    public void setReUserId(Object reUserId) {
        this.reUserId = reUserId;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Object getReUserNickName() {
        return reUserNickName;
    }

    public void setReUserNickName(Object reUserNickName) {
        this.reUserNickName = reUserNickName;
    }
}
