package com.xiaomiquan.mvp.delegate;

import com.xiaomiquan.R;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import android.view.View;

public class AllGroupDelegate extends BaseDelegate{
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_group;
    }


    public static class ViewHolder {
        public View rootView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
        
        }

    }
}