package com.xiaomiquan.entity.bean;

/**
 * Created by 郭青枫 on 2018/3/9 0009.
 */

public class PraiseReplyItem {
    /**
     * avatar : http://topcoin.oss-cn-hangzhou.aliyuncs.com/headimg/e9bbf5c4631eb31bbd2cf7c4e8541bce.jpg
     * content : 我去找你有事做朋友一起玩耍
     * createTime : 1518257646000
     * createTimeStr : 2018/02/10 18:14
     * linkId : 171
     * nickName : btc154CdU
     * praise : 1
     * reply :
     * showContent : 赞了您的主题[测试图片]
     * title : 测试图片
     * type : 1
     * userId : 5
     */

    private String avatar;
    private String content;
    private long createTime;
    private String createTimeStr;
    private int linkId;
    private String nickName;
    private int praise;
    private String reply;
    private String showContent;
    private String title;
    private int type;
    private int userId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getShowContent() {
        return showContent;
    }

    public void setShowContent(String showContent) {
        this.showContent = showContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
