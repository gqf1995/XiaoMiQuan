package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;


/**
 * Created by 郭青枫 on 2017/9/27.
 */

public class BaseActivityPullBinder<T extends BaseActivityPullDelegate> extends BaseDataBind<T> {
    public BaseActivityPullBinder(T viewDelegate) {
        super(viewDelegate);
    }


}