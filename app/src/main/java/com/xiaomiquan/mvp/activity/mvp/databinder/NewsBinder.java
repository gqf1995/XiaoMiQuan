package com.xiaomiquan.mvp.activity.mvp.databinder;

import com.xiaomiquan.mvp.activity.mvp.delegate.NewsDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class NewsBinder extends BaseDataBind<NewsDelegate> {

    public NewsBinder(NewsDelegate viewDelegate) {
        super(viewDelegate);
    }


}