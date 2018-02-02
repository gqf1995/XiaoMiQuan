package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.EditText;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class EditIntroductionDelegate extends BaseDelegate {
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
        public EditText et_introduction;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.et_introduction = (EditText) rootView.findViewById(R.id.et_introduction);
        }

    }
}