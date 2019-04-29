package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.kyleduo.switchbutton.SwitchButton;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatManagementDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat_management;
    }


    public static class ViewHolder {
        public View rootView;
        public CircleImageView ic_pic;
        public TextView tv_num;
        public SwitchButton checkbox_status;
        public LinearLayout lin1;
        public TextView tv_name;
        public LinearLayout lin2;
        public LinearLayout lin3;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.checkbox_status = (SwitchButton) rootView.findViewById(R.id.checkbox_status);
            this.lin1 = (LinearLayout) rootView.findViewById(R.id.lin1);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.lin2 = (LinearLayout) rootView.findViewById(R.id.lin2);
            this.lin3 = (LinearLayout) rootView.findViewById(R.id.lin3);
        }

    }
}