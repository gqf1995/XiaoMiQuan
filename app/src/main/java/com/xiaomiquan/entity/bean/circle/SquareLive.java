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

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    private List<String> imgList;

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


    public SquareLive() {
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
        dest.writeString(this.title);
        dest.writeString(this.img);
        dest.writeString(this.type);
        dest.writeString(this.status);
        dest.writeString(this.platform);
        dest.writeString(this.createTime);
        dest.writeString(this.updateTime);
        dest.writeString(this.content);
        dest.writeString(this.nickName);
        dest.writeString(this.avatar);
        dest.writeString(this.goodCount);
        dest.writeString(this.badCount);
        dest.writeString(this.commentCount);
        dest.writeString(this.createTimeStr);
        dest.writeString(this.praiseStr);
        dest.writeString(this.groupName);
        dest.writeString(this.groupMaster);
        dest.writeString(this.userPraise);
        dest.writeTypedList(this.commentVos);
        dest.writeStringList(this.imgList);
    }

    protected SquareLive(Parcel in) {
        this.id = in.readString();
        this.groupId = in.readString();
        this.userId = in.readString();
        this.title = in.readString();
        this.img = in.readString();
        this.type = in.readString();
        this.status = in.readString();
        this.platform = in.readString();
        this.createTime = in.readString();
        this.updateTime = in.readString();
        this.content = in.readString();
        this.nickName = in.readString();
        this.avatar = in.readString();
        this.goodCount = in.readString();
        this.badCount = in.readString();
        this.commentCount = in.readString();
        this.createTimeStr = in.readString();
        this.praiseStr = in.readString();
        this.groupName = in.readString();
        this.groupMaster = in.readString();
        this.userPraise = in.readString();
        this.commentVos = in.createTypedArrayList(Comment.CREATOR);
        this.imgList = in.createStringArrayList();
    }

    public static final Creator<SquareLive> CREATOR = new Creator<SquareLive>() {
        @Override
        public SquareLive createFromParcel(Parcel source) {
            return new SquareLive(source);
        }

        @Override
        public SquareLive[] newArray(int size) {
            return new SquareLive[size];
        }
    };
}
