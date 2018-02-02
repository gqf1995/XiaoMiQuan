package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleContentAdapter;
import com.xiaomiquan.adapter.circle.CommentAdapter;
import com.xiaomiquan.adapter.circle.CommentDetailAdapter;
import com.xiaomiquan.adapter.circle.PraiseAdapter;
import com.xiaomiquan.entity.bean.GroupOwner;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.entity.bean.circle.UserTopic;
import com.xiaomiquan.mvp.databinder.circle.CircleContentBinder;
import com.xiaomiquan.mvp.databinder.circle.TopicDetailBinder;
import com.xiaomiquan.mvp.delegate.circle.CircleContentDelegate;
import com.xiaomiquan.mvp.delegate.circle.TopicDetailDelegate;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TopicDetailActivity extends BasePullActivity<TopicDetailDelegate, TopicDetailBinder> {


    @Override
    protected Class<TopicDetailDelegate> getDelegateClass() {
        return TopicDetailDelegate.class;
    }

    @Override
    public TopicDetailBinder getDataBinder(TopicDetailDelegate viewDelegate) {
        return new TopicDetailBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle("帖子详情").setmRightImg1(""));
        addRequest(binder.getTopicContent(squareLive.getId(), TopicDetailActivity.this));
        viewDelegate.viewHolder.tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircleDialogHelper.initDefaultInputDialog(TopicDetailActivity.this, "评论", "请输入评论", "发布", new OnInputClickListener() {
                    @Override
                    public void onClick(String text, View v) {
                        addRequest(binder.saveComment(squareLive.getId(), text, TopicDetailActivity.this));
                    }
                }).show();
            }
        });
        viewDelegate.viewHolder.tv_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRequest(binder.savePraise(squareLive.getId(), TopicDetailActivity.this));
            }
        });
        viewDelegate.viewHolder.tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRequest(binder.saveComment(squareLive.getId(), viewDelegate.viewHolder.et_input2.getText().toString(), TopicDetailActivity.this));
            }
        });

    }

    @Override
    protected void clickRightIv1() {
        super.clickRightIv1();
    }

    @Override
    protected void clickRightIv2() {
        super.clickRightIv2();
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                SquareLive datas = GsonUtil.getInstance().toObj(data, SquareLive.class);
                initSquareLive(datas);
                break;
            case 0x124:
                viewDelegate.viewHolder.et_input2.setText("");
                addRequest(binder.getTopicContent(squareLive.getId(), TopicDetailActivity.this));
                break;
            case 0x125:
                addRequest(binder.getTopicContent(squareLive.getId(), TopicDetailActivity.this));
                break;
            case 0x126:
                addRequest(binder.getTopicContent(squareLive.getId(), TopicDetailActivity.this));
                break;
        }
    }


    private void initSquareLive(final SquareLive square) {

        if (square.getUserPraise().equals("false")) {
        } else {
        }
        GlideUtils.loadImage(square.getAvatar(), viewDelegate.viewHolder.cv_head);
        viewDelegate.viewHolder.tv_comment_num.setText(square.getCommentCount());
        viewDelegate.viewHolder.tv_praise_num.setText(square.getGoodCount());
        viewDelegate.viewHolder.tv_time.setText(square.getCreateTimeStr());
        viewDelegate.viewHolder.tv_con.setText(square.getContent());
        viewDelegate.viewHolder.tv_name.setText(square.getNickName());

        CommentDetailAdapter commentAdapter = new CommentDetailAdapter(mContext, square.getCommentVos());
        viewDelegate.viewHolder.rv_comment.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        commentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                CircleDialogHelper.initDefaultInputDialog(TopicDetailActivity.this, "回复", "回复" + square.getCommentVos().get(position).getNickName(), "回复", new OnInputClickListener() {
                    @Override
                    public void onClick(String text, View v) {
                        addRequest(binder.saveRecomment(
                                squareLive.getId(),
                                text,
                                square.getCommentVos().get(position).getCommentUserId(),
                                TopicDetailActivity.this));
                    }
                }).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        viewDelegate.viewHolder.rv_comment.setAdapter(commentAdapter);
    }


    @Override
    protected void refreshData() {

    }


    SquareLive squareLive;

    private void getIntentData() {
        Intent intent = getIntent();
        squareLive = (SquareLive) intent.getParcelableExtra(("squareLive"));
    }

    public static void startAct(Activity activity,
                                SquareLive squareLive
    ) {
        Intent intent = new Intent(activity, TopicDetailActivity.class);
        intent.putExtra("squareLive", squareLive);
        activity.startActivity(intent);
    }

}
