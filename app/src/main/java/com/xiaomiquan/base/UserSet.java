package com.xiaomiquan.base;

import com.xiaomiquan.R;

/**
 * Created by 郭青枫 on 2018/1/12 0012.
 */

public class UserSet {

    private static class userSet {
        private static UserSet userSet = new UserSet();
    }

    public static UserSet getinstance() {
        return userSet.userSet;
    }


    public int getDropColor() {
        return R.color.increasing_color;
    }

    public int getRiseColor() {
        return R.color.decreasing_color;
    }

}
