package com.xiaomiquan.mvp.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;

public class HomePageDelegate extends BaseActivityPullDelegate {
    int height;
    int mDistance = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_page;
    }

    public void initToplinsener(int h) {
        height = h;
        getLayoutTitleBar().getBackground().mutate().setAlpha(0);
        getmToolbarTitle().setAlpha(0);
        viewHolder.swipeRefreshLayout.setProgressViewEndTarget(false, 2 * (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_110px));
        viewHolder.pull_recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDistance += dy;
                float scale = mDistance * 1f / height;//百分比
                int alpha = (int) (scale * 255);
                getLayoutTitleBar().setVisibility(View.VISIBLE);

                if (mDistance <= height) {
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

}