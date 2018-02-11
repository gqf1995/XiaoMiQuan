package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.xiaomiquan.R;
import com.xiaomiquan.widget.JudgeNestedScrollView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticleDetailsDelegate extends BasePullDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.scrollView_scroll.setTabAndPager(viewHolder.lin_tab, 0, viewHolder.pull_recycleview, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_details;
    }


    public static class ViewHolder {
        public View rootView;

        public ImageView iv_banner;
        public TextView tv_title;
        public CircleImageView cv_head;
        public TextView tv_name;
        public TextView tv_time;
        public LinearLayout lin_userinfo;
        public TextView tv_con;
        public IconFontTextview tv_praise;
        public TextView tv_praise_num;
        public LinearLayout lin_praise;
        public IconFontTextview tv_comment;
        public TextView tv_comment_num;
        public LinearLayout lin_info_comment;
        public IconFontTextview icf_shared;
        public TextView tv_shared;
        public LinearLayout lin_shared;
        public LinearLayout lin_tab;
        public TextView tv_praise_person;
        public RecyclerView pull_recycleview;
        public JudgeNestedScrollView scrollView_scroll;
        public SwipeRefreshLayout swipeRefreshLayout;
        public MaterialEditText et_input2;
        public TextView tv_commit;
        public LinearLayout lin_comment;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.iv_banner = (ImageView) rootView.findViewById(R.id.iv_banner);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.cv_head = (CircleImageView) rootView.findViewById(R.id.cv_head);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.lin_userinfo = (LinearLayout) rootView.findViewById(R.id.lin_userinfo);
            this.tv_con = (TextView) rootView.findViewById(R.id.tv_con);
            this.tv_praise = (IconFontTextview) rootView.findViewById(R.id.tv_praise);
            this.tv_praise_num = (TextView) rootView.findViewById(R.id.tv_praise_num);
            this.lin_praise = (LinearLayout) rootView.findViewById(R.id.lin_praise);
            this.tv_comment = (IconFontTextview) rootView.findViewById(R.id.tv_comment);
            this.tv_comment_num = (TextView) rootView.findViewById(R.id.tv_comment_num);
            this.lin_info_comment = (LinearLayout) rootView.findViewById(R.id.lin_info_comment);
            this.icf_shared = (IconFontTextview) rootView.findViewById(R.id.icf_shared);
            this.tv_shared = (TextView) rootView.findViewById(R.id.tv_shared);
            this.lin_shared = (LinearLayout) rootView.findViewById(R.id.lin_shared);
            this.lin_tab = (LinearLayout) rootView.findViewById(R.id.lin_tab);
            this.tv_praise_person = (TextView) rootView.findViewById(R.id.tv_praise_person);
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.scrollView_scroll = (JudgeNestedScrollView) rootView.findViewById(R.id.scrollView_scroll);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.et_input2 = (MaterialEditText) rootView.findViewById(R.id.et_input2);
            this.tv_commit = (TextView) rootView.findViewById(R.id.tv_commit);
            this.lin_comment = (LinearLayout) rootView.findViewById(R.id.lin_comment);
        }

    }
}