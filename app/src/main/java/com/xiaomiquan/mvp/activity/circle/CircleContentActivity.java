package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleContentAdapter;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.circle.CircleContentBinder;
import com.xiaomiquan.mvp.delegate.circle.CircleContentDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.xiaomiquan.widget.circle.SquarePopupWindow;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CircleContentActivity extends BasePullActivity<CircleContentDelegate, CircleContentBinder> {

    CircleContentAdapter circleContentAdapter;
    List<SquareLive> squareLiveList;
    UserCircle userCircle;
    public static String groupId;

    @Override
    protected Class<CircleContentDelegate> getDelegateClass() {
        return CircleContentDelegate.class;
    }

    @Override
    public CircleContentBinder getDataBinder(CircleContentDelegate viewDelegate) {
        return new CircleContentBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        floatBtn();
        initToolbar(new ToolbarBuilder().setTitle(userCircle.getName()).setmRightImg2(CommonUtils.getString(R.string.ic_Message)).setmRightImg1(CommonUtils.getString(R.string.ic_Filter2)));
        groupId = userCircle.getId();
    }

    @Override
    protected void clickRightIv1() {
        super.clickRightIv1();
        CreatCircleActivity.startAct(CircleContentActivity.this, userCircle);
    }

    @Override
    protected void clickRightIv2() {
        super.clickRightIv2();
        CreatCircleActivity.startAct(CircleContentActivity.this, userCircle);
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        gotoActivity(CreatCodeActivity.class).startAct();

    }

    SquarePopupWindow squarePopupWindow;

    public void floatBtn() {
        viewDelegate.viewHolder.civ_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                squarePopupWindow = new SquarePopupWindow(CircleContentActivity.this);
                squarePopupWindow.setOnItemClickListener(new SquarePopupWindow.OnItemClickListener() {
                    @Override
                    public void setOnItemClick(View v) {
                        switch (v.getId()) {
                            case R.id.lin_dynamic:
                                ReleaseDynamicActivity.startAct(CircleContentActivity.this, "2", "2");
                                squarePopupWindow.dismiss();
                                break;
                            case R.id.lin_article:
                                ReleaseArticleActivity.startAct(CircleContentActivity.this, "1", "2", "0");
                                squarePopupWindow.dismiss();
                                break;
                            case R.id.lin_wechat:
                                ReleaseArticleActivity.startAct(CircleContentActivity.this, "1", "2", "1");
                                squarePopupWindow.dismiss();
                                break;
                            case R.id.btn_cancel:
                                squarePopupWindow.dismiss();
                                break;

                        }
                    }
                });
                squarePopupWindow.showAtLocation(viewDelegate.viewHolder.pull_recycleview, Gravity.BOTTOM, 0, 0);

            }
        });
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceSuccess(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
                initUserTopic(datas);
                break;
            case 0x124:
                addRequest(binder.getCicleContent("1", userCircle.getId(), this));
                break;
            case 0x125:
                break;
            case 0x126:
                addRequest(binder.getCicleContent("1", userCircle.getId(), this));
                break;
        }
    }

    private void initUserTopic(final List<SquareLive> squareLives) {
        if (circleContentAdapter == null) {
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
            circleContentAdapter = new CircleContentAdapter(binder, CircleContentActivity.this, squareLives);
            circleContentAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    TopicDetailActivity.startAct(CircleContentActivity.this, squareLives.get(position));
                }
                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            circleContentAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, final int position, Object item) {
                    if (view.getId() == R.id.cv_head) {
                        UserInfoActivity.startAct(CircleContentActivity.this, squareLives.get(position));
                    }
                }
            });

            initRecycleViewPull(circleContentAdapter, new LinearLayoutManager(CircleContentActivity.this));
        } else {
            getDataBack(circleContentAdapter.getDatas(), squareLives, circleContentAdapter);
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getCicleContent(1 + "", userCircle.getId(), this));
    }

    private void getIntentData() {
        Intent intent = getIntent();
        userCircle = (UserCircle) intent.getParcelableExtra("userCircle");
        initCircle(userCircle);
    }

    public static void startAct(Activity activity,
                                UserCircle userCircle
    ) {
        Intent intent = new Intent(activity, CircleContentActivity.class);
        intent.putExtra("userCircle", userCircle);
        activity.startActivity(intent);
    }

    List<String> dataset1;//选择 条件
    List<String> dataset2;//选择 时间

    private void initCircle(UserCircle userCircle) {
        dataset1 = Arrays.asList(CommonUtils.getStringArray(R.array.circle_show_type));
        dataset2 = Arrays.asList(CommonUtils.getStringArray(R.array.circle_show_time));


        viewDelegate.viewHolder.lin_time
                .setDefaultClickLinsener(new DefaultClickLinsener() {
                    @Override
                    public void onClick(View view, int position, Object item) {
                        viewDelegate.viewHolder.lin_time.setText(dataset2.get(position));
                    }
                })
                .setDatas(dataset2, null);
        viewDelegate.viewHolder.lin_type
                .setDefaultClickLinsener(new DefaultClickLinsener() {
                    @Override
                    public void onClick(View view, int position, Object item) {
                        viewDelegate.viewHolder.lin_type.setText(dataset1.get(position));
                    }
                })
                .setDatas(dataset1, null);

        viewDelegate.viewHolder.tv_name.setText(userCircle.getName());
        viewDelegate.viewHolder.tv_creator.setText(userCircle.getNickName());
        viewDelegate.viewHolder.tv_num.setText(userCircle.getMemberCount());
        GlideUtils.loadImage(userCircle.getAvatar(), viewDelegate.viewHolder.iv_bg);
        initUserTopic(new ArrayList<SquareLive>());
    }


}
