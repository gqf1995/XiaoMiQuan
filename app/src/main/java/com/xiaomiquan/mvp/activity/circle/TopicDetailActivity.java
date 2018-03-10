package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
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
import com.xiaomiquan.adapter.circle.DynamicPhotoAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.circle.Comment;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.user.PersonalHomePageActivity;
import com.xiaomiquan.mvp.databinder.circle.TopicDetailBinder;
import com.xiaomiquan.mvp.delegate.circle.TopicDetailDelegate;
import com.xiaomiquan.widget.circle.CommentPopupWindow;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.api.widget.Widget;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

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

    UserLogin userLogin;


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        userLogin = SingSettingDBUtil.getUserLogin();
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_topic)));
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
//                viewDelegate.viewHolder.et_input2.setText("");
//                addRequest(binder.getComment(squareLive.getId(), TopicDetailActivity.this));
                break;
            case 0x125:
//                addRequest(binder.getComment(squareLive.getId(), TopicDetailActivity.this));
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
        viewDelegate.viewHolder.cv_head.setOnClickListener(this);

        GlideUtils.loadImage(square.getAvatar(), viewDelegate.viewHolder.cv_head);
        viewDelegate.viewHolder.tv_comment_num.setText(square.getCommentCount() + "");
        viewDelegate.viewHolder.tv_praise_num.setText(square.getGoodCount() + "");
        viewDelegate.viewHolder.tv_time.setText(square.getCreateTimeStr());
        viewDelegate.viewHolder.tv_con.setText(Html.fromHtml(square.getContent()));
        viewDelegate.viewHolder.tv_name.setText(square.getNickName());
    }

    DynamicPhotoAdapter dynamicPhotoAdapter;

    private void initImgs(final List<String> stringList) {
        if (dynamicPhotoAdapter == null) {
            dynamicPhotoAdapter = new DynamicPhotoAdapter(TopicDetailActivity.this, stringList);
            dynamicPhotoAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    //图片预览
                    Album.gallery(TopicDetailActivity.this)
                            .widget(Widget.newDarkBuilder(TopicDetailActivity.this).title(CommonUtils.getString(R.string.str_img_preview)).build())
                            .checkedList((ArrayList<String>) stringList) // 要浏览的图片列表：ArrayList<String>。
                            .navigationAlpha(50) // Android5.0+的虚拟导航栏的透明度。
                            .checkable(false)
                            .start(); // 千万不要忘记调用start()方法。
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            GridLayoutManager gridLayoutManager = new GridLayoutManager(TopicDetailActivity.this, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            viewDelegate.viewHolder.rv_img.setLayoutManager(gridLayoutManager);
            viewDelegate.viewHolder.rv_img.setAdapter(dynamicPhotoAdapter);
        } else {
            dynamicPhotoAdapter.setDatas(stringList);
        }

    }

    CommentDetailAdapter commentAdapter;

    private void initComment(final List<Comment> comments) {
        if (commentAdapter == null) {
            commentAdapter = new CommentDetailAdapter(mContext, comments);
            commentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    if (userLogin != null) {
                        initPop(false, comments.get(position).getCommentUserId(), comments.get(position).getNickName());
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
                    PersonalHomePageActivity.startAct(TopicDetailActivity.this, comments.get(position).getCommentUserId());
                }
            });
            viewDelegate.viewHolder.pull_recycleview.getItemAnimator().setChangeDuration(0);
//            viewDelegate.viewHolder.pull_recycleview.setAdapter(commentAdapter);
            initRecycleViewPull(commentAdapter, new LinearLayoutManager(mContext));
        } else {
            getDataBack(commentAdapter.getDatas(), comments, commentAdapter);
        }
    }

    private void initPop(final Boolean comment, final String reId, final String reName) {
        final CommentPopupWindow commentPopupWindow = new CommentPopupWindow(TopicDetailActivity.this);
        commentPopupWindow.setOnItemClickListener(new CommentPopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                if (comment) {
                    addRequest(binder.saveComment(squareLive.getId(), commentPopupWindow.et_input2.getText().toString(), TopicDetailActivity.this));
                } else {

                    addRequest(binder.saveRecomment(
                            squareLive.getId(),
                            commentPopupWindow.et_input2.getText().toString(),
                            reId,
                            TopicDetailActivity.this));
                }
                commentPopupWindow.et_input2.setText(null);
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
        if (userLogin != null) {
            addRequest(binder.getComment(squareLive.getId(), TopicDetailActivity.this));
        } else {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        }

    }

    SquareLive squareLive;

    private void getIntentData() {
        Intent intent = getIntent();
        squareLive = (SquareLive) intent.getParcelableExtra(("squareLive"));

        initComment(squareLive.getCommentVos());
        initImgs(squareLive.getImgList());
        initSquareLive(squareLive);
    }

    public static void startAct(Activity activity,
                                SquareLive squareLive
    ) {
        Intent intent = new Intent(activity, TopicDetailActivity.class);
        intent.putExtra("squareLive", squareLive);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
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
                    addRequest(binder.savePraise(squareLive.getId(), TopicDetailActivity.this));
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
        switch (v.getId()) {
            case R.id.cv_head:
                PersonalHomePageActivity.startAct(TopicDetailActivity.this, squareLive.getUserId());
                break;
        }
    }
}
