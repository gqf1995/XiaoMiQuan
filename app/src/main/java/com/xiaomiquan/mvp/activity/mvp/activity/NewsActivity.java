package com.xiaomiquan.mvp.activity.mvp.activity;

import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.activity.mvp.databinder.NewsBinder;
import com.xiaomiquan.mvp.activity.mvp.delegate.NewsDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class NewsActivity extends BaseDataBindActivity<NewsDelegate, NewsBinder> {

    @Override
    protected Class<NewsDelegate> getDelegateClass() {
        return NewsDelegate.class;
    }

    @Override
    public NewsBinder getDataBinder(NewsDelegate viewDelegate) {
        return new NewsBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));

    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
