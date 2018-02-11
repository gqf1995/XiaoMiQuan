package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.AllTeamDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class AllTeamBinder extends BaseDataBind<AllTeamDelegate> {

    public AllTeamBinder(AllTeamDelegate viewDelegate) {
        super(viewDelegate);
    }


}