package com.xiaomiquan.entity.bean.circle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andy on 2018/1/21.
 */

public class Comment implements Parcelable {


    /**
     * avatar : https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1121475478,2545730346&fm=27&gp=0.jpg
     * commentUserId : 3
     * content : 民工
     * createTime : 1517825768000
     * createTimeStr : 昨天 18:16
     * groupOwner : false
     * id : 54
     * images :
     * linkId : 80
     * nickName : 成龙
     * reUserId : 0
     * reUserNickName :
     * reply : false
     * updateTime : null
     */

    private String avatar;
    private String commentUserId;
    private String content;
    private String createTime;
    private String createTimeStr;
    private boolean groupOwner;
    private String id;
    private String images;
    private String linkId;
    private String nickName;
    private String reUserId;
    private String reUserNickName;
    private boolean reply;
    private String updateTime;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public boolean isGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(boolean groupOwner) {
        this.groupOwner = groupOwner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getReUserId() {
        return reUserId;
    }

    public void setReUserId(String reUserId) {
        this.reUserId = reUserId;
    }

    public String getReUserNickName() {
        return reUserNickName;
    }

    public void setReUserNickName(String reUserNickName) {
        this.reUserNickName = reUserNickName;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeString(this.commentUserId);
        dest.writeString(this.content);
        dest.writeString(this.createTime);
        dest.writeString(this.createTimeStr);
        dest.writeByte(this.groupOwner ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.images);
        dest.writeString(this.linkId);
        dest.writeString(this.nickName);
        dest.writeString(this.reUserId);
        dest.writeString(this.reUserNickName);
        dest.writeByte(this.reply ? (byte) 1 : (byte) 0);
        dest.writeString(this.updateTime);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.avatar = in.readString();
        this.commentUserId = in.readString();
        this.content = in.readString();
        this.createTime = in.readString();
        this.createTimeStr = in.readString();
        this.groupOwner = in.readByte() != 0;
        this.id = in.readString();
        this.images = in.readString();
        this.linkId = in.readString();
        this.nickName = in.readString();
        this.reUserId = in.readString();
        this.reUserNickName = in.readString();
        this.reply = in.readByte() != 0;
        this.updateTime = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
