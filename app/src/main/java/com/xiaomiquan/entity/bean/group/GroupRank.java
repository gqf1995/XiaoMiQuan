package com.xiaomiquan.entity.bean.group;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/3 0003.
 */

public class GroupRank {


    /**
     * code : 1
     * topName : 7天排行
     * tops : [{"attentionCount":1,"isAttention":1,"isSelf":0,"name":"旱涝保收","rate":1.1011,"sort":1,"userId":1},{"attentionCount":2,"isAttention":1,"isSelf":0,"name":"吃胡胡","rate":0,"sort":2,"userId":0}]
     */

    private int code;
    private String topName;
    private List<TopsBean> tops;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }

    public List<TopsBean> getTops() {
        return tops;
    }

    public void setTops(List<TopsBean> tops) {
        this.tops = tops;
    }

    public static class TopsBean {
        /**
         * attentionCount : 1
         * isAttention : 1
         * isSelf : 0
         * name : 旱涝保收
         * rate : 1.1011
         * sort : 1
         * userId : 1
         */

        private int attentionCount;
        private int isAttention;
        private int isSelf;
        private String name;
        private double rate;
        private int sort;
        private int userId;

        public int getAttentionCount() {
            return attentionCount;
        }

        public void setAttentionCount(int attentionCount) {
            this.attentionCount = attentionCount;
        }

        public int getIsAttention() {
            return isAttention;
        }

        public void setIsAttention(int isAttention) {
            this.isAttention = isAttention;
        }

        public int getIsSelf() {
            return isSelf;
        }

        public void setIsSelf(int isSelf) {
            this.isSelf = isSelf;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
