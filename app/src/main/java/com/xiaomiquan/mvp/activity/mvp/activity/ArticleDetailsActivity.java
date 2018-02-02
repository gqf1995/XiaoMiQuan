package com.xiaomiquan.mvp.activity.mvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.adapter.circle.CommentDetailAdapter;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.mvp.activity.circle.TopicDetailActivity;
import com.xiaomiquan.mvp.activity.mvp.databinder.ArticleDetailsBinder;
import com.xiaomiquan.mvp.activity.mvp.delegate.ArticleDetailsDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

public class ArticleDetailsActivity extends BaseDataBindActivity<ArticleDetailsDelegate, ArticleDetailsBinder> {

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
        initToolbar(new ToolbarBuilder().setTitle("文章详情"));
        getIntentData();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
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
                addRequest(binder.getTopicContent(squareLive.getId(), ArticleDetailsActivity.this));
                break;
            case 0x126:
                addRequest(binder.getTopicContent(squareLive.getId(), ArticleDetailsActivity.this));
                break;
        }
    }

    private void initSquareLive(final SquareLive square) {

        if (square.getUserPraise().equals("false")) {
        } else {
        }
        GlideUtils.loadImage(square.getImg(), viewDelegate.viewHolder.iv_banner);
        GlideUtils.loadImage(square.getAvatar(), viewDelegate.viewHolder.cv_head);
        viewDelegate.viewHolder.tv_comment_num.setText(square.getCommentCount());
        viewDelegate.viewHolder.tv_praise_num.setText(square.getGoodCount());
        viewDelegate.viewHolder.tv_time.setText(square.getCreateTimeStr());
        viewDelegate.viewHolder.tv_con.setText(square.getContent());
        viewDelegate.viewHolder.tv_name.setText(square.getNickName());
        viewDelegate.viewHolder.tv_title.setText(square.getTitle());

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
                CircleDialogHelper.initDefaultInputDialog(ArticleDetailsActivity.this, "回复", "回复" + square.getCommentVos().get(position).getNickName(), "回复", new OnInputClickListener() {
                    @Override
                    public void onClick(String text, View v) {
                        addRequest(binder.saveRecomment(
                                squareLive.getId(),
                                text,
                                square.getCommentVos().get(position).getCommentUserId(),
                                ArticleDetailsActivity.this));
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

    SquareLive squareLive;

    private void getIntentData() {
        Intent intent = getIntent();
        squareLive = (SquareLive) intent.getSerializableExtra("squareLive");
    }

    public static void startAct(Activity activity,
                                SquareLive squareLive
    ) {
        Intent intent = new Intent(activity, TopicDetailActivity.class);
        intent.putExtra("squareLive", squareLive);
        activity.startActivity(intent);
    }

}
