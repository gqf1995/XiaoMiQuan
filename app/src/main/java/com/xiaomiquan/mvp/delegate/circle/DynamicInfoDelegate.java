package com.xiaomiquan.mvp.delegate.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.base.BasePullDelegate;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;

import de.hdodenhof.circleimageview.CircleImageView;
import skin.support.widget.SkinCompatImageView;
import skin.support.widget.SkinCompatLinearLayout;

public class DynamicInfoDelegate extends BasePullDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.public_pull_recycleview;
    }

    public static class ViewHolder {
        public View rootView;
        public RecyclerView pull_recycleview;
        public FrameLayout fl_rcv;
        public SwipeRefreshLayout swipeRefreshLayout;
        public SkinCompatLinearLayout fl_pull;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.pull_recycleview = (RecyclerView) rootView.findViewById(R.id.pull_recycleview);
            this.fl_rcv = (FrameLayout) rootView.findViewById(R.id.fl_rcv);
            this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            this.fl_pull = (SkinCompatLinearLayout) rootView.findViewById(R.id.fl_pull);
        }

    }


    /**
     * 详情头部
     */
    public CircleImageView cv_head;
    public TextView tv_name;
    public TextView tv_type_time;
    public IconFontTextview icf_more;
    public TextView tv_reason;
    public RecyclerView recycleview_reason;
    public IconFontTextview icf_shared;
    public TextView tv_shared_num;
    public LinearLayout lin_shared;
    public IconFontTextview tv_comment;
    public TextView tv_comment_num;
    public LinearLayout lin_comment;
    public IconFontTextview tv_praise;
    public TextView tv_praise_num;
    public LinearLayout lin_praise;
    public LinearLayout lin_root;
    public TextView tv_comment_member;

    public View initTop() {
        View rootView = this.getActivity().getLayoutInflater().inflate(R.layout.layout_dynamic_info_top, null);
        this.cv_head = (CircleImageView) rootView.findViewById(R.id.cv_head);
        this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        this.tv_type_time = (TextView) rootView.findViewById(R.id.tv_type_time);
        this.icf_more = (IconFontTextview) rootView.findViewById(R.id.icf_more);
        this.tv_reason = (TextView) rootView.findViewById(R.id.tv_reason);
        this.recycleview_reason = (RecyclerView) rootView.findViewById(R.id.recycleview_reason);
        this.icf_shared = (IconFontTextview) rootView.findViewById(R.id.icf_shared);
        this.tv_shared_num = (TextView) rootView.findViewById(R.id.tv_shared_num);
        this.lin_shared = (LinearLayout) rootView.findViewById(R.id.lin_shared);
        this.tv_comment = (IconFontTextview) rootView.findViewById(R.id.tv_comment);
        this.tv_comment_num = (TextView) rootView.findViewById(R.id.tv_comment_num);
        this.lin_comment = (LinearLayout) rootView.findViewById(R.id.lin_comment);
        this.tv_praise = (IconFontTextview) rootView.findViewById(R.id.tv_praise);
        this.tv_praise_num = (TextView) rootView.findViewById(R.id.tv_praise_num);
        this.lin_praise = (LinearLayout) rootView.findViewById(R.id.lin_praise);
        this.lin_root = (LinearLayout) rootView.findViewById(R.id.lin_root);
        this.tv_comment_member = (TextView) rootView.findViewById(R.id.tv_comment_member);
        return rootView;
    }

    /**
     * 文章
     */
    public ImageView iv_article;
    public TextView tv_article_title;
    public LinearLayout lin_article;

    public View initArticle() {
        View rootView = this.getActivity().getLayoutInflater().inflate(R.layout.layout_square_article, null);
        this.rootView = rootView;
        this.iv_article = (ImageView) rootView.findViewById(R.id.iv_article);
        this.tv_article_title = (TextView) rootView.findViewById(R.id.tv_article_title);
        this.lin_article = (LinearLayout) rootView.findViewById(R.id.lin_article);
        return rootView;
    }

    /**
     * 预测
     */
    public TextView tv_forecast_time;
    public TextView tv_forecast_info;
    public TextView tv_forecast_result;
    public LinearLayout lin_forecast;

    public View initForecast() {
        View rootView = this.getActivity().getLayoutInflater().inflate(R.layout.layout_square_forecast, null);
        this.tv_forecast_time = (TextView) rootView.findViewById(R.id.tv_forecast_time);
        this.tv_forecast_info = (TextView) rootView.findViewById(R.id.tv_forecast_info);
        this.tv_forecast_result = (TextView) rootView.findViewById(R.id.tv_forecast_result);
        this.lin_forecast = (LinearLayout) rootView.findViewById(R.id.lin_forecast);
        return rootView;
    }

    /**
     * 分享
     */

    public CircleImageView cv_shard_head;
    public TextView tv_shared_name;
    public TextView icf_shared_attention;
    public TextView tv_shared_brief;
    public SkinCompatImageView iv_shared;
    public LinearLayout lin_shared_topic;

    public View initShared() {
        View rootView = this.getActivity().getLayoutInflater().inflate(R.layout.layout_square_shared, null);
        this.cv_shard_head = (CircleImageView) rootView.findViewById(R.id.cv_shard_head);
        this.tv_shared_name = (TextView) rootView.findViewById(R.id.tv_shared_name);
        this.icf_shared_attention = (TextView) rootView.findViewById(R.id.icf_shared_attention);
        this.tv_shared_brief = (TextView) rootView.findViewById(R.id.tv_shared_brief);
        this.iv_shared = (SkinCompatImageView) rootView.findViewById(R.id.iv_shared);
        this.lin_shared_topic = (LinearLayout) rootView.findViewById(R.id.lin_shared_topic);
        return rootView;
    }


    /**
     * 话题
     */
    public TextView tv_title;
    public TextView tv_topic_brief;
    public ImageView iv_topic;
    public LinearLayout lin_topic;

    public View initTopic() {
        View rootView = this.getActivity().getLayoutInflater().inflate(R.layout.layout_square_topic, null);
        this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        this.tv_topic_brief = (TextView) rootView.findViewById(R.id.tv_topic_brief);
        this.iv_topic = (ImageView) rootView.findViewById(R.id.iv_topic);
        this.lin_topic = (LinearLayout) rootView.findViewById(R.id.lin_topic);
        return rootView;
    }
}