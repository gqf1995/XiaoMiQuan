package com.fivefivelike.mybaselibrary.utils;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/12 0012.
 */

public class ListUtils {

    public static boolean isEmpty(List list) {
        if (list != null && !list.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
