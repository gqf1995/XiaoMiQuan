package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;
import skin.support.widget.SkinCompatToolbar;

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
        public EditText tv_introduction;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
            this.tv_nick_name = (EditText) rootView.findViewById(R.id.tv_nick_name);
            this.tv_introduction = (EditText) rootView.findViewById(R.id.tv_introduction);
        }

    }
}