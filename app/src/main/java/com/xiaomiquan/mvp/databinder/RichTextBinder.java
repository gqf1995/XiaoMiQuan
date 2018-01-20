package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.RichTextDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class RichTextBinder extends BaseDataBind<RichTextDelegate> {

    public RichTextBinder(RichTextDelegate viewDelegate) {
        super(viewDelegate);
    }


}