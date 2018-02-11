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

public class AttentionFragment extends BasePullFragment<BaseFragentPullDelegate, RecommendBinder> {

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
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getAttention(AttentionFragment.this));
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
                List<UserFriende> datas = GsonUtil.getInstance().toList(data, UserFriende.class);
                initList(datas);
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
                                if (bigVListAdapter.getDatas().get(position).getAttention()) {
                                    addRequest(binder.attentiondelete(bigVListAdapter.getDatas().get(position).getId(), AttentionFragment.this));
                                    bigVListAdapter.getDatas().get(position).setAttention(false);
                                    bigVListAdapter.getDatas().get(position).setAttentionedCount(bigVListAdapter.getDatas().get(position).getAttentionedCount() - 1);
                                } else {
                                    addRequest(binder.attention(bigVListAdapter.getDatas().get(position).getId(), AttentionFragment.this));
                                    bigVListAdapter.getDatas().get(position).setAttention(true);
                                    bigVListAdapter.getDatas().get(position).setAttentionedCount(bigVListAdapter.getDatas().get(position).getAttentionedCount() + 1);
                                }
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
            initRecycleViewPull(bigVListAdapter, new LinearLayoutManager(getActivity()));
        } else {
            bigVListAdapter.setDatas(userFriendes);
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
        addRequest(binder.getAttention(this));
    }
}
