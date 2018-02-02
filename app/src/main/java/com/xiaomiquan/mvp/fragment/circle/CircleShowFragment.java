package com.xiaomiquan.mvp.fragment.circle;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleDynamicAdapter;
import com.xiaomiquan.adapter.circle.CircleMyAdapter;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.activity.circle.CircleContentActivity;
import com.xiaomiquan.mvp.activity.circle.CreatCircleActivity;
import com.xiaomiquan.mvp.activity.circle.GetFriendsJoinActivity;
import com.xiaomiquan.mvp.activity.circle.TopicDetailActivity;
import com.xiaomiquan.mvp.activity.mvp.activity.UserInfoActivity;
import com.xiaomiquan.mvp.databinder.circle.CircleShowBinder;
import com.xiaomiquan.mvp.delegate.circle.CircleShowDelegate;
import com.xiaomiquan.widget.circle.JoinPopupWindow;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2018/1/25.
 */

public class CircleShowFragment extends BasePullFragment<CircleShowDelegate, CircleShowBinder> {

    CircleMyAdapter circleMyAdapter;
    CircleDynamicAdapter circleDynamicAdapter;
    List<SquareLive> squareLiveList;
    List<UserCircle> userCircleList;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getCircleTopic(CircleShowFragment.this));
                addRequest(binder.getMyCircle(CircleShowFragment.this));
            }
        });
    }

    @Override
    protected Class<CircleShowDelegate> getDelegateClass() {
        return CircleShowDelegate.class;
    }

    @Override
    public CircleShowBinder getDataBinder(CircleShowDelegate viewDelegate) {
        return new CircleShowBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                List<UserCircle> userCircles = GsonUtil.getInstance().toList(data, UserCircle.class);
                initMyCircle(userCircles);
                break;
            case 0x124:
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
                initCircleTopic(datas);
                break;
            case 0x127:
                addRequest(binder.getCircleTopic(CircleShowFragment.this));
                break;
        }

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            onRefresh();
        } else {
            binder.cancelpost();
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        squareLiveList = new ArrayList<>();
        initCircleTopic(squareLiveList);
        userCircleList = new ArrayList<>();
        initMyCircle(userCircleList);
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getCircleTopic(this));
        addRequest(binder.getMyCircle(this));
    }

    /**
     * 已经加入圈子信息
     *
     * @param userCircles
     */
    private void initMyCircle(final List<UserCircle> userCircles) {
        UserCircle userCircle = new UserCircle();
        userCircle.setName("添加圈子");
        userCircles.add(0, userCircle);
        circleMyAdapter = new CircleMyAdapter(getActivity(), userCircles);
        circleMyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position != 0) {
                    CircleContentActivity.startAct(getActivity(), userCircles.get(position));
                } else {
                    initPop();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {

                return false;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewDelegate.viewHolder.rv_mycircle.setLayoutManager(gridLayoutManager);
        viewDelegate.viewHolder.rv_mycircle.setItemAnimator(new DefaultItemAnimator());
        viewDelegate.viewHolder.rv_mycircle.setAdapter(circleMyAdapter);
    }

    private void initCircleTopic(final List<SquareLive> squareLives) {
        circleDynamicAdapter = new CircleDynamicAdapter(getActivity(), squareLives);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        circleDynamicAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                TopicDetailActivity.startAct(getActivity(), squareLives.get(position));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        circleDynamicAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, final int position, Object item) {
                if (view.getId() == R.id.tv_comment) {
                    TopicDetailActivity.startAct(getActivity(), squareLives.get(position));
                }
                if (view.getId() == R.id.tv_praise) {
                    addRequest(binder.savePraise(circleDynamicAdapter.getDatas().get(position).getId(), CircleShowFragment.this));
                }
                if (view.getId() == R.id.cv_head) {
                    UserInfoActivity.startAct(getActivity(), squareLives.get(position));
                }
            }
        });
        viewDelegate.viewHolder.rv_circle.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.rv_circle.setAdapter(circleDynamicAdapter);

    }


    JoinPopupWindow joinPopupWindow;

    private void initPop() {
        joinPopupWindow = new JoinPopupWindow(getActivity());
        joinPopupWindow.setOnItemClickListener(new JoinPopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                switch (v.getId()) {
                    case R.id.lin_join:
                        gotoActivity(GetFriendsJoinActivity.class).startAct();
                        joinPopupWindow.dismiss();
                        break;
                    case R.id.lin_creat:
                        gotoActivity(CreatCircleActivity.class).startAct();
                        joinPopupWindow.dismiss();
                        break;
                    case R.id.btn_cancel:
                        joinPopupWindow.dismiss();
                        break;
                }
            }
        });
        joinPopupWindow.showAtLocation(viewDelegate.viewHolder.rv_circle, Gravity.BOTTOM, 0, 0);
    }

}
