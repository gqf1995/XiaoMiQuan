package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.DynamicAdapter;
import com.xiaomiquan.adapter.group.MyGroupAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.HomePageDelegate;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomePageActivity extends BasePullActivity<HomePageDelegate, BaseActivityPullBinder> {
    DynamicAdapter dynamicAdapter;
    HeaderAndFooterWrapper adapter;
    UserLogin userLogin;
    MyGroupAdapter myGroupAdapter;

    @Override
    protected Class<HomePageDelegate> getDelegateClass() {
        return HomePageDelegate.class;
    }

    @Override
    public BaseActivityPullBinder getDataBinder(HomePageDelegate viewDelegate) {
        return new BaseActivityPullBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle("郭青枫"));
        viewDelegate.initToplinsener((int) CommonUtils.getDimensionPixelSize(R.dimen.trans_230px));
    }

    private void initList(List<String> datas) {
        for (int i = 0; i < 20; i++) {
            datas.add("");
        }
        if (dynamicAdapter == null) {
            dynamicAdapter = new DynamicAdapter(this, datas);
            adapter = new HeaderAndFooterWrapper(dynamicAdapter);
            adapter.addHeaderView(initTop());
            initRecycleViewPull(adapter, new LinearLayoutManager(this));
        } else {
            getDataBack(dynamicAdapter.getDatas(), datas, adapter);
        }
    }

    public ImageView iv_bg;
    public TextView tv_name;
    public TextView tv_is_focuse;
    public TextView tv_fans_num;
    public TextView tv_focuse_num;
    public TextView tv_high_quality;
    public TextView tv_free_subscription;
    public TextView tv_my_subscribe;
    public LinearLayout lin_management;
    public CircleImageView ic_pic;
    public TextView tv_title_group;
    public RecyclerView rv_group;
    public TextView tv_title_circle;
    public RecyclerView rv_circle;
    public TextView tv_title_live;
    public IconFontTextview tv_live_time;

    private View initTop() {
        View rootView = getLayoutInflater().inflate(R.layout.layout_user_page, null);
        this.iv_bg = (ImageView) rootView.findViewById(R.id.iv_bg);
        this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        this.tv_is_focuse = (TextView) rootView.findViewById(R.id.tv_is_focuse);
        this.tv_fans_num = (TextView) rootView.findViewById(R.id.tv_fans_num);
        this.tv_focuse_num = (TextView) rootView.findViewById(R.id.tv_focuse_num);
        this.tv_high_quality = (TextView) rootView.findViewById(R.id.tv_high_quality);
        this.tv_free_subscription = (TextView) rootView.findViewById(R.id.tv_free_subscription);
        this.tv_my_subscribe = (TextView) rootView.findViewById(R.id.tv_my_subscribe);
        this.lin_management = (LinearLayout) rootView.findViewById(R.id.lin_management);
        this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
        this.tv_title_group = (TextView) rootView.findViewById(R.id.tv_title_group);
        this.rv_group = (RecyclerView) rootView.findViewById(R.id.rv_group);
        this.tv_title_circle = (TextView) rootView.findViewById(R.id.tv_title_circle);
        this.rv_circle = (RecyclerView) rootView.findViewById(R.id.rv_circle);
        this.tv_title_live = (TextView) rootView.findViewById(R.id.tv_title_live);
        this.tv_live_time = (IconFontTextview) rootView.findViewById(R.id.tv_live_time);
        return rootView;
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    @Override
    protected void refreshData() {

    }

    public static void startAct(Activity activity,
                                String id) {
        Intent intent = new Intent(activity, UserHomePageActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    private String id;

    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        userLogin = SingSettingDBUtil.getUserLogin();
        initList(new ArrayList<String>());
        tv_high_quality.setVisibility(View.GONE);
        tv_free_subscription.setVisibility(View.GONE);
        if ((userLogin.getId() + "").equals(id)) {
            //我的个人中心
            tv_title_group.setText(CommonUtils.getString(R.string.str_my_group));
            tv_title_live.setText(CommonUtils.getString(R.string.str_my_live));
            tv_title_circle.setText(CommonUtils.getString(R.string.str_my_circle));
            tv_my_subscribe.setVisibility(View.VISIBLE);
        }
    }

}
