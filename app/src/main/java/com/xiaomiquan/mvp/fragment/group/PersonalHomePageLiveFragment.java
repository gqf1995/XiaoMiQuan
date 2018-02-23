package com.xiaomiquan.mvp.fragment.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.mvp.activity.circle.ArticleDetailsActivity;
import com.xiaomiquan.mvp.activity.circle.TopicDetailActivity;
import com.xiaomiquan.mvp.databinder.BaseFragmentPullBinder;
import com.xiaomiquan.mvp.delegate.BaseFragentPullDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * 币种列表
 */
public class PersonalHomePageLiveFragment extends BasePullFragment<BaseFragentPullDelegate, BaseFragmentPullBinder> {
    SquareLiveAdapter adapter;
    List<SquareLive> strDatas;

    @Override
    protected Class<BaseFragentPullDelegate> getDelegateClass() {
        return BaseFragentPullDelegate.class;
    }

    @Override
    public BaseFragmentPullBinder getDataBinder(BaseFragentPullDelegate viewDelegate) {
        return new BaseFragmentPullBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceSuccess(data,info,status,requestCode);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        if (strDatas == null) {
            strDatas = new ArrayList<>();
        }
        initList(strDatas);
    }

    public void setDatas(List<SquareLive> strDatas) {
        if (adapter == null) {
            this.strDatas = strDatas;
        } else {
            initList(strDatas);
        }
    }

    private void initList(List<SquareLive> strDatas) {
        if (adapter == null) {
            adapter = new SquareLiveAdapter(binder, getActivity(), strDatas);
            adapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if (view.getId() == R.id.lin_comment) {
                        if (adapter.getDatas().get(position).getType().equals("1")) {
                            ArticleDetailsActivity.startAct(getActivity(), adapter.getDatas().get(position));
                        } else {
                            TopicDetailActivity.startAct(getActivity(), adapter.getDatas().get(position));
                        }
                    }
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
                @Override
                public boolean canScrollVertically() {
                    return true;
                }
            };
            layoutManager.setSmoothScrollbarEnabled(true);
            layoutManager.setAutoMeasureEnabled(true);
            initRecycleViewPull(adapter, layoutManager);
            viewDelegate.setIsLoadMore(false);
            viewDelegate.setIsPullDown(false);
        } else {
            getDataBack(adapter.getDatas(), strDatas, adapter);
        }
    }


    @Override
    protected void refreshData() {

    }

}
