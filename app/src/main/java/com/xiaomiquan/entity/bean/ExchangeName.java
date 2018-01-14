package com.xiaomiquan.entity.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 郭青枫 on 2018/1/14 0014.
 */

public class ExchangeName implements Parcelable {
    String ename;
    String cname;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ename);
        dest.writeString(this.cname);
    }

    public ExchangeName() {
    }

    protected ExchangeName(Parcel in) {
        this.ename = in.readString();
        this.cname = in.readString();
    }

    public static final Parcelable.Creator<ExchangeName> CREATOR = new Parcelable.Creator<ExchangeName>() {
        @Override
        public ExchangeName createFromParcel(Parcel source) {
            return new ExchangeName(source);
        }

        @Override
        public ExchangeName[] newArray(int size) {
            return new ExchangeName[size];
        }
    };
}
