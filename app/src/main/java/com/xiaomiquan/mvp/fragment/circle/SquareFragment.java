package com.xiaomiquan.mvp.fragment.circle;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleAllDvpAdapter;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.adapter.circle.SquareNewsAdapter;
import com.xiaomiquan.adapter.circle.SquareShortCutAdapter;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.mvp.activity.circle.ArticleActivity;
import com.xiaomiquan.mvp.activity.circle.BigVListActivity;
import com.xiaomiquan.mvp.activity.circle.CircleContentActivity;
import com.xiaomiquan.mvp.activity.circle.LiveActivity;
import com.xiaomiquan.mvp.activity.circle.ReleaseArticleActivity;
import com.xiaomiquan.mvp.activity.circle.ReleaseDynamicActivity;
import com.xiaomiquan.mvp.activity.circle.TopicDetailActivity;
import com.xiaomiquan.mvp.activity.mvp.activity.ArticleDetailsActivity;
import com.xiaomiquan.mvp.activity.mvp.activity.UserInfoActivity;
import com.xiaomiquan.mvp.databinder.circle.SquareBinder;
import com.xiaomiquan.mvp.delegate.circle.SquareDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.xiaomiquan.widget.circle.SquarePopupWindow;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SquareFragment extends BasePullFragment<SquareDelegate, SquareBinder> {

    SquareShortCutAdapter squareShortCutAdapter;
    SquareNewsAdapter squareNewsAdapter;
    SquareLiveAdapter squareLiveAdapter;

    @Override
    protected Class<SquareDelegate> getDelegateClass() {
        return SquareDelegate.class;
    }

    @Override
    public SquareBinder getDataBinder(SquareDelegate viewDelegate) {
        return new SquareBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initShortCut();
        floatBtn();
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getLive(SquareFragment.this));
            }
        });
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                break;
            case 0x124:
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
                initLive(datas);
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getLive(this));
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            onRefresh();
        } else {
            binder.cancelpost();
        }
    }

    List<SquareLive> squareLives;

    @Override
    protected void onFragmentFirstVisible() {
        squareLives = new ArrayList<>();
        initLive(squareLives);
    }

    public void initShortCut() {
        String[] str = {"大v直播", "圈子动态", "热门快讯", "热门组合", "价格提醒", "推荐大v", "精选文章"};
        List<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("title", str[i]);
            list.add(hashMap);
        }
        squareShortCutAdapter = new SquareShortCutAdapter(getActivity(), list);
        squareShortCutAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (position) {
                    case 0:
                        gotoActivity(LiveActivity.class).startAct();
                        break;
                    case 5:
                        gotoActivity(BigVListActivity.class).startAct();
                        break;
                    case 6:
                        gotoActivity(ArticleActivity.class).startAct();
                        break;
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {

                return false;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewDelegate.viewHolder.ry_entrance.setLayoutManager(gridLayoutManager);
        viewDelegate.viewHolder.ry_entrance.setItemAnimator(new DefaultItemAnimator());
        viewDelegate.viewHolder.ry_entrance.setAdapter(squareShortCutAdapter);
    }

    public void initNew() {
//        squareNewsAdapter = new CircleAllDvpAdapter(getActivity(), userCircles);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        squareNewsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        viewDelegate.viewHolder.ry_message.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.ry_message.setAdapter(squareNewsAdapter);

    }

    public void initLive(final List<SquareLive> squareLives) {
        squareLiveAdapter = new SquareLiveAdapter(getActivity(), squareLives);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        squareLiveAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                if (squareLives.get(position).getType().equals("1")) {
                    ArticleDetailsActivity.startAct(getActivity(), squareLives.get(position));
                } else {
                    TopicDetailActivity.startAct(getActivity(), squareLives.get(position));
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
                        ArticleDetailsActivity.startAct(getActivity(), squareLives.get(position));
                    } else {
                        TopicDetailActivity.startAct(getActivity(), squareLives.get(position));
                    }
                }
                if (view.getId() == R.id.tv_praise) {
                    addRequest(binder.savePraise(squareLiveAdapter.getDatas().get(position).getId(), SquareFragment.this));
                }
                if (view.getId() == R.id.cv_head) {
                    UserInfoActivity.startAct(getActivity(), squareLives.get(position));
                }
            }
        });
        viewDelegate.viewHolder.ry_live.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.ry_live.setAdapter(squareLiveAdapter);

    }

    SquarePopupWindow squarePopupWindow;

    public void floatBtn() {
        viewDelegate.viewHolder.flt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                squarePopupWindow = new SquarePopupWindow(getActivity());
                squarePopupWindow.setOnItemClickListener(new SquarePopupWindow.OnItemClickListener() {
                    @Override
                    public void setOnItemClick(View v) {
                        switch (v.getId()) {
                            case R.id.lin_dynamic:
                                ReleaseDynamicActivity.startAct(getActivity(), "2", "1");
                                squarePopupWindow.dismiss();
                                break;
                            case R.id.lin_article:
                                ReleaseArticleActivity.startAct(getActivity(), "1", "1");
                                squarePopupWindow.dismiss();
                                break;
                            case R.id.lin_wechat:
                                break;
                            case R.id.btn_cancel:
                                squarePopupWindow.dismiss();
                                break;

                        }
                    }
                });
                squarePopupWindow.showAtLocation(viewDelegate.viewHolder.banner_img, Gravity.BOTTOM, 0, 0);
            }
        });
    }

}
