package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.base.BaseMyPullDelegate;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticleDelegate extends BasePullDelegate {
    public ViewHolder viewHolder;

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
        public TextView tv_name;
        public IconFontTextview tv_comment;
        public TextView tv_comment_num;
        public IconFontTextview tv_praise;
        public TextView tv_praise_num;
        public RecyclerView pull_recycleview;
        public SwipeRefreshLayout swipeRefreshLayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.iv_head = (CircleImageView) rootView.findViewById(R.id.iv_head);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_comment = (IconFontTextview) rootView.findViewById(R.id.tv_comment);
            this.tv_comment_num = (TextView) rootView.findViewById(R.id.tv_comment_num);
            this.tv_praise = (IconFontTextview) rootView.findViewById(R.id.tv_praise);
            this.tv_praise_num = (TextView) rootView.findViewById(R.id.tv_praise_num);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        }

    }
}