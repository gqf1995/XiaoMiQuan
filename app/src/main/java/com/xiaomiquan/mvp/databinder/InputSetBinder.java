package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.InputSetDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class InputSetBinder extends BaseDataBind<InputSetDelegate> {

    public InputSetBinder(InputSetDelegate viewDelegate) {
        super(viewDelegate);
    }


}