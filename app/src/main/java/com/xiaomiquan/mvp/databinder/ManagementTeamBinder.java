package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.ManagementTeamDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class ManagementTeamBinder extends BaseDataBind<ManagementTeamDelegate> {

    public ManagementTeamBinder(ManagementTeamDelegate viewDelegate) {
        super(viewDelegate);
    }


}