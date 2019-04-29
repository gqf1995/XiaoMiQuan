package com.xiaomiquan.entity.bean.chat;

/**
 * Created by 郭青枫 on 2018/3/6 0006.
 */

public class CheckScore {

    /**
     * leader : false
     * canSpeak : false
     * joinGroup : false
     */

    private boolean leader;
    private boolean canSpeak;
    private boolean joinGroup;

    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    public boolean isCanSpeak() {
        return canSpeak;
    }

    public void setCanSpeak(boolean canSpeak) {
        this.canSpeak = canSpeak;
    }

    public boolean isJoinGroup() {
        return joinGroup;
    }

    public void setJoinGroup(boolean joinGroup) {
        this.joinGroup = joinGroup;
    }
}
