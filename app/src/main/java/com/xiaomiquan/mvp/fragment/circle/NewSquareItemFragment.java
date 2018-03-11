package com.xiaomiquan.mvp.fragment.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.circle.ArticleDetailsActivity;
import com.xiaomiquan.mvp.activity.circle.TopicDetailActivity;
import com.xiaomiquan.mvp.activity.user.PersonalDetailsActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewSquareItemFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {

    SquareLiveAdapter squareLiveAdapter;
    UserLogin userLogin;

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        userLogin = SingSettingDBUtil.getUserLogin();
        type = getArguments().getInt("type");
        initLive(new ArrayList<SquareLive>());
        onRefresh();

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

        switch (requestCode) {
            case 0x123:
                break;
            case 0x124:
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
//                viewDelegate.viewHolder.tv_live_time.setText(datas.get(0).getYearMonthDay());
                initLive(datas);
                break;
            case 0x125:
                break;
            case 0x127:
                break;
            case 0x128:
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
                List<SquareLive> data2 = GsonUtil.getInstance().toList(data, SquareLive.class);
//                viewDelegate.viewHolder.tv_live_time.setText(datas.get(0).getYearMonthDay());
                initLive(data2);
                break;
        }
    }

    @Override
    protected void refreshData() {
        switch (type) {
            case 0:
                addRequest(binder.getLive(this));
                break;
            case 1:
                addRequest(binder.getAllLive(this));
                break;
        }
    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    public void initLive(final List<SquareLive> squareLives) {
        if (squareLiveAdapter == null) {
            squareLiveAdapter = new SquareLiveAdapter(binder, getActivity(), squareLives);
            squareLiveAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                    if (squareLiveAdapter.getDatas().get(position).getType().equals("1")) {
                        ArticleDetailsActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position), null);
                    } else {
                        TopicDetailActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position), null);
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
                            ArticleDetailsActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position), null);
                        } else {
                            TopicDetailActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position), null);
                        }
                    }
                    if (view.getId() == R.id.cv_head) {
                        PersonalDetailsActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position).getUserId(), 0x124);
                    }
                    if (view.getId() == R.id.lin_article) {
                        if (squareLiveAdapter.getDatas().get(position).getType().equals("1")) {
                            ArticleDetailsActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position), null);
                        } else {
                            TopicDetailActivity.startAct(getActivity(), squareLiveAdapter.getDatas().get(position), null);
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

    public static NewSquareItemFragment newInstance(
            int type
    ) {
        NewSquareItemFragment newFragment = new NewSquareItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    public int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((savedInstanceState != null)
                && savedInstanceState.containsKey("type")) {
            type = savedInstanceState.getInt("type");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("type", type);
    }


}
