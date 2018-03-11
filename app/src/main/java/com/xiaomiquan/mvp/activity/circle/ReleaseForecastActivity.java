package com.xiaomiquan.mvp.activity.circle;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.mvp.databinder.circle.ReleaseForecastBinder;
import com.xiaomiquan.mvp.delegate.circle.ReleaseForecastDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Set;

public class ReleaseForecastActivity extends BaseDataBindActivity<ReleaseForecastDelegate, ReleaseForecastBinder> {

    @Override
    protected Class<ReleaseForecastDelegate> getDelegateClass() {
        return ReleaseForecastDelegate.class;
    }

    @Override
    public ReleaseForecastBinder getDataBinder(ReleaseForecastDelegate viewDelegate) {
        return new ReleaseForecastBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));

        viewDelegate.viewHolder.id_currency_flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ToastUtil.show("aaa" + position);
                return true;
            }
        });

    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
