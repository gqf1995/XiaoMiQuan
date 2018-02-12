package com.xiaomiquan.entity.bean.group;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/12 0012.
 */

public class TeamDetails {
    HotTeam gameTeamOwnerVo;
    GroupItem myAccount;

    List<Player> playerList;
    boolean isSelf;

    public HotTeam getGameTeamOwnerVo() {
        return gameTeamOwnerVo;
    }

    public void setGameTeamOwnerVo(HotTeam gameTeamOwnerVo) {
        this.gameTeamOwnerVo = gameTeamOwnerVo;
    }

    public GroupItem getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(GroupItem myAccount) {
        this.myAccount = myAccount;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public static class Player {

        /**
         * avatar : http://topcoin.oss-cn-hangzhou.aliyuncs.com/headimg/8fc790fd52c6d8de26a605944e8acaaa.jpg
         * demoId : 3
         * nickName : 王念龙1
         * totalIncome : 0
         * totalIncomeStr :
         * userId : 4
         */

        private String avatar;
        private int demoId;
        private String nickName;
        private int totalIncome;
        private String totalIncomeStr;
        private int userId;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getDemoId() {
            return demoId;
        }

        public void setDemoId(int demoId) {
            this.demoId = demoId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(int totalIncome) {
            this.totalIncome = totalIncome;
        }

        public String getTotalIncomeStr() {
            return totalIncomeStr;
        }

        public void setTotalIncomeStr(String totalIncomeStr) {
            this.totalIncomeStr = totalIncomeStr;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
