package com.xiaomiquan.mvp.fragment.circle;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.entity.ResultDialogEntity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.adapter.circle.SquareShortCutAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.circle.ArticleActivity;
import com.xiaomiquan.mvp.activity.circle.ArticleDetailsActivity;
import com.xiaomiquan.mvp.activity.circle.BigVListActivity;
import com.xiaomiquan.mvp.activity.circle.LiveActivity;
import com.xiaomiquan.mvp.activity.circle.NewsActivity;
import com.xiaomiquan.mvp.activity.circle.ReleaseArticleActivity;
import com.xiaomiquan.mvp.activity.circle.ReleaseDynamicActivity;
import com.xiaomiquan.mvp.activity.circle.TopicDetailActivity;
import com.xiaomiquan.mvp.activity.user.PersonalHomePageActivity;
import com.xiaomiquan.mvp.databinder.circle.SquareBinder;
import com.xiaomiquan.mvp.delegate.circle.SquareDelegate;
import com.xiaomiquan.widget.JudgeNestedScrollView;
import com.xiaomiquan.widget.circle.SquarePopupWindow;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SquareFragment extends BasePullFragment<SquareDelegate, SquareBinder> {

    SquareShortCutAdapter squareShortCutAdapter;
    SquareLiveAdapter squareLiveAdapter;
    UserLogin userLogin;

    @Override
    protected Class<SquareDelegate> getDelegateClass() {
        return SquareDelegate.class;
    }

    @Override
    public SquareBinder getDataBinder(SquareDelegate viewDelegate) {
        return new SquareBinder(viewDelegate);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_square)).setSubTitle(CommonUtils.getString(R.string.str_release)).setShowBack(false));
        userLogin = SingSettingDBUtil.getUserLogin();
        initShortCut();
        floatBtn();
        initLive(new ArrayList<SquareLive>());
//        addRequest(binder.getLive(SquareFragment.this));
        viewDelegate.viewHolder.lin_live.setOnClickListener(this);

        viewDelegate.viewHolder.pull_recycleview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (squareLiveAdapter.getDatas().size() > 0) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    //判断是当前layoutManager是否为LinearLayoutManager
                    // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        //获取第一个可见view的位置
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                        viewDelegate.viewHolder.tv_live_time.setText(
                                squareLiveAdapter.getDatas().get(firstItemPosition).getYearMonthDay() + "");
                    }
                }
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
//                viewDelegate.viewHolder.tv_live_time.setText(datas.get(0).getYearMonthDay());
                initLive(datas);
                break;
            case 0x125:
                break;
            case 0x127:
                break;
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        final SquarePopupWindow squarePopupWindow = new SquarePopupWindow(getActivity());
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
        squarePopupWindow.showAtLocation(viewDelegate.viewHolder.lin_live, Gravity.BOTTOM, 0, 0);

    }

    @Override
    protected void refreshData() {
        addRequest(binder.getLive(this));
    }

    @Override
    protected void onFragmentFirstVisible() {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
    }

    List<String> mtitles;

    public void initShortCut() {
        mtitles = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_square));
        int[] imgs = {R.drawable.icon_live, R.drawable.icon_group, R.drawable.icon_bigv, R.drawable.icon_article};
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
                    //                    case 1:
                    //                        gotoActivity(NewsActivity.class).startAct();
                    //                        break;
                    case 1:
                        //模拟交易
                        ResultDialogEntity resultDialogEntity = new ResultDialogEntity();
                        resultDialogEntity.setCode("0");
                        EventBus.getDefault().post(resultDialogEntity);
                        break;
                    case 2:
                        gotoActivity(BigVListActivity.class).startAct();
                        break;
                    case 3:
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
        if (squareLiveAdapter == null) {
            onRefresh();
            //if (squareLives.size() > 0) {
            squareLiveAdapter = new SquareLiveAdapter(binder, getActivity(), squareLives);

            squareLiveAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                    if (squareLiveAdapter.getDatas().get(position).getType().equals("1")) {
                        ArticleDetailsActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position));
                    } else {
                        TopicDetailActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position));
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
                        if (squareLiveAdapter.getDatas().get(position).getType().equals("1")) {
                            ArticleDetailsActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position));
                        } else {
                            TopicDetailActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position));
                        }
                    }
                    if (view.getId() == R.id.cv_head) {
                        PersonalHomePageActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position).getUserId());
                    }
                    if (view.getId() == R.id.lin_article) {
                        if (squareLiveAdapter.getDatas().get(position).getType().equals("1")) {
                            ArticleDetailsActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position));
                        } else {
                            TopicDetailActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position));
                        }
                    }
                }
            });
            viewDelegate.viewHolder.pull_recycleview.getItemAnimator().setChangeDuration(0);
            initRecycleViewPull(squareLiveAdapter, new LinearLayoutManager(getActivity()));
        } else {
            getDataBack(squareLiveAdapter.getDatas(), squareLives, squareLiveAdapter);
        }

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
                squarePopupWindow.showAtLocation(viewDelegate.viewHolder.lin_live, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_live:
                gotoActivity(LiveActivity.class).startAct();
                break;
        }
    }


}
