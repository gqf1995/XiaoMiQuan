package com.xiaomiquan.entity.bean.circle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andy on 2018/2/5.
 */

public class News implements Parcelable {

    /**
     * id : 1
     * content : <p>1a123323aasd</p>
     * level : 5
     * goodNum : 1
     * badNum : 0
     * link : 123
     * from : 343434
     * createTime : 2018-01-31 12:15:31
     * good : 1
     * bad : 0
     * commentCount : 1
     */

    private String id;
    private String content;
    private int level;
    private int goodNum;
    private int badNum;
    private String link;
    private String from;
    private String createTime;
    private String good;
    private String bad;
    private String commentCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public int getBadNum() {
        return badNum;
    }

    public void setBadNum(int badNum) {
        this.badNum = badNum;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public String getBad() {
        return bad;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeInt(this.level);
        dest.writeInt(this.goodNum);
        dest.writeInt(this.badNum);
        dest.writeString(this.link);
        dest.writeString(this.from);
        dest.writeString(this.createTime);
        dest.writeString(this.good);
        dest.writeString(this.bad);
        dest.writeString(this.commentCount);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.id = in.readString();
        this.content = in.readString();
        this.level = in.readInt();
        this.goodNum = in.readInt();
        this.badNum = in.readInt();
        this.link = in.readString();
        this.from = in.readString();
        this.createTime = in.readString();
        this.good = in.readString();
        this.bad = in.readString();
        this.commentCount = in.readString();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
