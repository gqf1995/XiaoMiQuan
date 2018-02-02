package com.xiaomiquan.mvp.fragment.circle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.BigVListAdapter;
import com.xiaomiquan.entity.bean.circle.UserFriende;
import com.xiaomiquan.mvp.databinder.circle.RecommendBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import java.util.ArrayList;
import java.util.List;

public class AttentionFragment extends BasePullFragment<BaseFragentPullDelegate, RecommendBinder> {

    BigVListAdapter bigVListAdapter;
    List<UserFriende> userFriendeList;

    @Override
    public RecommendBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new RecommendBinder(viewDelegate);
    }

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:

                break;
            case 0x124:
                List<UserFriende> datas = GsonUtil.getInstance().toList(data, UserFriende.class);
                initList(datas);
                break;
            case 0x125:
                addRequest(binder.getAttention(this));
                break;
            case 0x126:
                addRequest(binder.getAttention(this));
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
                        if (userFriendes.get(position).getAttention()) {
                            addRequest(binder.attentiondelete(userFriendes.get(position).getAttentionId(), AttentionFragment.this));
                        } else {
                            addRequest(binder.attention(userFriendes.get(position).getAttentionId(), AttentionFragment.this));
                        }
                        break;
                }
            }
        });
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
        viewDelegate.viewHolder.pull_recycleview.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        viewDelegate.viewHolder.pull_recycleview.setAdapter(bigVListAdapter);

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
