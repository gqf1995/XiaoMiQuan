package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.EditText;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

public class EditTextDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_text;
    }


    public static class ViewHolder {
        public View rootView;
        public EditText edit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.edit = (EditText) rootView.findViewById(R.id.edit);
        }

    }
}