package com.xiaomiquan.mvp.delegate;

import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.widget.JudgeNestedScrollView;
import com.xiaomiquan.widget.StickyScrollView;

import de.hdodenhof.circleimageview.CircleImageView;
import skin.support.widget.SkinCompatToolbar;

public class PersonalHomePageDelegate extends BaseDelegate {
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
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.lin_tab, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_81px), viewHolder.recycler_view, true);
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
        viewHolder.recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDistance += dy;
                if (squareLiveAdapter != null) {
                    if (squareLiveAdapter.getDatas().size() > 0) {
                        long intemHeight = viewHolder.recycler_view.getLayoutManager().getHeight() / squareLiveAdapter.getDatas().size();
                        int index = (int) (mDistance / intemHeight);
                        if (index < squareLiveAdapter.getDatas().size()) {
                            viewHolder.tv_time_live.setText(squareLiveAdapter.getDatas().get(index).getYearMonthDay());
                        }
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_home_page;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView iv_bg;
        public TextView tv_name;
        public TextView tv_is_focuse;
        public TextView tv_fans_num;
        public TextView tv_focuse_num;
        public TextView tv_high_quality;
        public TextView tv_free_subscription;
        public TextView tv_my_subscribe;
        public LinearLayout lin_management;
        public CircleImageView ic_pic;
        public TextView tv_title_group;
        public LinearLayout lin_group;
        public RecyclerView rv_group;
        public TextView tv_title_circle;
        public RecyclerView rv_circle;
        public FrameLayout fl_top;
        public TextView tv_live_title;
        public IconFontTextview tv_time_live;
        public LinearLayout lin_tab;
        public RecyclerView recycler_view;
        public StickyScrollView nestedScrollView;
        public SwipeRefreshLayout swipeRefreshLayout;
        public View v_status;
        public IconFontTextview toolbar_back;
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
            this.tv_is_focuse = (TextView) rootView.findViewById(R.id.tv_is_focuse);
            this.tv_fans_num = (TextView) rootView.findViewById(R.id.tv_fans_num);
            this.tv_focuse_num = (TextView) rootView.findViewById(R.id.tv_focuse_num);
            this.tv_high_quality = (TextView) rootView.findViewById(R.id.tv_high_quality);
            this.tv_free_subscription = (TextView) rootView.findViewById(R.id.tv_free_subscription);
            this.tv_my_subscribe = (TextView) rootView.findViewById(R.id.tv_my_subscribe);
            this.lin_management = (LinearLayout) rootView.findViewById(R.id.lin_management);
            this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
            this.tv_title_group = (TextView) rootView.findViewById(R.id.tv_title_group);
            this.lin_group = (LinearLayout) rootView.findViewById(R.id.lin_group);
            this.rv_group = (RecyclerView) rootView.findViewById(R.id.rv_group);
            this.tv_title_circle = (TextView) rootView.findViewById(R.id.tv_title_circle);
            this.rv_circle = (RecyclerView) rootView.findViewById(R.id.rv_circle);
            this.fl_top = (FrameLayout) rootView.findViewById(R.id.fl_top);
            this.tv_live_title = (TextView) rootView.findViewById(R.id.tv_live_title);
            this.tv_time_live = (IconFontTextview) rootView.findViewById(R.id.tv_time_live);
            this.lin_tab = (LinearLayout) rootView.findViewById(R.id.lin_tab);
            this.recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            this.nestedScrollView = (StickyScrollView) rootView.findViewById(R.id.nestedScrollView);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.v_status = (View) rootView.findViewById(R.id.v_status);
            this.toolbar_back = (IconFontTextview) rootView.findViewById(R.id.toolbar_back);
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