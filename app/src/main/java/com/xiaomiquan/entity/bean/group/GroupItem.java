package com.xiaomiquan.entity.bean.group;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 郭青枫 on 2018/1/31 0031.
 */

public class GroupItem implements Parcelable {
    /**
     * id : 1
     * userId : null
     * name : 你摸摸
     * type : null
     * isShow : null
     * balance : 100000.0
     * brief : 额敏工作
     * operationCount : null
     * startTime : null
     * endTime : null
     * createTime : 1517371792000
     * updateTime : null
     * nickName : wangke
     * avatar : https://resources.bicoin.info/image/agency_head_image/5c91636e10a1d3b92d037842cdb4e8d8.jpg
     * totalProfit : 0
     * currProfit : 0
     * attentionCount : 0
     */

    private String id;
    private String userId;
    private String name;
    private String type;
    private String isShow;
    private String balance;
    private String brief;
    private String operationCount;
    private long startTime;
    private long endTime;
    private long createTime;
    private long updateTime;
    private String nickName;
    private String avatar;
    private double totalProfit;
    private double currProfit;
    private String attentionCount;
    private String sync;


    private int isAttention;
    private int isSelf;
    private double rate;
    private int sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(String operationCount) {
        this.operationCount = operationCount;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
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

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double getCurrProfit() {
        return currProfit;
    }

    public void setCurrProfit(double currProfit) {
        this.currProfit = currProfit;
    }

    public String getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(String attentionCount) {
        this.attentionCount = attentionCount;
    }

    public int getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(int isAttention) {
        this.isAttention = isAttention;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.isShow);
        dest.writeString(this.balance);
        dest.writeString(this.brief);
        dest.writeString(this.operationCount);
        dest.writeLong(this.startTime);
        dest.writeLong(this.endTime);
        dest.writeLong(this.createTime);
        dest.writeLong(this.updateTime);
        dest.writeString(this.nickName);
        dest.writeString(this.avatar);
        dest.writeDouble(this.totalProfit);
        dest.writeDouble(this.currProfit);
        dest.writeString(this.attentionCount);
        dest.writeString(this.sync);
        dest.writeInt(this.isAttention);
        dest.writeInt(this.isSelf);
        dest.writeDouble(this.rate);
        dest.writeInt(this.sort);
    }

    public GroupItem() {
    }

    protected GroupItem(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.name = in.readString();
        this.type = in.readString();
        this.isShow = in.readString();
        this.balance = in.readString();
        this.brief = in.readString();
        this.operationCount = in.readString();
        this.startTime = in.readLong();
        this.endTime = in.readLong();
        this.createTime = in.readLong();
        this.updateTime = in.readLong();
        this.nickName = in.readString();
        this.avatar = in.readString();
        this.totalProfit = in.readDouble();
        this.currProfit = in.readDouble();
        this.attentionCount = in.readString();
        this.sync = in.readString();
        this.isAttention = in.readInt();
        this.isSelf = in.readInt();
        this.rate = in.readDouble();
        this.sort = in.readInt();
    }

    public static final Parcelable.Creator<GroupItem> CREATOR = new Parcelable.Creator<GroupItem>() {
        @Override
        public GroupItem createFromParcel(Parcel source) {
            return new GroupItem(source);
        }

        @Override
        public GroupItem[] newArray(int size) {
            return new GroupItem[size];
        }
    };
}
