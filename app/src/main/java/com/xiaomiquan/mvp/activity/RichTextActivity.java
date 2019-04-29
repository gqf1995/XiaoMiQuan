package com.xiaomiquan.mvp.activity;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.mvp.databinder.RichTextBinder;
import com.xiaomiquan.mvp.delegate.RichTextDelegate;

public class RichTextActivity extends BaseDataBindActivity<RichTextDelegate, RichTextBinder> {

    @Override
    protected Class<RichTextDelegate> getDelegateClass() {
        return RichTextDelegate.class;
    }

    @Override
    public RichTextBinder getDataBinder(RichTextDelegate viewDelegate) {
        return new RichTextBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));

    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
        }
    }

}
