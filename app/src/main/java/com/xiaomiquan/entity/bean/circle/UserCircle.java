package com.xiaomiquan.entity.bean.circle;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Andy on 2018/1/19.
 */

public class UserCircle implements Parcelable{


    /**
     * articleTopicVos : []
     * avatar : http://n1.itc.cn/img8/wb/smccloud/2015/04/25/142992747468731176.JPEG
     * brief : 没什么
     * chargeMoney : 0
     * createTime : 1517312512000
     * email :
     * groupNum : 20180130194152
     * id : 1
     * isBanned : true
     * isFree : true
     * memberCount : 1
     * name : 免费圈子111
     * nickName : 成龙
     * ownerAvatar : https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1121475478,2545730346&fm=27&gp=0.jpg
     * phone : 11111111113
     * status : 1
     * type : 科技
     * updateTime : null
     * userId : 3
     */

    private String avatar;
    private String brief;
    private String chargeMoney;
    private String createTime;
    private String email;
    private String groupNum;
    private String id;
    private boolean isBanned;
    private boolean isFree;
    private String memberCount;
    private String name;
    private String nickName;
    private String ownerAvatar;
    private String phone;
    private String status;
    private String type;
    private String updateTime;
    private String userId;
    private List<SquareLive> articleTopicVos;

    public UserCircle(){}

    protected UserCircle(Parcel in) {
        avatar = in.readString();
        brief = in.readString();
        chargeMoney = in.readString();
        createTime = in.readString();
        email = in.readString();
        groupNum = in.readString();
        id = in.readString();
        isBanned = in.readByte() != 0;
        isFree = in.readByte() != 0;
        memberCount = in.readString();
        name = in.readString();
        nickName = in.readString();
        ownerAvatar = in.readString();
        phone = in.readString();
        status = in.readString();
        type = in.readString();
        updateTime = in.readString();
        userId = in.readString();
        articleTopicVos = in.createTypedArrayList(SquareLive.CREATOR);
    }

    public static final Creator<UserCircle> CREATOR = new Creator<UserCircle>() {
        @Override
        public UserCircle createFromParcel(Parcel in) {
            return new UserCircle(in);
        }

        @Override
        public UserCircle[] newArray(int size) {
            return new UserCircle[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<SquareLive> getArticleTopicVos() {
        return articleTopicVos;
    }

    public void setArticleTopicVos(List<SquareLive> articleTopicVos) {
        this.articleTopicVos = articleTopicVos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(avatar);
        parcel.writeString(brief);
        parcel.writeString(chargeMoney);
        parcel.writeString(createTime);
        parcel.writeString(email);
        parcel.writeString(groupNum);
        parcel.writeString(id);
        parcel.writeByte((byte) (isBanned ? 1 : 0));
        parcel.writeByte((byte) (isFree ? 1 : 0));
        parcel.writeString(memberCount);
        parcel.writeString(name);
        parcel.writeString(nickName);
        parcel.writeString(ownerAvatar);
        parcel.writeString(phone);
        parcel.writeString(status);
        parcel.writeString(type);
        parcel.writeString(updateTime);
        parcel.writeString(userId);
        parcel.writeTypedList(articleTopicVos);
    }
}
