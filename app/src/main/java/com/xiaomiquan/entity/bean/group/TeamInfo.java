package com.xiaomiquan.entity.bean.group;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/13 0013.
 */

public class TeamInfo {
    /**
     * attentionCount : 7
     * avatar : http://topcoin.oss-cn-hangzhou.aliyuncs.com/headimg/292a2f1fc12fb61aba3120158bb995e4.jpg
     * brief : 您KKK
     * count : 19
     * createTime : 1517992055000
     * currProfit : -1.4600
     * endTime : 1518364808000
     * gameEndTime : null
     * gameStatus : 0
     * monthProfit : -2.00
     * name : 墨拒绝了
     * nickName : btc81
     * rates : [-2]
     * startTime : 1518364808000
     * totalProfit : -2.00
     * type : 1
     * weekProfit : -2.00
     * yesterdayProfit : -2.00
     */

    private int attentionCount;
    private String avatar;
    private String brief;
    private int count;
    private long createTime;
    private String currProfit;
    private long endTime;
    private long gameEndTime;
    private int gameStatus;
    private String monthProfit;
    private String name;
    private String nickName;
    private long startTime;
    private String totalProfit;
    private int type;
    private String weekProfit;
    private String yesterdayProfit;
    private List<Float> rates;

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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCurrProfit() {
        return currProfit;
    }

    public void setCurrProfit(String currProfit) {
        this.currProfit = currProfit;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(long gameEndTime) {
        this.gameEndTime = gameEndTime;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getMonthProfit() {
        return monthProfit;
    }

    public void setMonthProfit(String monthProfit) {
        this.monthProfit = monthProfit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWeekProfit() {
        return weekProfit;
    }

    public void setWeekProfit(String weekProfit) {
        this.weekProfit = weekProfit;
    }

    public String getYesterdayProfit() {
        return yesterdayProfit;
    }

    public void setYesterdayProfit(String yesterdayProfit) {
        this.yesterdayProfit = yesterdayProfit;
    }

    public List<Float> getRates() {
        return rates;
    }

    public void setRates(List<Float> rates) {
        this.rates = rates;
    }
}
