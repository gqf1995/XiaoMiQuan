package com.xiaomiquan.entity.bean.group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/3 0003.
 */

public class GroupRank {


    /**
     * code : 2
     * topName : 30天排行
     * tops : [{"attentionCount":1,"isAttention":0,"isSelf":1,"name":"旱涝保收","rate":1.1211,"sort":1,"userId":1},{"attentionCount":2,"isAttention":0,"isSelf":0,"name":"吃胡胡","rate":0,"sort":2,"userId":2}]
     */

    private int code;
    private String topName;
    private List<GroupItem> tops;
    public GroupRank() {
        super();
    }
    public GroupRank(int code, String topName, List<GroupItem> tops) {
        this.code = code;
        this.topName = topName;
        this.tops = new ArrayList<>();
        this.tops.addAll(tops);
    }

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

    public List<GroupItem> getTops() {
        return tops;
    }

    public void setTops(List<GroupItem> tops) {
        this.tops = tops;
    }


}
