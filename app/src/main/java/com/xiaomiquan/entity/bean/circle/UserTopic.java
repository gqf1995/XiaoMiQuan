package com.xiaomiquan.entity.bean.circle;

import java.util.List;

/**
 * Created by Andy on 2018/1/21.
 */

public class UserTopic {

    /**
     * id : 1
     * groupId : 1
     * userId : 1
     * images : null
     * top : 0
     * type : null
     * status : 1
     * createTime : 1516450019000
     * createTimeStr : 昨天 20:06
     * updateTime : 1516450019000
     * content : hahhah
     * nickName : hyk
     * avatar : sdasdasdasd
     * praiseQty : 0
     * badEggQty : 0
     * commentQty : 0
     * groupName : null
     * praiseStr : null
     * commentList : []
     * showImage : false
     * imageList : null
     * userPraise : false
     */

    private String id;
    private String groupId;
    private String userId;
    private String images;
    private String top;
    private String type;
    private String status;
    private String createTime;
    private String createTimeStr;
    private String updateTime;
    private String content;
    private String nickName;
    private String avatar;
    private String praiseQty;
    private String badEggQty;
    private String commentQty;
    private String groupName;
    private String praiseStr;
    private String showImage;
    private String imageList;
    private String userPraise;
    private List<Commend> commentList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
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

    public String getPraiseQty() {
        return praiseQty;
    }

    public void setPraiseQty(String praiseQty) {
        this.praiseQty = praiseQty;
    }

    public String getBadEggQty() {
        return badEggQty;
    }

    public void setBadEggQty(String badEggQty) {
        this.badEggQty = badEggQty;
    }

    public String getCommentQty() {
        return commentQty;
    }

    public void setCommentQty(String commentQty) {
        this.commentQty = commentQty;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPraiseStr() {
        return praiseStr;
    }

    public void setPraiseStr(String praiseStr) {
        this.praiseStr = praiseStr;
    }

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }

    public String getUserPraise() {
        return userPraise;
    }

    public void setUserPraise(String userPraise) {
        this.userPraise = userPraise;
    }

    public List<Commend> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Commend> commentList) {
        this.commentList = commentList;
    }
}
