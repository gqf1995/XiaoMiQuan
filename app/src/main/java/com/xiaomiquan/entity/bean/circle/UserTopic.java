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

    private int id;
    private int groupId;
    private int userId;
    private Object images;
    private int top;
    private Object type;
    private int status;
    private long createTime;
    private String createTimeStr;
    private long updateTime;
    private String content;
    private String nickName;
    private String avatar;
    private int praiseQty;
    private int badEggQty;
    private int commentQty;
    private Object groupName;
    private Object praiseStr;
    private boolean showImage;
    private Object imageList;
    private boolean userPraise;

    public String getCommentList() {
        return commentList;
    }

    public void setCommentList(String commentList) {
        this.commentList = commentList;
    }

    private String commentList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
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

    public int getPraiseQty() {
        return praiseQty;
    }

    public void setPraiseQty(int praiseQty) {
        this.praiseQty = praiseQty;
    }

    public int getBadEggQty() {
        return badEggQty;
    }

    public void setBadEggQty(int badEggQty) {
        this.badEggQty = badEggQty;
    }

    public int getCommentQty() {
        return commentQty;
    }

    public void setCommentQty(int commentQty) {
        this.commentQty = commentQty;
    }

    public Object getGroupName() {
        return groupName;
    }

    public void setGroupName(Object groupName) {
        this.groupName = groupName;
    }

    public Object getPraiseStr() {
        return praiseStr;
    }

    public void setPraiseStr(Object praiseStr) {
        this.praiseStr = praiseStr;
    }

    public boolean isShowImage() {
        return showImage;
    }

    public void setShowImage(boolean showImage) {
        this.showImage = showImage;
    }

    public Object getImageList() {
        return imageList;
    }

    public void setImageList(Object imageList) {
        this.imageList = imageList;
    }

    public boolean isUserPraise() {
        return userPraise;
    }

    public void setUserPraise(boolean userPraise) {
        this.userPraise = userPraise;
    }


}
