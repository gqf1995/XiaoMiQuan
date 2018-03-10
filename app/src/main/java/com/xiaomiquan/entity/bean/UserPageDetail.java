package com.xiaomiquan.entity.bean;

import com.xiaomiquan.entity.bean.chat.ChatLiveItem;

/**
 * Created by 郭青枫 on 2018/3/6 0006.
 */

public class UserPageDetail {

    /**
     * isAttention : false
     * attentionCount : 0
     * fansCount : 0
     * user : {"avatar":"http://topcoin.oss-cn-hangzhou.aliyuncs.com/headimg/a334d3b7a689a9a1f28ed2f56d5cf3a3.jpg","bigv":0,"brief":"","createTime":1519469437000,"email":"","id":1,"imToken":"h89lfK8SuQeukm3kwE3crNlca4Pgti1k+vD7xo8Nl9+hQWpwdnWSt3yTl+XJbyvTp5p8GY0O/7s90fn+eOj8sw==","nickName":"Jerry昊昊","password":"ec06827a87006e75f7182d5e1d6920e5","phone":"17737460923","score":0,"subscribeCharge":0,"updateTime":1519469437000,"vBrief":""}
     */

    private boolean isAttention;
    private int attentionCount;
    private int fansCount;
    private UserBean user;
    private ChatLiveItem articleTopicVos;

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public ChatLiveItem getArticleTopicVos() {
        return articleTopicVos;
    }

    public void setArticleTopicVos(ChatLiveItem articleTopicVos) {
        this.articleTopicVos = articleTopicVos;
    }

    public boolean isIsAttention() {
        return isAttention;
    }

    public void setIsAttention(boolean isAttention) {
        this.isAttention = isAttention;
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * avatar : http://topcoin.oss-cn-hangzhou.aliyuncs.com/headimg/a334d3b7a689a9a1f28ed2f56d5cf3a3.jpg
         * bigv : 0
         * brief :
         * createTime : 1519469437000
         * email :
         * id : 1
         * imToken : h89lfK8SuQeukm3kwE3crNlca4Pgti1k+vD7xo8Nl9+hQWpwdnWSt3yTl+XJbyvTp5p8GY0O/7s90fn+eOj8sw==
         * nickName : Jerry昊昊
         * password : ec06827a87006e75f7182d5e1d6920e5
         * phone : 17737460923
         * score : 0
         * subscribeCharge : 0
         * updateTime : 1519469437000
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

        public String getVBrief() {
            return vBrief;
        }

        public void setVBrief(String vBrief) {
            this.vBrief = vBrief;
        }
    }
}
