package com.xiaomiquan.mvp.fragment.circle;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.adapter.circle.CircleAllDvpAdapter;
import com.xiaomiquan.adapter.circle.CircleMyAdapter;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.activity.circle.CircleContentActivity;
import com.xiaomiquan.mvp.activity.circle.CreatCircleActivity;
import com.xiaomiquan.mvp.databinder.circle.CircleDvpBinder;
import com.xiaomiquan.mvp.delegate.circle.CircleDvpDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Andy on 2018/1/18.
 */

public class CircleDvpFragment extends BasePullFragment<CircleDvpDelegate, CircleDvpBinder> {

    CircleAllDvpAdapter circleAllDvpAdapter;
    CircleMyAdapter circleCreatAdapter;
    List<UserCircle> userCircleList;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();

    }

    private void initMyCircle(final List<UserCircle> userCircles) {
        UserCircle userCircle = new UserCircle();
        userCircle.setBrief("创建圈子");
        userCircles.add(0, userCircle);
        circleCreatAdapter = new CircleMyAdapter(getActivity(), userCircles);
        final List<UserCircle> finalUserCircles = userCircles;
        circleCreatAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position != 0) {
                    Intent intent = new Intent();
                    intent.putExtra("userCircle", (Parcelable) userCircles.get(position));
                    gotoActivity(CircleContentActivity.class).setIntent(intent).startAct();
                } else {
                    gotoActivity(CreatCircleActivity.class).startAct();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {

                return false;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewDelegate.viewHolder.rv_mycircle.setLayoutManager(gridLayoutManager);
        viewDelegate.viewHolder.rv_mycircle.setItemAnimator(new DefaultItemAnimator());
        viewDelegate.viewHolder.rv_mycircle.setAdapter(circleCreatAdapter);
    }

    private void initAllCircle(final List<UserCircle> userCircles) {
        circleAllDvpAdapter = new CircleAllDvpAdapter(getActivity(), userCircles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        circleAllDvpAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                CircleDialogHelper.initDefaultDialog(getActivity(), "确定加入吗？", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //加入圈子
                        addRequest(binder.joinCircle(userCircles.get(position).getId() + "", CircleDvpFragment.this));
                    }
                }).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        viewDelegate.viewHolder.rv_allcircle.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.rv_allcircle.setAdapter(circleAllDvpAdapter);
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
                List<UserCircle> datas = GsonUtil.getInstance().toList(data, UserCircle.class);
                initAllCircle(datas);
                break;
            case 0x125:
//                addRequest(binder.getCircleMy(1, 10, this));
                addRequest(binder.getMyCircleInfo(1, 10, this));
                break;
        }
    }

    @Override
    protected Class<CircleDvpDelegate> getDelegateClass() {
        return CircleDvpDelegate.class;
    }


    @Override
    public CircleDvpBinder getDataBinder(CircleDvpDelegate viewDelegate) {
        return new CircleDvpBinder(viewDelegate);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                //创建圈子后刷新

            }
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
        userCircleList = new ArrayList<>();

        initMyCircle(userCircleList);
        initAllCircle(userCircleList);
    }

    @Override
    protected void refreshData() {
//        addRequest(binder.getCircleMy(1, 10, this));
        addRequest(binder.getMyCircleInfo(1, 10, this));
    }
}
