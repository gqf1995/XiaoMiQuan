package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
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
import com.xiaomiquan.mvp.databinder.circle.DynamicInfoBinder;
import com.xiaomiquan.mvp.delegate.circle.DynamicInfoDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.widget.circle.CommentPopupWindow;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.api.widget.Widget;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

public class DynamicInfoActivity extends BasePullActivity<DynamicInfoDelegate, DynamicInfoBinder> {

    DynamicPhotoAdapter dynamicPhotoAdapter;
    CommentDetailAdapter commentAdapter;
    HeaderAndFooterWrapper headerAndFooterWrapper;
    UserLogin userLogin;

    @Override
    protected Class<DynamicInfoDelegate> getDelegateClass() {
        return DynamicInfoDelegate.class;
    }

    @Override
    public DynamicInfoBinder getDataBinder(DynamicInfoDelegate viewDelegate) {
        return new DynamicInfoBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        userLogin = SingSettingDBUtil.getUserLogin();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_topic)));
    }

    @Override
    protected void refreshData() {

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    private void initTop(SquareLive squareLive) {
        GlideUtils.loadImage(squareLive.getAvatar(), viewDelegate.cv_head);
        viewDelegate.tv_name.setText(squareLive.getNickName());
        viewDelegate.tv_type_time.setText(squareLive.getCreateTimeStr());
        viewDelegate.tv_comment_num.setText(squareLive.getCommentCount());
        viewDelegate.tv_comment_member.setText("评论数：" + squareLive.getCommentCount());
        viewDelegate.tv_praise_num.setText(squareLive.getGoodCount() + "");
        viewDelegate.tv_shared_num.setText(squareLive.getGoodCount() + "");
        if (squareLive.getImgList() != null) {
            initImgs(squareLive.getImgList());
        }
        switch (squareLive.getType()) {
            case "1":

                break;
            case "2":
                viewDelegate.lin_root.addView(viewDelegate.initArticle());
                break;
            case "3":
                viewDelegate.lin_root.addView(viewDelegate.initForecast());
                break;
            case "4":
                viewDelegate.lin_root.addView(viewDelegate.initShared());
                break;
            case "5":
                viewDelegate.lin_root.addView(viewDelegate.initTopic());
                break;
        }

    }

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
                    PersonalHomePageActivity.startAct(DynamicInfoActivity.this, comments.get(position).getCommentUserId());
                }
            });
            viewDelegate.viewHolder.pull_recycleview.getItemAnimator().setChangeDuration(0);
            headerAndFooterWrapper = new HeaderAndFooterWrapper(commentAdapter);
            headerAndFooterWrapper.addHeaderView(viewDelegate.initTop());
            initRecycleViewPull(headerAndFooterWrapper, 1, new LinearLayoutManager(DynamicInfoActivity.this));
        } else {
            getDataBack(commentAdapter.getDatas(), comments, commentAdapter);
        }
    }

    private void initImgs(final List<String> stringList) {
        if (dynamicPhotoAdapter == null) {
            dynamicPhotoAdapter = new DynamicPhotoAdapter(DynamicInfoActivity.this, stringList);
            dynamicPhotoAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    //图片预览
                    Album.gallery(DynamicInfoActivity.this)
                            .widget(Widget.newDarkBuilder(DynamicInfoActivity.this).title(CommonUtils.getString(R.string.str_img_preview)).build())
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
            GridLayoutManager gridLayoutManager = new GridLayoutManager(DynamicInfoActivity.this, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            viewDelegate.recycleview_reason.setLayoutManager(gridLayoutManager);
            viewDelegate.recycleview_reason.setAdapter(dynamicPhotoAdapter);
        } else {
            dynamicPhotoAdapter.setDatas(stringList);
        }

    }


    SquareLive squareLive;

    private void getIntentData() {
        Intent intent = getIntent();
        squareLive = (SquareLive) intent.getParcelableExtra(("squareLive"));
        initComment(squareLive.getCommentVos());
    }

    public static void startAct(Activity activity,
                                SquareLive squareLive
    ) {
        Intent intent = new Intent(activity, TopicDetailActivity.class);
        intent.putExtra("squareLive", squareLive);
        activity.startActivity(intent);
    }

    private void initPop(final Boolean comment, final String reId, final String reName) {
        final CommentPopupWindow commentPopupWindow = new CommentPopupWindow(DynamicInfoActivity.this);
        commentPopupWindow.setOnItemClickListener(new CommentPopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                if (comment) {
                    addRequest(binder.saveComment(squareLive.getId(), commentPopupWindow.et_input2.getText().toString(), DynamicInfoActivity.this));
                } else {
                    addRequest(binder.saveRecomment(
                            squareLive.getId(),
                            commentPopupWindow.et_input2.getText().toString(),
                            reId,
                            DynamicInfoActivity.this));
                }
                commentPopupWindow.et_input2.setText(null);
            }
        });
        if (comment) {
            commentPopupWindow.et_input2.setHint("评论");
        } else {
            commentPopupWindow.et_input2.setHint("回复" + reName);
        }
        commentPopupWindow.showAtLocation(viewDelegate.viewHolder.fl_rcv, Gravity.BOTTOM, 0, 0);
        //弹出键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
