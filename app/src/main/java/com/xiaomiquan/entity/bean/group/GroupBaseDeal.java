package com.xiaomiquan.entity.bean.group;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 郭青枫 on 2018/1/31 0031.
 */

public class GroupBaseDeal implements Parcelable {

    private String id;
    private String balance;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.balance);
        dest.writeString(this.name);
    }

    public GroupBaseDeal() {
    }

    protected GroupBaseDeal(Parcel in) {
        this.id = in.readString();
        this.balance = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<GroupBaseDeal> CREATOR = new Parcelable.Creator<GroupBaseDeal>() {
        @Override
        public GroupBaseDeal createFromParcel(Parcel source) {
            return new GroupBaseDeal(source);
        }

        @Override
        public GroupBaseDeal[] newArray(int size) {
            return new GroupBaseDeal[size];
        }
    };
}
