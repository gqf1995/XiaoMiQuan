package com.xiaomiquan.entity.bean.circle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andy on 2018/1/19.
 */

public class UserCircle implements Parcelable {

    /**
     * id : 3
     * groupNum : 20180120100750
     * userId : 1
     * name : 圈子名称
     * avatar : 圈子头像
     * type : 圈子类型
     * brief : 圈子简介
     * status : 1
     * createTime : 1516414070000
     * updateTime : null
     * nickName : null
     * memberCount : 3
     */

    private String id;
    private String groupNum;
    private String userId;
    private String name;
    private String avatar;
    private String type;
    private String brief;
    private String status;
    private String createTime;
    private String updateTime;
    private String nickName;
    private String memberCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    public String getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.groupNum);
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.type);
        dest.writeString(this.brief);
        dest.writeString(this.status);
        dest.writeString(this.createTime);
        dest.writeString(this.updateTime);
        dest.writeString(this.nickName);
        dest.writeString(this.memberCount);
    }

    public UserCircle() {
    }

    protected UserCircle(Parcel in) {
        this.id = in.readString();
        this.groupNum = in.readString();
        this.userId = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.type = in.readString();
        this.brief = in.readString();
        this.status = in.readString();
        this.createTime = in.readString();
        this.updateTime = in.readString();
        this.nickName = in.readString();
        this.memberCount = in.readString();
    }

    public static final Parcelable.Creator<UserCircle> CREATOR = new Parcelable.Creator<UserCircle>() {
        @Override
        public UserCircle createFromParcel(Parcel source) {
            return new UserCircle(source);
        }

        @Override
        public UserCircle[] newArray(int size) {
            return new UserCircle[size];
        }
    };
}
