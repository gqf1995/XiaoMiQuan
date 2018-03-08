package com.xiaomiquan.entity.bean;

/**
 * Created by 郭青枫 on 2018/3/8 0008.
 */

public class Participant {
    /**
     * avatar : http://topcoin.oss-cn-hangzhou.aliyuncs.com/headimg/f044dc83b37e801212deee465ab46cff.jpg
     * bigv : 0
     * brief :
     * createTime : 1520316616000
     * email :
     * id : 26
     * imToken :
     * nickName : hyk
     * password :
     * phone : 17633905867
     * score : 0
     * subscribeCharge : 0
     * updateTime : null
     * vBrief :
     */

    private String avatar;
    private int bigv;
    private String brief;
    private long createTime;
    private String email;
    private int id;
    private String imToken;
    private String nickName;
    private String password;
    private String phone;
    private int score;
    private int subscribeCharge;
    private long updateTime;
    private String vBrief;
    private boolean attentionMyfans;

    public boolean isAttentionMyfans() {
        return attentionMyfans;
    }

    public void setAttentionMyfans(boolean attentionMyfans) {
        this.attentionMyfans = attentionMyfans;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getBigv() {
        return bigv;
    }

    public void setBigv(int bigv) {
        this.bigv = bigv;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSubscribeCharge() {
        return subscribeCharge;
    }

    public void setSubscribeCharge(int subscribeCharge) {
        this.subscribeCharge = subscribeCharge;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getvBrief() {
        return vBrief;
    }

    public void setvBrief(String vBrief) {
        this.vBrief = vBrief;
    }
}
