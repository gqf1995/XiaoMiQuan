package com.xiaomiquan.mvp.activity.market;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.entity.bean.ExchangeName;
import com.xiaomiquan.mvp.databinder.TabViewpageBinder;
import com.xiaomiquan.mvp.delegate.TabViewpageDelegate;
import com.xiaomiquan.mvp.fragment.SelectAddCoinFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.xiaomiquan.base.AppConst.CACHE_EXCHANGENAME;

public class AddCoinActivity extends BaseDataBindActivity<TabViewpageDelegate, TabViewpageBinder> {

    ArrayList<Fragment> fragments;
    List<String> mTitles;
    List<ExchangeName> exchangeNameList;
    List<String> userSelectKeys;


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_add_coin_market)).setSubTitle(CommonUtils.getString(R.string.str_complete)));
        String exchangeNamesStr = CacheUtils.getInstance().getString(CACHE_EXCHANGENAME);
        getIntentData();
        if (!TextUtils.isEmpty(exchangeNamesStr)) {
            exchangeNameList = GsonUtil.getInstance().toList(exchangeNamesStr, ExchangeName.class);
            initTablelayout(exchangeNameList);
        }
        EventBus.getDefault().register(this);
    }

    private void initTablelayout(List<ExchangeName> exchangeNames) {
        if (mTitles == null && fragments == null) {
            fragments = new ArrayList<>();
            mTitles = new ArrayList<>();
        }

        List<String> strings = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_market));
        mTitles.add(strings.get(4));
        mTitles.add(strings.get(5));
        mTitles.add(strings.get(6));
        mTitles.add(strings.get(7));

        for (int i = 0; i < exchangeNames.size(); i++) {
            mTitles.add(exchangeNames.get(i).getEname());
        }

        for (int i = 0; i < mTitles.size(); i++) {
            fragments.add(SelectAddCoinFragment.newInstance(mTitles.get(i)));
        }

        for (int i = 0; i < 4; i++) {
            if (fragments.get(i) instanceof SelectAddCoinFragment) {
                ((SelectAddCoinFragment) fragments.get(i)).setCoin(true);
            }
        }

        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) instanceof SelectAddCoinFragment) {
                ((SelectAddCoinFragment) fragments.get(i)).setUserSelectKeys(userSelectKeys);
            }
        }

        viewDelegate.viewHolder.tl_2.setViewPager(viewDelegate.viewHolder.vp_sliding,
                mTitles.toArray(new String[mTitles.size()]), (FragmentActivity) viewDelegate.viewHolder.rootView.getContext(), fragments);

    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        commit();
    }

    private void commit() {
        addRequest(binder.subs(userSelectKeys, this));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onExchangeName(ExchangeData event) {
        if (userSelectKeys.contains(event.getOnlyKey())) {
            userSelectKeys.remove(userSelectKeys.indexOf(event.getOnlyKey()));
        } else {
            userSelectKeys.add(event.getOnlyKey());
        }
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) instanceof SelectAddCoinFragment) {
                ((SelectAddCoinFragment) fragments.get(i)).setUserSelectKeys(userSelectKeys);
            }
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                setResult(RESULT_OK);
                onBackPressed();
                break;
        }
    }

    public static void startAct(Fragment activity,
                                ArrayList<String> userSelectKeys,
                                int code
    ) {
        Intent intent = new Intent(activity.getContext(), AddCoinActivity.class);
        intent.putStringArrayListExtra("userSelectKeys", userSelectKeys);
        activity.startActivityForResult(intent, code);
    }


    private void getIntentData() {
        Intent intent = getIntent();
        userSelectKeys = intent.getStringArrayListExtra("userSelectKeys");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

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
