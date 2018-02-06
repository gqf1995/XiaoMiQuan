package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.EditText;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeUserInfoDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_user_info;
    }


    public static class ViewHolder {
        public View rootView;
        public CircleImageView ic_pic;
        public EditText tv_nick_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
            this.tv_nick_name = (EditText) rootView.findViewById(R.id.tv_nick_name);
        }

    }
}