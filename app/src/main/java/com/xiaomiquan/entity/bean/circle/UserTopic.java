package com.xiaomiquan.entity.bean.circle;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Andy on 2018/1/21.
 */

public class UserTopic implements Parcelable {

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
    private List<Comment> commentList;


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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.groupId);
        dest.writeString(this.userId);
        dest.writeString(this.images);
        dest.writeString(this.top);
        dest.writeString(this.type);
        dest.writeString(this.status);
        dest.writeString(this.createTime);
        dest.writeString(this.createTimeStr);
        dest.writeString(this.updateTime);
        dest.writeString(this.content);
        dest.writeString(this.nickName);
        dest.writeString(this.avatar);
        dest.writeString(this.praiseQty);
        dest.writeString(this.badEggQty);
        dest.writeString(this.commentQty);
        dest.writeString(this.groupName);
        dest.writeString(this.praiseStr);
        dest.writeString(this.showImage);
        dest.writeString(this.imageList);
        dest.writeString(this.userPraise);
        dest.writeTypedList(this.commentList);
    }

    public UserTopic() {
    }

    protected UserTopic(Parcel in) {
        this.id = in.readString();
        this.groupId = in.readString();
        this.userId = in.readString();
        this.images = in.readString();
        this.top = in.readString();
        this.type = in.readString();
        this.status = in.readString();
        this.createTime = in.readString();
        this.createTimeStr = in.readString();
        this.updateTime = in.readString();
        this.content = in.readString();
        this.nickName = in.readString();
        this.avatar = in.readString();
        this.praiseQty = in.readString();
        this.badEggQty = in.readString();
        this.commentQty = in.readString();
        this.groupName = in.readString();
        this.praiseStr = in.readString();
        this.showImage = in.readString();
        this.imageList = in.readString();
        this.userPraise = in.readString();
        this.commentList = in.createTypedArrayList(Comment.CREATOR);
    }

    public static final Parcelable.Creator<UserTopic> CREATOR = new Parcelable.Creator<UserTopic>() {
        @Override
        public UserTopic createFromParcel(Parcel source) {
            return new UserTopic(source);
        }

        @Override
        public UserTopic[] newArray(int size) {
            return new UserTopic[size];
        }
    };
}
