package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.editcodeview.EditCodeView;

public class VerificationCodeDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verification_code;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_toast;
        public EditCodeView et_code;
        public TextView tv_get_again;
        public TextView tv_not_received;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_toast = (TextView) rootView.findViewById(R.id.tv_toast);
            this.et_code = (EditCodeView) rootView.findViewById(R.id.et_code);
            this.tv_get_again = (TextView) rootView.findViewById(R.id.tv_get_again);
            this.tv_not_received = (TextView) rootView.findViewById(R.id.tv_not_received);
        }

    }
}