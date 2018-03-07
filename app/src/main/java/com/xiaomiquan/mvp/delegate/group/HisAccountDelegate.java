package com.xiaomiquan.mvp.delegate.group;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.kyleduo.switchbutton.SwitchButton;
import com.tablayout.CommonTabLayout;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HisAccountDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.lin_table, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_110px), viewHolder.viewpager, false);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_his_account;
    }

    public static class ViewHolder {
        public View rootView;
        public CircleImageView cv_head;
        public TextView tv_name;
        public TextView tv_time;
        public SwitchButton checkbox_notification;
        public CommonTabLayout tl_1;
        public LinearLayout lin_table;
        public ViewPager viewpager;
        public JudgeNestedScrollView nestedScrollView;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.cv_head = (CircleImageView) rootView.findViewById(R.id.cv_head);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.checkbox_notification = (SwitchButton) rootView.findViewById(R.id.checkbox_notification);
            this.tl_1 = (CommonTabLayout) rootView.findViewById(R.id.tl_1);
            this.lin_table = (LinearLayout) rootView.findViewById(R.id.lin_table);
            this.viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
            this.nestedScrollView = (JudgeNestedScrollView) rootView.findViewById(R.id.nestedScrollView);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}