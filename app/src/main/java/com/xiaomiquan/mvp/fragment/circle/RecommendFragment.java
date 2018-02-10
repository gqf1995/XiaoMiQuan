package com.xiaomiquan.mvp.fragment.circle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.BigVListAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.circle.UserFriende;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.user.PersonalHomePageActivity;
import com.xiaomiquan.mvp.databinder.circle.RecommendBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends BasePullFragment<BaseFragentPullDelegate, RecommendBinder> {


    BigVListAdapter bigVListAdapter;
    List<UserFriende> userFriendeList;
    UserLogin userLogin;

    @Override
    public RecommendBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new RecommendBinder(viewDelegate);
    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        userLogin = SingSettingDBUtil.getUserLogin();
        addRequest(binder.getBigVlist(this));
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getBigVlist(RecommendFragment.this));
            }
        });
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 0x123:
                List<UserFriende> datas = GsonUtil.getInstance().toList(data, UserFriende.class);
                initList(datas);
                break;
            case 0x124:
                addRequest(binder.getBigVlist(this));
                break;
            case 0x125:
                break;
            case 0x126:
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

    public void initList(final List<UserFriende> userFriendes) {
        if (bigVListAdapter == null) {
            bigVListAdapter = new BigVListAdapter(getActivity(), userFriendes);
            bigVListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            bigVListAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    switch (view.getId()) {
                        case R.id.tv_attention:
                            if (userLogin != null) {
                                UserFriende userFriende = bigVListAdapter.userFriendes.get(position);
                                if (userFriende.getAttention()) {
                                    bigVListAdapter.userFriendes.remove(position);
                                    userFriende.setAttention(false);
                                    userFriende.setAttentionedCount(userFriende.getAttentionedCount() - 1);
                                    bigVListAdapter.userFriendes.add(position, userFriende);
                                } else {
                                    bigVListAdapter.userFriendes.remove(position);
                                    userFriende.setAttention(true);
                                    userFriende.setAttentionedCount(userFriende.getAttentionedCount() + 1);
                                    bigVListAdapter.userFriendes.add(position, userFriende);
                                }
                                addRequest(binder.attention(userFriendes.get(position).getId(), RecommendFragment.this));
                                bigVListAdapter.notifyItemChanged(position);
                            } else {
                                ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
                            }
                            break;
                        case R.id.cv_head:
                            PersonalHomePageActivity.startAct(getActivity(), userFriendes.get(position).getId());
                            break;
                    }
                }
            });
            viewDelegate.viewHolder.pull_recycleview.getItemAnimator().setChangeDuration(0);
//            viewDelegate.viewHolder.pull_recycleview.setAdapter(bigVListAdapter);
            initRecycleViewPull(bigVListAdapter, new LinearLayoutManager(getActivity()));
            viewDelegate.setNoDataTxt(CommonUtils.getString(R.string.str_kline_nodata));
        } else {
            getDataBack(bigVListAdapter.getDatas(), userFriendes, bigVListAdapter);
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        userFriendeList = new ArrayList<>();
        initList(userFriendeList);
    }

    @Override
    protected void refreshData() {
        addRequest(binder.getBigVlist(this));
    }
}
