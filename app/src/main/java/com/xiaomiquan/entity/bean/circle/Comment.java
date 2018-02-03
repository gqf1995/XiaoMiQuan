package com.xiaomiquan.entity.bean.circle;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andy on 2018/1/21.
 */

public class Comment implements Parcelable {


    /**
     * commentUserId : 3
     * content : 评论内容
     * createTime : 1517464604000
     * createTimeStr : 昨天 13:56
     * groupOwner : true
     * id : 13
     * images :
     * linkId : 1
     * nickName : 成龙
     * reUserId : 0
     * reUserNickName :
     * reply : false
     * updateTime : null
     */

    private String commentUserId;
    private String content;
    private String createTime;
    private String createTimeStr;
    private String groupOwner;
    private String id;
    private String images;
    private String linkId;
    private String nickName;
    private String reUserId;
    private String reUserNickName;
    private boolean reply;
    private String updateTime;

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

    public String getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(String groupOwner) {
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
        dest.writeString(this.commentUserId);
        dest.writeString(this.content);
        dest.writeString(this.createTime);
        dest.writeString(this.createTimeStr);
        dest.writeString(this.groupOwner);
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
        this.commentUserId = in.readString();
        this.content = in.readString();
        this.createTime = in.readString();
        this.createTimeStr = in.readString();
        this.groupOwner = in.readString();
        this.id = in.readString();
        this.images = in.readString();
        this.linkId = in.readString();
        this.nickName = in.readString();
        this.reUserId = in.readString();
        this.reUserNickName = in.readString();
        this.reply = in.readByte() != 0;
        this.updateTime = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
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
