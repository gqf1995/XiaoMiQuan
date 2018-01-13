package com.xiaomiquan.mvp.databinder;


import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

/**
 * Created by 郭青枫 on 2017/9/27.
 */

public class BaseFragmentPullBinder extends BaseDataBind<BaseFragentPullDelegate> {
    public BaseFragmentPullBinder(BaseFragentPullDelegate viewDelegate) {
        super(viewDelegate);
    }

}