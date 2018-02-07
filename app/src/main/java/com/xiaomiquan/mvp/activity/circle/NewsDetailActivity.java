package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CommentDetailAdapter;
import com.xiaomiquan.entity.bean.circle.Comment;
import com.xiaomiquan.entity.bean.circle.Praise;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.mvp.databinder.circle.TopicDetailBinder;
import com.xiaomiquan.mvp.delegate.circle.TopicDetailDelegate;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.xiaomiquan.widget.circle.CommentPopupWindow;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;


public class NewsDetailActivity extends BasePullActivity<TopicDetailDelegate, TopicDetailBinder> {


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
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_news)));
        addRequest(binder.getTopicContent(squareLive.getId(), NewsDetailActivity.this));

        viewDelegate.viewHolder.tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPop(true, "", "");
            }
        });
        viewDelegate.viewHolder.tv_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRequest(binder.savePraise(squareLive.getId(), NewsDetailActivity.this));
            }
        });
        viewDelegate.viewHolder.tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPop(true, "", "");
            }
        });
        viewDelegate.viewHolder.et_input2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPop(true, "", "");
            }
        });
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getComment(squareLive.getId(), NewsDetailActivity.this));
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
                addRequest(binder.getComment(squareLive.getId(), NewsDetailActivity.this));
                break;
            case 0x125:
                addRequest(binder.getComment(squareLive.getId(), NewsDetailActivity.this));
                break;
            case 0x126:
                Praise praise = GsonUtil.getInstance().toObj(data, Praise.class);
                if (praise.getIspraise()) {
                    viewDelegate.viewHolder.tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
                    viewDelegate.viewHolder.tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
                } else {
                    viewDelegate.viewHolder.tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font1));
                    viewDelegate.viewHolder.tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
                }
                viewDelegate.viewHolder.tv_praise_num.setText(praise.getPraiseQty());
                break;
            case 0x127:
                List<Comment> comments = GsonUtil.getInstance().toList(data, Comment.class);
                initComment(comments);
                viewDelegate.viewHolder.tv_comment_num.setText(comments.size() + "");
                break;
        }
    }


    private void initSquareLive(final SquareLive square) {
        if (square.isUserPraise()) {
            viewDelegate.viewHolder.tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
            viewDelegate.viewHolder.tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));

        } else {
            viewDelegate.viewHolder.tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font1));
            viewDelegate.viewHolder.tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
        }

        GlideUtils.loadImage(square.getAvatar(), viewDelegate.viewHolder.cv_head);
        viewDelegate.viewHolder.tv_comment_num.setText(square.getCommentCount() + "");
        viewDelegate.viewHolder.tv_praise_num.setText(square.getGoodCount() + "");
        viewDelegate.viewHolder.tv_time.setText(square.getCreateTimeStr());
        viewDelegate.viewHolder.tv_con.setText(Html.fromHtml(square.getContent()));

        viewDelegate.viewHolder.tv_name.setText(square.getNickName());
        initComment(square.getCommentVos());
    }

    private void initComment(final List<Comment> comments) {
        CommentDetailAdapter commentAdapter = new CommentDetailAdapter(mContext, comments);
        viewDelegate.viewHolder.rv_comment.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        commentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                initPop(false, comments.get(position).getCommentUserId(), comments.get(position).getNickName());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        viewDelegate.viewHolder.rv_comment.setAdapter(commentAdapter);
    }

    private void initPop(final Boolean comment, final String reId, final String reName) {
        final CommentPopupWindow commentPopupWindow = new CommentPopupWindow(NewsDetailActivity.this);
        commentPopupWindow.setOnItemClickListener(new CommentPopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                if (comment) {

                    addRequest(binder.saveComment(squareLive.getId(), commentPopupWindow.et_input2.getText().toString(), NewsDetailActivity.this));
                } else {

                    addRequest(binder.saveRecomment(
                            squareLive.getId(),
                            commentPopupWindow.et_input2.getText().toString(),
                            reId,
                            NewsDetailActivity.this));
                }
            }
        });
        if (comment) {
            commentPopupWindow.et_input2.setHint("评论");
        } else {
            commentPopupWindow.et_input2.setHint("回复" + reName);
        }
        commentPopupWindow.showAtLocation(viewDelegate.viewHolder.lin_comment, Gravity.BOTTOM, 0, 0);
        //弹出键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getComment(squareLive.getId(), NewsDetailActivity.this));
    }

    SquareLive squareLive;

    private void getIntentData() {
        Intent intent = getIntent();
        squareLive = (SquareLive) intent.getParcelableExtra(("squareLive"));
    }

    public static void startAct(Activity activity,
                                SquareLive squareLive
    ) {
        Intent intent = new Intent(activity, NewsDetailActivity.class);
        intent.putExtra("squareLive", squareLive);
        activity.startActivity(intent);
    }

}
