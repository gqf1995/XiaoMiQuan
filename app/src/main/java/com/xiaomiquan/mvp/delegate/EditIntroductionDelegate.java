package com.xiaomiquan.mvp.delegate;

import com.xiaomiquan.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import android.view.View;

public class EditIntroductionDelegate extends BaseDelegate{
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_introduction;
    }


    public static class ViewHolder {
        public View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
        
        }

    }
}