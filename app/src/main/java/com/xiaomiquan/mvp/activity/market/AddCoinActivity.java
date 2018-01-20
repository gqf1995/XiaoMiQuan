package com.xiaomiquan.mvp.activity.market;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeName;
import com.xiaomiquan.mvp.databinder.TabViewpageBinder;
import com.xiaomiquan.mvp.delegate.TabViewpageDelegate;
import com.xiaomiquan.mvp.fragment.SelectAddCoinFragment;

import java.util.ArrayList;
import java.util.List;

import static com.xiaomiquan.base.AppConst.CACHE_EXCHANGENAME;

public class AddCoinActivity extends BaseDataBindActivity<TabViewpageDelegate, TabViewpageBinder> {

    ArrayList<Fragment> fragments;
    List<String> mTitles;
    List<ExchangeName> exchangeNameList;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_add_coin_market)).setSubTitle(CommonUtils.getString(R.string.str_complete)));

        String exchangeNamesStr = CacheUtils.getInstance().getString(CACHE_EXCHANGENAME);
        if (!TextUtils.isEmpty(exchangeNamesStr)) {
            exchangeNameList = GsonUtil.getInstance().toList(exchangeNamesStr, ExchangeName.class);
            initTablelayout(exchangeNameList);
        }
    }

    private void initTablelayout(List<ExchangeName> exchangeNames) {
        if (mTitles == null && fragments == null) {
            fragments = new ArrayList<>();
            mTitles = new ArrayList<>();
        }

        for (int i = 0; i < mTitles.size(); i++) {
            fragments.add(SelectAddCoinFragment.newInstance(exchangeNames.get(i).getEname()));
        }
        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.vp_sliding,
                mTitles.toArray(new String[mTitles.size()]), (FragmentActivity) viewDelegate.viewHolder.rootView.getContext(), fragments);

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    @Override
    protected Class<TabViewpageDelegate> getDelegateClass() {
        return TabViewpageDelegate.class;
    }

    @Override
    public TabViewpageBinder getDataBinder(TabViewpageDelegate viewDelegate) {
        return new TabViewpageBinder(viewDelegate);
    }

}
