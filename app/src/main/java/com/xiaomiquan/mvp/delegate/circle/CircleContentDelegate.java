package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseMyPullDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.widget.DropDownView;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import skin.support.widget.SkinCompatToolbar;

/**
 * Created by Andy on 2018/1/19.
 */

public class CircleContentDelegate extends BaseMyPullDelegate {
    public ViewHolder viewHolder;
    int height;
    SquareLiveAdapter squareLiveAdapter;
    int mDistance = 0;

    public void setSquareLiveAdapter(SquareLiveAdapter squareLiveAdapter) {
        this.squareLiveAdapter = squareLiveAdapter;
    }

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());

        height = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_150px);
        viewHolder.layout_title_bar.getBackground().mutate().setAlpha(0);
        viewHolder.toolbar_title.setAlpha(0);
        viewHolder.swipeRefreshLayout.setProgressViewEndTarget(false, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_250px));
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.lin_tab, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_81px), viewHolder.pull_recycleview, true);
        viewHolder.nestedScrollView.setOnScrollChangeListener(new JudgeNestedScrollView.OnScrollChangeListener() {
            // 将透明度声明成局部变量用于判断
            int alpha = 0;
            int count = 0;
            float scale = 0;

            @Override
            public void onScrollChangeListener(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                viewHolder.layout_title_bar.setVisibility(View.VISIBLE);
                if (scrollY <= height) {
                    scale = (float) scrollY / height;
                    alpha = (int) (255 * scale);
                    // 随着滑动距离改变透明度
                    // Log.e("al=","="+alpha);
                    viewHolder.layout_title_bar.getBackground().mutate().setAlpha(alpha);
                    viewHolder.toolbar_title.setAlpha(scale);

                } else {
                    if (alpha < 255) {
                        // 防止频繁重复设置相同的值影响性能
                        alpha = 255;
                        viewHolder.layout_title_bar.getBackground().mutate().setAlpha(alpha);
                        viewHolder.toolbar_title.setAlpha(scale);
                    }
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_circle_content;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView iv_bg;
        public TextView tv_name;
        public TextView tv_creator;
        public TextView tv_num;
        public DropDownView lin_type;
        public DropDownView lin_time;
        public LinearLayout lin_tab;
        public RecyclerView pull_recycleview;
        public JudgeNestedScrollView nestedScrollView;
        public SwipeRefreshLayout swipeRefreshLayout;
        public AppCompatImageView civ_send;
        public View v_status;
        public IconFontTextview toolbar_back;
        public View view_back_point;
        public TextView toolbar_back_txt;
        public LinearLayout toolbar_lin_back;
        public FrameLayout fl_content;
        public IconFontTextview toolbar_subtitle;
        public View view_subtitle_point;
        public IconFontTextview toolbar_img2;
        public View view_img2_point;
        public IconFontTextview toolbar_img1;
        public View view_img1_point;
        public IconFontTextview toolbar_img;
        public View view_img_point;
        public TextView toolbar_title;
        public SkinCompatToolbar toolbar;
        public LinearLayout layout_title_bar;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_bg = (ImageView) rootView.findViewById(R.id.iv_bg);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_creator = (TextView) rootView.findViewById(R.id.tv_creator);
            this.tv_num = (TextView) rootView.findViewById(R.id.tv_num);
            this.lin_type = (DropDownView) rootView.findViewById(R.id.lin_type);
            this.lin_time = (DropDownView) rootView.findViewById(R.id.lin_time);
            this.lin_tab = (LinearLayout) rootView.findViewById(R.id.lin_tab);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.nestedScrollView = (JudgeNestedScrollView) rootView.findViewById(R.id.nestedScrollView);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.civ_send = (AppCompatImageView) rootView.findViewById(R.id.civ_send);
            this.v_status = (View) rootView.findViewById(R.id.v_status);
            this.toolbar_back = (IconFontTextview) rootView.findViewById(R.id.toolbar_back);
            this.view_back_point = (View) rootView.findViewById(R.id.view_back_point);
            this.toolbar_back_txt = (TextView) rootView.findViewById(R.id.toolbar_back_txt);
            this.toolbar_lin_back = (LinearLayout) rootView.findViewById(R.id.toolbar_lin_back);
            this.fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);
            this.toolbar_subtitle = (IconFontTextview) rootView.findViewById(R.id.toolbar_subtitle);
            this.view_subtitle_point = (View) rootView.findViewById(R.id.view_subtitle_point);
            this.toolbar_img2 = (IconFontTextview) rootView.findViewById(R.id.toolbar_img2);
            this.view_img2_point = (View) rootView.findViewById(R.id.view_img2_point);
            this.toolbar_img1 = (IconFontTextview) rootView.findViewById(R.id.toolbar_img1);
            this.view_img1_point = (View) rootView.findViewById(R.id.view_img1_point);
            this.toolbar_img = (IconFontTextview) rootView.findViewById(R.id.toolbar_img);
            this.view_img_point = (View) rootView.findViewById(R.id.view_img_point);
            this.toolbar_title = (TextView) rootView.findViewById(R.id.toolbar_title);
            this.toolbar = (SkinCompatToolbar) rootView.findViewById(R.id.toolbar);
            this.layout_title_bar = (LinearLayout) rootView.findViewById(R.id.layout_title_bar);
        }

    }
}
