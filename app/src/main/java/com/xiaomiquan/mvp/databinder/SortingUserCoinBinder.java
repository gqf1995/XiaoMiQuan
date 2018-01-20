package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.SortingUserCoinDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class SortingUserCoinBinder extends BaseDataBind<SortingUserCoinDelegate> {

    public SortingUserCoinBinder(SortingUserCoinDelegate viewDelegate) {
        super(viewDelegate);
    }


}