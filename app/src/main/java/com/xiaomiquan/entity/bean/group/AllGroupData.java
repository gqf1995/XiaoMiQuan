package com.xiaomiquan.entity.bean.group;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/11 0011.
 */

public class AllGroupData {
    List<HotTeam> hotTeams;
    List<GroupDynamic> userDemoDynamicList;
    List<GroupItem> userDemoList;//用户账号
    List<GroupItem> topTotal;
    List<GroupItem> topMonth;
    List<GroupItem> topWeeks;
    List<BannerEntity> turnPicturesList;

    public List<BannerEntity> getTurnPicturesList() {
        return turnPicturesList;
    }

    public void setTurnPicturesList(List<BannerEntity> turnPicturesList) {
        this.turnPicturesList = turnPicturesList;
    }

    public List<GroupDynamic> getUserDemoDynamicList() {
        return userDemoDynamicList;
    }

    public void setUserDemoDynamicList(List<GroupDynamic> userDemoDynamicList) {
        this.userDemoDynamicList = userDemoDynamicList;
    }

    public List<HotTeam> getHotTeams() {
        return hotTeams;
    }

    public void setHotTeams(List<HotTeam> hotTeams) {
        this.hotTeams = hotTeams;
    }

    public List<GroupItem> getUserDemoList() {
        return userDemoList;
    }

    public void setUserDemoList(List<GroupItem> userDemoList) {
        this.userDemoList = userDemoList;
    }

    public List<GroupItem> getTopTotal() {
        return topTotal;
    }

    public void setTopTotal(List<GroupItem> topTotal) {
        this.topTotal = topTotal;
    }

    public List<GroupItem> getTopMonth() {
        return topMonth;
    }

    public void setTopMonth(List<GroupItem> topMonth) {
        this.topMonth = topMonth;
    }

    public List<GroupItem> getTopWeeks() {
        return topWeeks;
    }

    public void setTopWeeks(List<GroupItem> topWeeks) {
        this.topWeeks = topWeeks;
    }
}
