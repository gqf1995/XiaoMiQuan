package com.xiaomiquan.entity.bean.circle;

/**
 * Created by Andy on 2018/1/20.
 */

public class UserFriende {
    /**
     * id : 1
     * nickName : hyk
     * password : c4ca4238a0b923820dcc509a6f75849b
     * email : null
     * phone : 17633905867
     * avatar : sdasdasdasd
     * imToken : null
     * bigv : 2
     * subscribeCharge : 0
     * createTime : 1515414006000
     * updateTime : 1516260051000
     */

    private int id;
    private String nickName;
    private String password;
    private Object email;
    private String phone;
    private String avatar;
    private Object imToken;
    private int bigv;
    private int subscribeCharge;
    private long createTime;
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
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

    public Object getImToken() {
        return imToken;
    }

    public void setImToken(Object imToken) {
        this.imToken = imToken;
    }

    public int getBigv() {
        return bigv;
    }

    public void setBigv(int bigv) {
        this.bigv = bigv;
    }

    public int getSubscribeCharge() {
        return subscribeCharge;
    }

    public void setSubscribeCharge(int subscribeCharge) {
        this.subscribeCharge = subscribeCharge;
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
}
