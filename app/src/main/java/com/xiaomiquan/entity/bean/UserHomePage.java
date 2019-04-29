package com.xiaomiquan.entity.bean;

import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserCircle;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/6 0006.
 */

public class UserHomePage {

    private int attentionCount;
    private int fansCount;
    private UserBean user;
    private boolean isAttention;


    private List<SquareLive> articleTopicVos;
    private List<UserCircle> groupVos;

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

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public List<SquareLive> getArticleTopicVos() {
        return articleTopicVos;
    }

    public void setArticleTopicVos(List<SquareLive> articleTopicVos) {
        this.articleTopicVos = articleTopicVos;
    }

    public List<UserCircle> getGroupVos() {
        return groupVos;
    }

    public void setGroupVos(List<UserCircle> groupVos) {
        this.groupVos = groupVos;
    }

    public static class UserBean {
        /**
         * avatar : http://topcoin.oss-cn-hangzhou.aliyuncs.com/d87fe30aceaffc3704cc2456415b8c09.jpg
         * bigv : 0
         * createTime : 1517835022000
         * email :
         * id : 1
         * imToken : H85C+3AJGsOsHyTwIoZqtVVxHIYGnXpkHuJ3K63i4Y5Z4wMdVwVksMpT8J0C9EyxIAmgKiNVSvs=
         * nickName : 无敌
         * password : c4ca4238a0b923820dcc509a6f75849b
         * phone : 17633905867
         * subscribeCharge : 0
         * updateTime : 1517835021000
         */

        private String avatar;
        private int bigv;
        private long createTime;
        private String email;
        private int id;
        private String imToken;
        private String nickName;
        private String password;
        private String phone;
        private int subscribeCharge;
        private long updateTime;

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
    }
}
