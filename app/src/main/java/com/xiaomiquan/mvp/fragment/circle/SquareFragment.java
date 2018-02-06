package com.xiaomiquan.mvp.fragment.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.adapter.circle.SquareShortCutAdapter;
import com.xiaomiquan.entity.bean.circle.Praise;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.mvp.activity.circle.ArticleActivity;
import com.xiaomiquan.mvp.activity.circle.BigVListActivity;
import com.xiaomiquan.mvp.activity.circle.LiveActivity;
import com.xiaomiquan.mvp.activity.circle.NewsActivity;
import com.xiaomiquan.mvp.activity.circle.ReleaseArticleActivity;
import com.xiaomiquan.mvp.activity.circle.ReleaseDynamicActivity;
import com.xiaomiquan.mvp.activity.circle.TopicDetailActivity;
import com.xiaomiquan.mvp.activity.circle.ArticleDetailsActivity;
import com.xiaomiquan.mvp.activity.circle.UserInfoActivity;
import com.xiaomiquan.mvp.databinder.circle.SquareBinder;
import com.xiaomiquan.mvp.delegate.circle.SquareDelegate;
import com.xiaomiquan.widget.circle.SquarePopupWindow;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SquareFragment extends BasePullFragment<SquareDelegate, SquareBinder> {

    SquareShortCutAdapter squareShortCutAdapter;
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
        viewDelegate.viewHolder.lin_live.setOnClickListener(this);
        viewDelegate.viewHolder.lin_news.setOnClickListener(this);
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
            case 0x125:
                break;
            case 0x127:
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


    List<String> mtitles;


    public void initShortCut() {
        mtitles = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_square));
        int[] imgs = {R.drawable.icon_live, R.drawable.icon_news, R.drawable.icon_group, R.drawable.icon_bigv, R.drawable.icon_article};

        List<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < mtitles.size(); i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("title", mtitles.get(i));
            hashMap.put("img", imgs[i]);
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
                    case 1:
                        gotoActivity(NewsActivity.class).startAct();
                        break;
                    case 2:

                        break;
                    case 3:
                        gotoActivity(BigVListActivity.class).startAct();
                        break;
                    case 4:
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
                if (view.getId() == R.id.lin_comment) {
                    if (squareLives.get(position).getType().equals("1")) {
                        ArticleDetailsActivity.startAct(getActivity(), squareLives.get(position));
                    } else {
                        TopicDetailActivity.startAct(getActivity(), squareLives.get(position));
                    }
                }
                if (view.getId() == R.id.lin_praise) {
                    if (squareLiveAdapter.isPraise.get(position).equals("false")) {
                        squareLiveAdapter.isPraise.add(position, "true");
                        squareLiveAdapter.paiseNum.add(position, Integer.parseInt(squareLiveAdapter.paiseNum.get(position)) + 1 + "");
                        squareLiveAdapter.notifyItemChanged(position);
                    } else {
                        squareLiveAdapter.isPraise.add(position, "false");
                        squareLiveAdapter.paiseNum.add(position, Integer.parseInt(squareLiveAdapter.paiseNum.get(position)) - 1 + "");
                        squareLiveAdapter.notifyItemChanged(position);
                    }
                    addRequest(binder.savePraise(squareLiveAdapter.getDatas().get(position).getId(), SquareFragment.this));
                }
                if (view.getId() == R.id.cv_head) {
                }
            }
        });
        viewDelegate.viewHolder.ry_live.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.ry_live.getItemAnimator().setChangeDuration(0);
        viewDelegate.viewHolder.ry_live.setAdapter(squareLiveAdapter);
        viewDelegate.setIsLoadMore(true);
    }

    SquarePopupWindow squarePopupWindow;

    public void floatBtn() {
        viewDelegate.viewHolder.civ_send.setOnClickListener(new View.OnClickListener() {
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
                                ReleaseArticleActivity.startAct(getActivity(), "1", "1", "0");
                                squarePopupWindow.dismiss();
                                break;
                            case R.id.lin_wechat:
                                ReleaseArticleActivity.startAct(getActivity(), "1", "1", "1");
                                squarePopupWindow.dismiss();
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_news:
                gotoActivity(NewsActivity.class).startAct();
                break;
            case R.id.lin_live:
                gotoActivity(LiveActivity.class).startAct();
                break;
        }
    }



    @Override
    public void loadData() {
        super.loadData();
        addRequest(binder.getLive(this));
    }
}
