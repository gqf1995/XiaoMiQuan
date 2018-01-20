package com.xiaomiquan.mvp.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.CircleAllDvpAdapter;
import com.xiaomiquan.adapter.CircleCreatAdapter;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.circle.CircleContentActivity;
import com.xiaomiquan.mvp.activity.circle.CreatCircleActivity;
import com.xiaomiquan.mvp.activity.market.CoinIndexActivity;
import com.xiaomiquan.mvp.databinder.CircleDvpBinder;
import com.xiaomiquan.mvp.delegate.CircleDvpDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andy on 2018/1/18.
 */

public class CircleDvpFragment extends BaseDataBindFragment<CircleDvpDelegate, CircleDvpBinder> {

    CircleAllDvpAdapter circleAllDvpAdapter;
    CircleCreatAdapter circleCreatAdapter;
    List<UserCircle> userCircleList;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        addRequest(binder.getCircleMy(1,10,this));
        addRequest(binder.getMyCircleInfo(1,10,this));
        initRefush();
    }

    private void initRefush() {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getMyCircleInfo(1,10,CircleDvpFragment.this));
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_coin_index:
                //币种指数
                gotoActivity(CoinIndexActivity.class).startAct();
                break;
        }
    }

    private void initCoinIndex(final List<UserCircle> userCircles) {
            UserCircle userCircle=new UserCircle();
            userCircle.setBrief("创建圈子");
            userCircles.add(0,userCircle);
            circleCreatAdapter = new CircleCreatAdapter(getActivity(),userCircles);
            final List<UserCircle> finalUserCircles = userCircles;
            circleCreatAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    if (position!=0) {
                        Intent intent = new Intent();
                        intent.putExtra("userCircle", (Serializable) userCircles.get(position));
                        gotoActivity(CircleContentActivity.class).setIntent(intent).startAct();
//
                    }else{
                        gotoActivity(CreatCircleActivity.class).startAct();
                    }

//                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                    userCircles.remove(position);
//                    circleCreatAdapter.notifyItemRemoved(position);
                    return false;
                }
            });
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            viewDelegate.viewHolder.circledvp_card.setLayoutManager(gridLayoutManager);
            viewDelegate.viewHolder.circledvp_card.setItemAnimator(new DefaultItemAnimator());
            viewDelegate.viewHolder.circledvp_card.setAdapter(circleCreatAdapter);
    }

    private void initVolume(List<UserCircle> userCircles) {
        circleAllDvpAdapter = new CircleAllDvpAdapter(getActivity(), userCircles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        circleAllDvpAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                CircleDialogHelper.initDefaultDialog(getActivity(),"确定加入吗？", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SingSettingDBUtil.logout();
                        //加入圈子
                        addRequest(binder.joinCircle());
                    }
                }).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        viewDelegate.viewHolder.circle_dvp_hot.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.circle_dvp_hot.setAdapter(circleAllDvpAdapter);
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
//                viewDelegate.viewHolder.lin_root.setVisibility(View.VISIBLE);
                List<UserCircle> userCircles = GsonUtil.getInstance().toList(data, UserCircle.class);
                initCoinIndex(userCircles);
                initVolume(userCircles);
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


}
