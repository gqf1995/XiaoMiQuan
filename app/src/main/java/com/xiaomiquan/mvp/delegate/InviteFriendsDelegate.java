package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;

import skin.support.widget.SkinCompatToolbar;

public class InviteFriendsDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_friends;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView iv_pic;
        public TextView tv_save;
        public TextView tv_send;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_pic = (ImageView) rootView.findViewById(R.id.iv_pic);
            this.tv_save = (TextView) rootView.findViewById(R.id.tv_save);
            this.tv_send = (TextView) rootView.findViewById(R.id.tv_send);
        }

    }
}