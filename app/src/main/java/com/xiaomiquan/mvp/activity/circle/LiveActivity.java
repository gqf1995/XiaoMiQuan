package com.xiaomiquan.mvp.activity.circle;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleAllDvpAdapter;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.entity.bean.circle.Praise;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.activity.user.UserHomePageActivity;
import com.xiaomiquan.mvp.databinder.GetFriendsJoinBinder;
import com.xiaomiquan.mvp.databinder.circle.LiveBinder;
import com.xiaomiquan.mvp.databinder.circle.NewsBinder;
import com.xiaomiquan.mvp.delegate.GetFriendsJoinDelegate;
import com.xiaomiquan.mvp.delegate.circle.LiveDelegate;
import com.xiaomiquan.mvp.delegate.circle.NewsDelegate;
import com.xiaomiquan.mvp.fragment.circle.SquareFragment;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LiveActivity extends BasePullActivity<NewsDelegate, NewsBinder> {

    SquareLiveAdapter squareLiveAdapter;

    @Override
    protected Class<NewsDelegate> getDelegateClass() {
        return NewsDelegate.class;
    }

    @Override
    public NewsBinder getDataBinder(NewsDelegate viewDelegate) {
        return new NewsBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_tv_live)).setSubTitle(CommonUtils.getString(R.string.str_release)));
        initLive(new ArrayList<SquareLive>());
//        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                addRequest(binder.getLive(LiveActivity.this));
//            }
//        });
        viewDelegate.viewHolder.pull_recycleview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    viewDelegate.viewHolder.tv_time.setText(
                            squareLiveAdapter.getDatas().get(firstItemPosition).getYearMonthDay() + "");


                }
            }
        });
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        ReleaseDynamicActivity.startAct(LiveActivity.this, "2", "1");

    }

    HeaderAndFooterWrapper adapter;
    public void initLive(final List<SquareLive> squareLives) {
        if (squareLiveAdapter == null) {
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            if (squareLives.size() > 0) {
                viewDelegate.viewHolder.tv_time.setText(squareLives.get(0).getYearMonthDay());
            }
            squareLiveAdapter = new SquareLiveAdapter(binder, LiveActivity.this, squareLives);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LiveActivity.this) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }
            };
            squareLiveAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                    if (squareLives.get(position).getType().equals("1")) {
                        ArticleDetailsActivity.startAct(LiveActivity.this, squareLives.get(position));
                    } else {
                        TopicDetailActivity.startAct(LiveActivity.this, squareLives.get(position));
                    }
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            squareLiveAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, final int position, Object item) {
                    if (view.getId() == R.id.tv_comment) {
                        if (squareLives.get(position).getType().equals("1")) {
                            ArticleDetailsActivity.startAct(LiveActivity.this, squareLives.get(position));
                        } else {
                            TopicDetailActivity.startAct(LiveActivity.this, squareLives.get(position));
                        }
                    }
                    if (view.getId() == R.id.cv_head) {
                        UserHomePageActivity.startAct(LiveActivity.this, squareLives.get(position).getUserId());
                    }
                }
            });
//            viewDelegate.viewHolder.pull_recycleview.setLayoutManager(linearLayoutManager);
//            viewDelegate.viewHolder.pull_recycleview.getItemAnimator().setChangeDuration(0);
//            viewDelegate.viewHolder.pull_recycleview.setAdapter(squareLiveAdapter);
            adapter = new HeaderAndFooterWrapper(squareLiveAdapter);
            initRecycleViewPull(adapter, new LinearLayoutManager(this));
            viewDelegate.setNoDataTxt(CommonUtils.getString(R.string.str_kline_nodata));
        } else {
            getDataBack(squareLiveAdapter.getDatas(), squareLives, squareLiveAdapter);
        }
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
                initLive(datas);
                break;
            case 0x124:

                break;
            case 0x127:

                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getLive(this));
    }
}
