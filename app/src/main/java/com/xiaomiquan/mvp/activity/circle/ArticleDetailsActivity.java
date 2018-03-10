package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CommentDetailAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.circle.Comment;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.user.PersonalHomePageActivity;
import com.xiaomiquan.mvp.databinder.circle.ArticleDetailsBinder;
import com.xiaomiquan.mvp.delegate.circle.ArticleDetailsDelegate;
import com.xiaomiquan.widget.circle.CommentPopupWindow;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

public class ArticleDetailsActivity extends BasePullActivity<ArticleDetailsDelegate, ArticleDetailsBinder> {

    @Override
    protected Class<ArticleDetailsDelegate> getDelegateClass() {
        return ArticleDetailsDelegate.class;
    }

    UserLogin userLogin;

    @Override
    public ArticleDetailsBinder getDataBinder(ArticleDetailsDelegate viewDelegate) {
        return new ArticleDetailsBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_article)));
        userLogin = SingSettingDBUtil.getUserLogin();
        getIntentData();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                SquareLive datas = GsonUtil.getInstance().toObj(data, SquareLive.class);
                initSquareLive(datas);
                squareLive = datas;
                break;
            case 0x124:
//                viewDelegate.viewHolder.et_input2.setText("");
//                addRequest(binder.getTopicContent(squareLive.getId(), ArticleDetailsActivity.this));
                break;
            case 0x125:
//                addRequest(binder.getComment(squareLive.getId(), ArticleDetailsActivity.this));
                break;
            case 0x126:
                break;
            case 0x127:
                List<Comment> comments = GsonUtil.getInstance().toList(data, Comment.class);
                initComment(comments);
//                viewDelegate.viewHolder.tv_comment_num.setText(comments.size() + "");
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
        viewDelegate.viewHolder.lin_info_comment.setOnClickListener(this);
        viewDelegate.viewHolder.lin_praise.setOnClickListener(this);
        viewDelegate.viewHolder.tv_commit.setOnClickListener(this);
        viewDelegate.viewHolder.et_input2.setOnClickListener(this);
        viewDelegate.viewHolder.lin_comment.setOnClickListener(this);
        viewDelegate.viewHolder.lin_userinfo.setOnClickListener(this);
        GlideUtils.loadImage(square.getImg(), viewDelegate.viewHolder.iv_banner);
        GlideUtils.loadImage(square.getAvatar(), viewDelegate.viewHolder.cv_head);
        viewDelegate.viewHolder.tv_comment_num.setText(square.getCommentCount() + "");
        viewDelegate.viewHolder.tv_praise_num.setText(square.getGoodCount() + "");
        viewDelegate.viewHolder.tv_time.setText(square.getCreateTimeStr());
        viewDelegate.viewHolder.tv_con.setText(Html.fromHtml(square.getContent()));
        viewDelegate.viewHolder.tv_name.setText(square.getNickName());
        viewDelegate.viewHolder.tv_title.setText(square.getTitle());

    }

    CommentDetailAdapter commentAdapter;

    private void initComment(final List<Comment> comment) {
        if (commentAdapter == null) {
            commentAdapter = new CommentDetailAdapter(mContext, comment);
            commentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                    if (userLogin != null) {
                        initPop(false, comment.get(position).getCommentUserId(), comment.get(position).getNickName());
                    } else {
                        ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            commentAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    PersonalHomePageActivity.startAct(ArticleDetailsActivity.this, comment.get(position).getCommentUserId());
                }
            });
            viewDelegate.viewHolder.pull_recycleview.getItemAnimator().setChangeDuration(0);
//            viewDelegate.viewHolder.rv_comment.setAdapter(commentAdapter);
            initRecycleViewPull(commentAdapter, new LinearLayoutManager(mContext));
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        } else {
            getDataBack(commentAdapter.getDatas(), comment, commentAdapter);
        }
    }

    SquareLive squareLive;

    private void getIntentData() {

        Intent intent = getIntent();
        squareLive = (SquareLive) intent.getParcelableExtra("squareLive");
        initSquareLive(squareLive);
        initComment(squareLive.getCommentVos());
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

            case R.id.lin_userinfo:
                PersonalHomePageActivity.startAct(ArticleDetailsActivity.this, squareLive.getUserId());
                break;
        }
        if (userLogin != null) {
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
                case R.id.lin_info_comment:
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
        } else {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
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
        if (userLogin != null) {
            addRequest(binder.getComment(squareLive.getId(), ArticleDetailsActivity.this));
        } else {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        }
    }
}
