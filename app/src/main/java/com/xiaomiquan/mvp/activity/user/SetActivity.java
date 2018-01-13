package com.xiaomiquan.mvp.activity.user;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.delegate.SetDelegate;

public class SetActivity extends BaseActivity<SetDelegate> {

    @Override
    protected Class<SetDelegate> getDelegateClass() {
        return SetDelegate.class;
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_setting)));
        viewDelegate.setOnClickListener(this, R.id.lin_clean_cache, R.id.lin_about_us);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_clean_cache:
                //清理缓存
                break;
            case R.id.lin_about_us:
                //关于我们

                break;
        }
    }
}
