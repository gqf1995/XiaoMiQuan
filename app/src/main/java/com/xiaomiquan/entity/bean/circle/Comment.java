package com.xiaomiquan.entity.bean.circle;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Andy on 2018/1/21.
 */

public class Comment implements Parcelable {


    /**
     * commentUserId : 3
     * content : 啦啦啦
     * createTime : 1517313414000
     * groupOwner : false
     * id : 11
     * images :
     * linkId : 14
     * nickName : 成龙
     * reUserId : 0
     * reUserNickName :
     * reply : false
     * updateTime : null
     */

    private String commentUserId;
    private String content;
    private String createTime;
    private String groupOwner;
    private String id;
    private String images;
    private String linkId;
    private String nickName;
    private String reUserId;
    private String reUserNickName;
    private boolean reply;
    private String updateTime;

    protected Comment(Parcel in) {
        commentUserId = in.readString();
        content = in.readString();
        createTime = in.readString();
        groupOwner = in.readString();
        id = in.readString();
        images = in.readString();
        linkId = in.readString();
        nickName = in.readString();
        reUserId = in.readString();
        reUserNickName = in.readString();
        reply = in.readByte() != 0;
        updateTime = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(commentUserId);
        parcel.writeString(content);
        parcel.writeString(createTime);
        parcel.writeString(groupOwner);
        parcel.writeString(id);
        parcel.writeString(images);
        parcel.writeString(linkId);
        parcel.writeString(nickName);
        parcel.writeString(reUserId);
        parcel.writeString(reUserNickName);
        parcel.writeByte((byte) (reply ? 1 : 0));
        parcel.writeString(updateTime);
    }
}
