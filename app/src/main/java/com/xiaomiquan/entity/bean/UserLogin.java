package com.xiaomiquan.entity.bean;

import android.text.TextUtils;

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
     * brief : null
     * score : 0
     * phone : 15139464819
     * nickName : btc868isB
     * imToken : tTC8BvmmjtPsYCS8iyh+j1VxHIYGnXpkHuJ3K63i4Y5Z4wMdVwVksHRuYRvr9RezLzO0zhluhzA=
     * vBrief : null
     * attentionCount : 0
     * fansCount : 7
     * id : 2
     * avatar : 36.png
     * email : null
     */

    private String brief;
    private int score;
    private String phone;
    private String nickName;
    private String imToken;
    private String vBrief;
    private int attentionCount;
    private int fansCount;
    private String avatar;
    private String email;
    private String demoId;



    @Generated(hash = 973876413)
    public UserLogin(long id, String brief, int score, String phone, String nickName,
            String imToken, String vBrief, int attentionCount, int fansCount, String avatar,
            String email, String demoId) {
        this.id = id;
        this.brief = brief;
        this.score = score;
        this.phone = phone;
        this.nickName = nickName;
        this.imToken = imToken;
        this.vBrief = vBrief;
        this.attentionCount = attentionCount;
        this.fansCount = fansCount;
        this.avatar = avatar;
        this.email = email;
        this.demoId = demoId;
    }

    @Generated(hash = 180802810)
    public UserLogin() {
    }



    public String getBrief() {
        if (TextUtils.isEmpty(brief)) {
            return "";
        }
        return brief;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getvBrief() {
        return vBrief;
    }

    public void setvBrief(String vBrief) {
        this.vBrief = vBrief;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(int attentionCount) {
        this.attentionCount = attentionCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
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

    public String getDemoId() {
        return demoId;
    }

    public void setDemoId(String demoId) {
        this.demoId = demoId;
    }

    public String getVBrief() {
        return this.vBrief;
    }

    public void setVBrief(String vBrief) {
        this.vBrief = vBrief;
    }
}
