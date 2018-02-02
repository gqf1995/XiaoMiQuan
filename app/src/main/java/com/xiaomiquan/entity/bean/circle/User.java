package com.xiaomiquan.entity.bean.circle;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Andy on 2018/1/30.
 */

public class User implements Parcelable {

    /**
     * id : 3
     * nickName : 成龙
     * password : 96e79218965eb72c92a549dd5a330112
     * email : null
     * phone : 11111111113
     * avatar : https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1121475478,2545730346&fm=27&gp=0.jpg
     * imToken : null
     * bigv : 0
     * subscribeCharge : 0
     * createTime : null
     * updateTime : 1517276141000
     */

    private String id;
    private String nickName;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private String imToken;
    private String bigv;
    private String subscribeCharge;
    private String createTime;
    private String updateTime;

    protected User(Parcel in) {
        id = in.readString();
        nickName = in.readString();
        password = in.readString();
        email = in.readString();
        phone = in.readString();
        avatar = in.readString();
        imToken = in.readString();
        bigv = in.readString();
        subscribeCharge = in.readString();
        createTime = in.readString();
        updateTime = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getBigv() {
        return bigv;
    }

    public void setBigv(String bigv) {
        this.bigv = bigv;
    }

    public String getSubscribeCharge() {
        return subscribeCharge;
    }

    public void setSubscribeCharge(String subscribeCharge) {
        this.subscribeCharge = subscribeCharge;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nickName);
        parcel.writeString(password);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(avatar);
        parcel.writeString(imToken);
        parcel.writeString(bigv);
        parcel.writeString(subscribeCharge);
        parcel.writeString(createTime);
        parcel.writeString(updateTime);
    }
}
