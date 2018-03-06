package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fivefivelike.mybaselibrary.utils.glide.GlideUtils.BASE_URL;

public class UserDrawerDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_drawer;
    }

    public void initUserMsg(UserLogin userLogin) {
        //基础信息设置
        //用户信息设置brief
        if (userLogin == null) {
            viewHolder.tv_name.setText(CommonUtils.getString(R.string.str_nologin));
            viewHolder.lin8.setVisibility(View.GONE);
            viewHolder.tv_content.setVisibility(View.GONE);
            GlideUtils.loadImage(BASE_URL, viewHolder.ic_pic);
            return;
        }
        viewHolder.tv_name.setText(userLogin.getNickName());
        viewHolder.lin8.setVisibility(View.VISIBLE);
        viewHolder.tv_content.setVisibility(View.VISIBLE);
        viewHolder.tv_content.setText(userLogin.getBrief());
        GlideUtils.loadImage(userLogin.getAvatar(), viewHolder.ic_pic);

    }

    public static class ViewHolder {
        public View rootView;
        public CircleImageView ic_pic;
        public TextView tv_name;
        public LinearLayout lin_user;
        public TextView tv_content;
        public LinearLayout lin1;
        public TextView tv_focuse_num;
        public LinearLayout lin2;
        public TextView tv_fans_num;
        public LinearLayout lin3;
        public LinearLayout lin4;
        public LinearLayout lin5;
        public LinearLayout lin6;
        public LinearLayout lin7;
        public LinearLayout lin8;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.lin_user = (LinearLayout) rootView.findViewById(R.id.lin_user);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.lin1 = (LinearLayout) rootView.findViewById(R.id.lin1);
            this.tv_focuse_num = (TextView) rootView.findViewById(R.id.tv_focuse_num);
            this.lin2 = (LinearLayout) rootView.findViewById(R.id.lin2);
            this.tv_fans_num = (TextView) rootView.findViewById(R.id.tv_fans_num);
            this.lin3 = (LinearLayout) rootView.findViewById(R.id.lin3);
            this.lin4 = (LinearLayout) rootView.findViewById(R.id.lin4);
            this.lin5 = (LinearLayout) rootView.findViewById(R.id.lin5);
            this.lin6 = (LinearLayout) rootView.findViewById(R.id.lin6);
            this.lin7 = (LinearLayout) rootView.findViewById(R.id.lin7);
            this.lin8 = (LinearLayout) rootView.findViewById(R.id.lin8);
        }

    }
}