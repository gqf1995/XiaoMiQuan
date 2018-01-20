package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.mvp.view.IDelegate;
import com.xiaomiquan.mvp.delegate.CircleInnfoDelegate;

/**
 * Created by Andy on 2018/1/19.
 */

public class CircleInfoBinder extends BaseDataBind<CircleInnfoDelegate> {
    public CircleInfoBinder(CircleInnfoDelegate viewDelegate) {
        super(viewDelegate);
    }
}
