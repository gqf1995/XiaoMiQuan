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

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CommentDetailAdapter;
import com.xiaomiquan.entity.bean.circle.Comment;
import com.xiaomiquan.entity.bean.circle.CommentRecode;
import com.xiaomiquan.entity.bean.circle.Praise;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.mvp.databinder.circle.ArticleDetailsBinder;
import com.xiaomiquan.mvp.delegate.circle.ArticleDetailsDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.xiaomiquan.widget.circle.CommentPopupWindow;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

public class ArticleDetailsActivity extends BasePullActivity<ArticleDetailsDelegate, ArticleDetailsBinder> {

    @Override
    protected Class<ArticleDetailsDelegate> getDelegateClass() {
        return ArticleDetailsDelegate.class;
    }

    @Override
    public ArticleDetailsBinder getDataBinder(ArticleDetailsDelegate viewDelegate) {
        return new ArticleDetailsBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_article)));
        getIntentData();
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getTopicContent(squareLive.getId(), ArticleDetailsActivity.this));
            }
        });
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
                addRequest(binder.getTopicContent(squareLive.getId(), ArticleDetailsActivity.this));
                break;
            case 0x125:
                addRequest(binder.getComment(squareLive.getId(), ArticleDetailsActivity.this));
                break;
            case 0x126:
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
        viewDelegate.viewHolder.tv_comment.setOnClickListener(this);
        viewDelegate.viewHolder.lin_praise.setOnClickListener(this);
        viewDelegate.viewHolder.tv_commit.setOnClickListener(this);
        viewDelegate.viewHolder.et_input2.setOnClickListener(this);
        viewDelegate.viewHolder.lin_comment.setOnClickListener(this);
        GlideUtils.loadImage(square.getImg(), viewDelegate.viewHolder.iv_banner);
        GlideUtils.loadImage(square.getAvatar(), viewDelegate.viewHolder.cv_head);
        viewDelegate.viewHolder.tv_comment_num.setText(square.getCommentCount() + "");
        viewDelegate.viewHolder.tv_praise_num.setText(square.getGoodCount() + "");
        viewDelegate.viewHolder.tv_time.setText(square.getCreateTimeStr());
        viewDelegate.viewHolder.tv_con.setText(Html.fromHtml(square.getContent()));
        viewDelegate.viewHolder.tv_name.setText(square.getNickName());
        viewDelegate.viewHolder.tv_title.setText(square.getTitle());

        initComment(square.getCommentVos());
    }

    CommentDetailAdapter commentAdapter;

    private void initComment(final List<Comment> comment) {

        if (commentAdapter == null) {
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            commentAdapter = new CommentDetailAdapter(mContext, comment);
            viewDelegate.viewHolder.rv_comment.setLayoutManager(new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            commentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                    initPop(false, comment.get(position).getCommentUserId(), comment.get(position).getNickName());
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            viewDelegate.viewHolder.rv_comment.setAdapter(commentAdapter);
        } else {
            commentAdapter.setDatas(comment);
        }
    }

    SquareLive squareLive;

    private void getIntentData() {
        Intent intent = getIntent();
        squareLive = (SquareLive) intent.getParcelableExtra("squareLive");
        initSquareLive(squareLive);
    }

    public static void startAct(Activity activity,
                                SquareLive squareLive
    ) {
        Intent intent = new Intent(activity, ArticleDetailsActivity.class);
        intent.putExtra("squareLive", squareLive);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_praise:
                if (squareLive.isUserPraise()) {
                    squareLive.setUserPraise(false);
                    squareLive.setGoodCount(squareLive.getGoodCount() - 1);
                    viewDelegate.viewHolder.tv_praise_num.setText(squareLive.getGoodCount() + "");
                    viewDelegate.viewHolder.tv_praise.setTextColor(CommonUtils.getColor(R.color.color_font1));
                    viewDelegate.viewHolder.tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_font1));
                } else {
                    squareLive.setUserPraise(true);
                    squareLive.setGoodCount(squareLive.getGoodCount() + 1);
                    viewDelegate.viewHolder.tv_praise_num.setText(squareLive.getGoodCount() + "");
                    viewDelegate.viewHolder.tv_praise.setTextColor(CommonUtils.getColor(R.color.color_blue));
                    viewDelegate.viewHolder.tv_praise_num.setTextColor(CommonUtils.getColor(R.color.color_blue));
                }
                addRequest(binder.savePraise(squareLive.getId(), ArticleDetailsActivity.this));
                break;
            case R.id.tv_comment:
                initPop(true, "", "");
                break;
            case R.id.tv_commit:
                initPop(true, "", "");
                break;
            case R.id.et_input2:
                initPop(true, "", "");
                break;
            case R.id.lin_comment:
                initPop(true, "", "");
                break;
        }
    }

    private void initPop(final Boolean comment, final String reId, final String reName) {
        final CommentPopupWindow commentPopupWindow = new CommentPopupWindow(ArticleDetailsActivity.this);
        commentPopupWindow.setOnItemClickListener(new CommentPopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                if (comment) {

                    addRequest(binder.saveComment(squareLive.getId(), commentPopupWindow.et_input2.getText().toString(), ArticleDetailsActivity.this));
                } else {

                    addRequest(binder.saveRecomment(
                            squareLive.getId(),
                            commentPopupWindow.et_input2.getText().toString(),
                            reId,
                            ArticleDetailsActivity.this));
                }
                commentPopupWindow.et_input2.setText(null);
            }
        });
        if (comment) {
            commentPopupWindow.et_input2.setHint(CommonUtils.getString(R.string.str_tv_sub));
        } else {
            commentPopupWindow.et_input2.setHint(CommonUtils.getString(R.string.str_tv_recomment) + reName);
        }
        commentPopupWindow.showAtLocation(viewDelegate.viewHolder.lin_comment, Gravity.BOTTOM, 0, 0);
        //弹出键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getTopicContent(squareLive.getId(), ArticleDetailsActivity.this));
    }
}
