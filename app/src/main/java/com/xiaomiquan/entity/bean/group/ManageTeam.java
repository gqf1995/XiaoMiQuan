package com.xiaomiquan.entity.bean.group;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/12 0012.
 */

public class ManageTeam {
    /**
     * teamName : 五色战队
     * teamAvatar : e
     * teamId : 1
     * approvesData : [{"reason":"申请加入","nickName":"btc405rid","attentionCount":0,"avatar":"201.png","id":22,"userId":14,"passFlag":false},{"reason":"申请加入","nickName":"testgame","attentionCount":0,"avatar":"e","id":21,"userId":15,"passFlag":true},{"reason":"申请加入","nickName":"btc622wfq","attentionCount":0,"avatar":"156.png","id":23,"userId":13,"passFlag":true}]
     */

    private String teamName;
    private String teamAvatar;
    private int teamId;
    private List<ApprovesDataBean> approvesData;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamAvatar() {
        return teamAvatar;
    }

    public void setTeamAvatar(String teamAvatar) {
        this.teamAvatar = teamAvatar;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public List<ApprovesDataBean> getApprovesData() {
        return approvesData;
    }

    public void setApprovesData(List<ApprovesDataBean> approvesData) {
        this.approvesData = approvesData;
    }

    public static class ApprovesDataBean {
        /**
         * reason : 申请加入
         * nickName : btc405rid
         * attentionCount : 0
         * avatar : 201.png
         * id : 22
         * userId : 14
         * passFlag : false
         */

        private String reason;
        private String nickName;
        private int attentionCount;
        private String avatar;
        private int id;
        private int userId;
        private boolean passFlag;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getAttentionCount() {
            return attentionCount;
        }

        public void setAttentionCount(int attentionCount) {
            this.attentionCount = attentionCount;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public boolean isPassFlag() {
            return passFlag;
        }

        public void setPassFlag(boolean passFlag) {
            this.passFlag = passFlag;
        }
    }
}
