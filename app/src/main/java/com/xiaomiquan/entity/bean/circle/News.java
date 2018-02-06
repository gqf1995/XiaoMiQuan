package com.xiaomiquan.entity.bean.circle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Andy on 2018/2/5.
 */

public class News implements Parcelable {


    /**
     * id : 1
     * content : null
     * level : 5
     * goodNum : 0
     * badNum : 0
     * link : 123
     * from : 343434
     * createTime : 2018-01-31 12:15:31
     */

    private String id;
    private String content;
    private String level;
    private String goodNum;
    private String badNum;
    private String link;
    private String from;
    private String createTime;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(String goodNum) {
        this.goodNum = goodNum;
    }

    public String getBadNum() {
        return badNum;
    }

    public void setBadNum(String badNum) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeString(this.level);
        dest.writeString(this.goodNum);
        dest.writeString(this.badNum);
        dest.writeString(this.link);
        dest.writeString(this.from);
        dest.writeString(this.createTime);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.id = in.readString();
        this.content = in.readString();
        this.level = in.readString();
        this.goodNum = in.readString();
        this.badNum = in.readString();
        this.link = in.readString();
        this.from = in.readString();
        this.createTime = in.readString();
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
