package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.EditTextDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class EditTextBinder extends BaseDataBind<EditTextDelegate> {

    public EditTextBinder(EditTextDelegate viewDelegate) {
        super(viewDelegate);
    }


}