package com.xiaomiquan.entity.bean.circle;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by Andy on 2018/1/26.
 */

public class SquareLive implements Parcelable {


    /**
     * avatar : https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1121475478,2545730346&fm=27&gp=0.jpg
     * badCount : 0
     * commentCount : 0
     * commentVos : []
     * content : 监控啦啦啦
     * createTime : 1517839839000
     * createTimeStr : 昨天 22:10
     * goodCount : 0
     * groupId : 0
     * groupMaster : false
     * groupName :
     * hourMinute : 22:10
     * id : 92
     * img :
     * * imgList :
     * * nickName : 成龙
     * platform : 0
     * praiseStr :
     * showImg : true
     * status : 0
     * title :
     * top : 0
     * type : 2
     * updateTime : null
     * userDemoDealVos : []
     * userId : 3
     * userPraise : false
     * yearMonthDay : 18-02-05
     */

    private String avatar;
    private int badCount;
    private int commentCount;
    private String content;
    private String createTime;
    private String createTimeStr;
    private int goodCount;
    private String groupId;
    private boolean groupMaster;
    private String groupName;
    private String hourMinute;
    private String id;
    private String img;
    private String nickName;
    private String platform;
    private String praiseStr;
    private String showImg;
    private String status;
    private String title;
    private String top;
    private String type;
    private String updateTime;
    private String userId;
    private boolean userPraise;
    private String yearMonthDay;
    private List<Comment> commentVos;
    private List<String> imgList;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getBadCount() {
        return badCount;
    }

    public void setBadCount(int badCount) {
        this.badCount = badCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
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

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isGroupMaster() {
        return groupMaster;
    }

    public void setGroupMaster(boolean groupMaster) {
        this.groupMaster = groupMaster;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHourMinute() {
        return hourMinute;
    }

    public void setHourMinute(String hourMinute) {
        this.hourMinute = hourMinute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPraiseStr() {
        return praiseStr;
    }

    public void setPraiseStr(String praiseStr) {
        this.praiseStr = praiseStr;
    }

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isUserPraise() {
        return userPraise;
    }

    public void setUserPraise(boolean userPraise) {
        this.userPraise = userPraise;
    }

    public String getYearMonthDay() {
        return yearMonthDay;
    }

    public void setYearMonthDay(String yearMonthDay) {
        this.yearMonthDay = yearMonthDay;
    }

    public List<Comment> getCommentVos() {
        return commentVos;
    }

    public void setCommentVos(List<Comment> commentVos) {
        this.commentVos = commentVos;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeInt(this.badCount);
        dest.writeInt(this.commentCount);
        dest.writeString(this.content);
        dest.writeString(this.createTime);
        dest.writeString(this.createTimeStr);
        dest.writeInt(this.goodCount);
        dest.writeString(this.groupId);
        dest.writeByte(this.groupMaster ? (byte) 1 : (byte) 0);
        dest.writeString(this.groupName);
        dest.writeString(this.hourMinute);
        dest.writeString(this.id);
        dest.writeString(this.img);
        dest.writeString(this.nickName);
        dest.writeString(this.platform);
        dest.writeString(this.praiseStr);
        dest.writeString(this.showImg);
        dest.writeString(this.status);
        dest.writeString(this.title);
        dest.writeString(this.top);
        dest.writeString(this.type);
        dest.writeString(this.updateTime);
        dest.writeString(this.userId);
        dest.writeByte(this.userPraise ? (byte) 1 : (byte) 0);
        dest.writeString(this.yearMonthDay);
        dest.writeTypedList(this.commentVos);
        dest.writeStringList(this.imgList);
    }

    public SquareLive() {
    }

    protected SquareLive(Parcel in) {
        this.avatar = in.readString();
        this.badCount = in.readInt();
        this.commentCount = in.readInt();
        this.content = in.readString();
        this.createTime = in.readString();
        this.createTimeStr = in.readString();
        this.goodCount = in.readInt();
        this.groupId = in.readString();
        this.groupMaster = in.readByte() != 0;
        this.groupName = in.readString();
        this.hourMinute = in.readString();
        this.id = in.readString();
        this.img = in.readString();
        this.nickName = in.readString();
        this.platform = in.readString();
        this.praiseStr = in.readString();
        this.showImg = in.readString();
        this.status = in.readString();
        this.title = in.readString();
        this.top = in.readString();
        this.type = in.readString();
        this.updateTime = in.readString();
        this.userId = in.readString();
        this.userPraise = in.readByte() != 0;
        this.yearMonthDay = in.readString();
        this.commentVos = in.createTypedArrayList(Comment.CREATOR);
        this.imgList = in.createStringArrayList();
    }

    public static final Parcelable.Creator<SquareLive> CREATOR = new Parcelable.Creator<SquareLive>() {
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
