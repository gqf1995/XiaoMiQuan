package com.xiaomiquan.entity.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 郭青枫 on 2017/11/11.
 */
@Entity
public class UserLogin {

    @Id
    private long id;

    /**
     * phone : 15670702651
     * id : 3
     * avatar : 137.png
     * email : null
     */

    private String phone;
    private String avatar;
    private String email;
    private String imToken;
    private String nickName;

    @Generated(hash = 774387590)
    public UserLogin(long id, String phone, String avatar, String email,
            String imToken, String nickName) {
        this.id = id;
        this.phone = phone;
        this.avatar = avatar;
        this.email = email;
        this.imToken = imToken;
        this.nickName = nickName;
    }

    @Generated(hash = 180802810)
    public UserLogin() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
