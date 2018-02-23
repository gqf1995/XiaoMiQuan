package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.TeamDetailDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class TeamDetailBinder extends BaseDataBind<TeamDetailDelegate> {

    public TeamDetailBinder(TeamDetailDelegate viewDelegate) {
        super(viewDelegate);
    }


}