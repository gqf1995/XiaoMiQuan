package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.group.HisAccountBinder;
import com.xiaomiquan.mvp.delegate.group.HisAccountDelegate;
import com.xiaomiquan.mvp.fragment.group.EarningsTrendFragment;
import com.xiaomiquan.mvp.fragment.group.GroupHistoryTradingFragment;
import com.xiaomiquan.mvp.fragment.group.PositionDetailFragment;

import java.util.ArrayList;

public class HisAccountActivity extends BaseDataBindActivity<HisAccountDelegate, HisAccountBinder> {

    ArrayList<Fragment> fragments;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    protected Class<HisAccountDelegate> getDelegateClass() {
        return HisAccountDelegate.class;
    }

    @Override
    public HisAccountBinder getDataBinder(HisAccountDelegate viewDelegate) {
        return new HisAccountBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_other_group)));
        getIntentData();
        initViews();
    }

    private void initViews() {
        if (!ListUtils.isEmpty(getSupportFragmentManager().getFragments())) {
            String[] stringArray = CommonUtils.getStringArray(R.array.sa_select_hisaccount);
            fragments = new ArrayList<>();
            fragments.add(EarningsTrendFragment.newInstance(id));
            fragments.add(PositionDetailFragment.newInstance(id));
            fragments.add(GroupHistoryTradingFragment.newInstance(id));
            for (int i = 0; i < stringArray.length; i++) {
                mTabEntities.add(new TabEntity(stringArray[i], 0, 0));
            }
            viewDelegate.viewHolder.tl_1.setTabData(mTabEntities);
            InnerPagerAdapter innerPagerAdapter = new InnerPagerAdapter(getSupportFragmentManager(), fragments, stringArray);
            viewDelegate.viewHolder.tl_1.setViewPager(innerPagerAdapter, viewDelegate.viewHolder.viewpager);
            if(!TextUtils.isEmpty(type)){
                int posiition= Integer.parseInt(type)-1;
                viewDelegate.viewHolder.tl_1.setCurrentTab(posiition);
                viewDelegate.viewHolder.viewpager.setCurrentItem(posiition);
            }
        }
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
        }
    }

    public static void startAct(Activity activity,
                                String id
    ) {
        Intent intent = new Intent(activity, HisAccountActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }
    public static void startAct(Activity activity,
                                String id,
                                String type
    ) {
        Intent intent = new Intent(activity, HisAccountActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }
    String id;
    String type;

    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");
    }

}
