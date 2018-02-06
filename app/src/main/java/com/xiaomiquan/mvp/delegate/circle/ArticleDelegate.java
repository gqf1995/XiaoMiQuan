package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticleDelegate extends BasePullDelegate {
    public ViewHolder viewHolder;
    int height;
    int mDistance = 0;


    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_time;
        public TextView tv_title;
        public CircleImageView iv_head;
        public ImageView iv_banner;
        public TextView tv_name;
        public IconFontTextview tv_comment;
        public TextView tv_comment_num;
        public IconFontTextview tv_praise;
        public TextView tv_praise_num;
        public RecyclerView pull_recycleview;
        public SwipeRefreshLayout swipeRefreshLayout;
        public LinearLayout lin_praise;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.iv_head = (CircleImageView) rootView.findViewById(R.id.iv_head);
            this.iv_banner = (ImageView) rootView.findViewById(R.id.iv_banner);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_comment = (IconFontTextview) rootView.findViewById(R.id.tv_comment);
            this.tv_comment_num = (TextView) rootView.findViewById(R.id.tv_comment_num);
            this.tv_praise = (IconFontTextview) rootView.findViewById(R.id.tv_praise);
            this.tv_praise_num = (TextView) rootView.findViewById(R.id.tv_praise_num);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.lin_praise = (LinearLayout) rootView.findViewById(R.id.lin_praise);
        }

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