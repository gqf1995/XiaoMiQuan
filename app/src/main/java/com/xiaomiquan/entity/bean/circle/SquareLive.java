package com.xiaomiquan.entity.bean.circle;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andy on 2018/1/26.
 */

public class SquareLive implements Parcelable {

    /**
     * id : 32
     * groupId : null
     * userId : 1
     * title : 5456456456
     * img : null
     * type : 1
     * status : null
     * platform : null
     * createTime : 1516959148000
     * updateTime : null
     * content : 444444
     * nickName : hyk
     * avatar : sdasdasdasd
     * goodCount : 0
     * badCount : 0
     * commentCount : 0
     * createTimeStr : null
     * commentVos : null
     */

    private String id;
    private String groupId;
    private String userId;
    private String title;
    private String img;
    private String type;
    private String status;
    private String platform;
    private String createTime;
    private String updateTime;
    private String content;
    private String nickName;
    private String avatar;
    private String goodCount;
    private String badCount;
    private String commentCount;
    private String createTimeStr;
    private String praiseStr;
    private String groupName;
    private String groupMaster;
    private String userPraise;
    private List<Comment> commentVos;

    protected SquareLive(Parcel in) {
        id = in.readString();
        groupId = in.readString();
        userId = in.readString();
        title = in.readString();
        img = in.readString();
        type = in.readString();
        status = in.readString();
        platform = in.readString();
        createTime = in.readString();
        updateTime = in.readString();
        content = in.readString();
        nickName = in.readString();
        avatar = in.readString();
        goodCount = in.readString();
        badCount = in.readString();
        commentCount = in.readString();
        createTimeStr = in.readString();
        praiseStr = in.readString();
        groupName = in.readString();
        groupMaster = in.readString();
        userPraise = in.readString();
    }

    public static final Creator<SquareLive> CREATOR = new Creator<SquareLive>() {
        @Override
        public SquareLive createFromParcel(Parcel in) {
            return new SquareLive(in);
        }

        @Override
        public SquareLive[] newArray(int size) {
            return new SquareLive[size];
        }
    };

    public String getGroupMaster() {
        return groupMaster;
    }

    public void setGroupMaster(String groupMaster) {
        this.groupMaster = groupMaster;
    }

    public String getUserPraise() {
        return userPraise;
    }

    public void setUserPraise(String userPraise) {
        this.userPraise = userPraise;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public List<Comment> getCommentVos() {
        return commentVos;
    }

    public void setCommentVos(List<Comment> commentVos) {
        this.commentVos = commentVos;
    }

    public String getPraiseStr() {
        return praiseStr;
    }

    public void setPraiseStr(String praiseStr) {
        this.praiseStr = praiseStr;
    }


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(groupId);
        parcel.writeString(userId);
        parcel.writeString(title);
        parcel.writeString(img);
        parcel.writeString(type);
        parcel.writeString(status);
        parcel.writeString(platform);
        parcel.writeString(createTime);
        parcel.writeString(updateTime);
        parcel.writeString(content);
        parcel.writeString(nickName);
        parcel.writeString(avatar);
        parcel.writeString(goodCount);
        parcel.writeString(badCount);
        parcel.writeString(commentCount);
        parcel.writeString(createTimeStr);
        parcel.writeString(praiseStr);
        parcel.writeString(groupName);
        parcel.writeString(groupMaster);
        parcel.writeString(userPraise);
    }
}
