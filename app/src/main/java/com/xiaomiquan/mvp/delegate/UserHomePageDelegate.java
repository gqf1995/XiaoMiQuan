package com.xiaomiquan.mvp.delegate;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.DropDownView;
import com.xiaomiquan.widget.JudgeNestedScrollView;

public class UserHomePageDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    int height;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());

    }

    public void initToolbar() {
        height = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_210px);
        getLayoutTitleBar().getBackground().mutate().setAlpha(0);
        getmToolbarTitle().setAlpha(0);
        viewHolder.nestedScrollView.setTabAndPager(viewHolder.view_tab, (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_1px), viewHolder.fl_root, true);
        viewHolder.nestedScrollView.setOnScrollChangeListener(new JudgeNestedScrollView.OnScrollChangeListener() {
            int alpha = 0;
            float scale = 0;

            @Override
            public void onScrollChangeListener(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                getLayoutTitleBar().setVisibility(View.VISIBLE);
                if (scrollY <= height) {
                    scale = (float) scrollY / height;
                    alpha = (int) (255 * scale);
                    // 随着滑动距离改变透明度
                    // Log.e("al=","="+alpha);
                    getLayoutTitleBar().getBackground().mutate().setAlpha(alpha);
                    getmToolbarTitle().setAlpha(scale);
                } else {
                    if (alpha < 255) {
                        // 防止频繁重复设置相同的值影响性能
                        alpha = 255;
                        getLayoutTitleBar().getBackground().mutate().setAlpha(alpha);
                        getmToolbarTitle().setAlpha(scale);
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_home_page;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_user_name;
        public TextView tv_fucose_people_num;
        public DropDownView dp_theme;
        public DropDownView dp_sorting;
        public View view_tab;
        public FrameLayout fl_root;
        public JudgeNestedScrollView nestedScrollView;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_user_name = (TextView) rootView.findViewById(R.id.tv_user_name);
            this.tv_fucose_people_num = (TextView) rootView.findViewById(R.id.tv_fucose_people_num);
            this.dp_theme = (DropDownView) rootView.findViewById(R.id.dp_theme);
            this.dp_sorting = (DropDownView) rootView.findViewById(R.id.dp_sorting);
            this.view_tab = (View) rootView.findViewById(R.id.view_tab);
            this.fl_root = (FrameLayout) rootView.findViewById(R.id.fl_root);
            this.nestedScrollView = (JudgeNestedScrollView) rootView.findViewById(R.id.nestedScrollView);
        }

    }
}