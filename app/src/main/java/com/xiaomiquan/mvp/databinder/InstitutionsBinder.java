package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.InstitutionsDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class InstitutionsBinder extends BaseDataBind<InstitutionsDelegate> {

    public InstitutionsBinder(InstitutionsDelegate viewDelegate) {
        super(viewDelegate);
    }


}