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
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleMyAdapter;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.adapter.group.MyGroupAdapter;
import com.xiaomiquan.entity.bean.UserHomePage;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.group.CombinationActivity;
import com.xiaomiquan.mvp.activity.group.GroupDealActivity;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.HomePageDelegate;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomePageActivity extends BasePullActivity<HomePageDelegate, BaseActivityPullBinder> {
    SquareLiveAdapter squareLiveAdapter;
    HeaderAndFooterWrapper adapter;
    UserLogin userLogin;
    MyGroupAdapter myGroupAdapter;
    CircleMyAdapter circleMyAdapter;

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
        initToolbar(new ToolbarBuilder().setTitle(""));
        viewDelegate.initToplinsener((int) CommonUtils.getDimensionPixelSize(R.dimen.trans_90px));
    }

    private void initList(List<SquareLive> datas) {
        if (adapter == null) {
            squareLiveAdapter = new SquareLiveAdapter(this, datas);
            adapter = new HeaderAndFooterWrapper(squareLiveAdapter);
            adapter.addHeaderView(initTop());
            initRecycleViewPull(adapter, new LinearLayoutManager(this));
            onRefresh();
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        } else {
            getDataBack(squareLiveAdapter.getDatas(), datas, adapter);
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

    private void initGroupList(List<GroupItem> groupItems) {
        if (myGroupAdapter == null) {
            myGroupAdapter = new MyGroupAdapter(this, groupItems);
            myGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if (view.getId() == R.id.tv_deal) {
                        GroupDealActivity.startAct(UserHomePageActivity.this, myGroupAdapter.getDatas(), position, true);
                    }
                    if (view.getId() == R.id.tv_look) {
                        CombinationActivity.startAct(UserHomePageActivity.this, myGroupAdapter.getDatas().get(position), true);
                    }
                }
            });
            rv_group.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            rv_group.setAdapter(myGroupAdapter);
        } else {
            myGroupAdapter.setDatas(groupItems);
        }
    }

    private void initCircleList(List<UserCircle> circles) {
        if (circleMyAdapter == null) {
            circleMyAdapter = new CircleMyAdapter(this, circles);
            rv_group.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            rv_circle.setAdapter(circleMyAdapter);
        } else {
            circleMyAdapter.setDatas(circles);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                UserHomePage userHomePage = GsonUtil.getInstance().toObj(data, UserHomePage.class);
                initList(userHomePage.getArticleTopicVos());
                initCircleList(userHomePage.getGroupVos());
                initUser(userHomePage);
            case 0x124:
                List<GroupItem> groupItems = GsonUtil.getInstance().toList(data, GroupItem.class);
                initGroupList(groupItems);
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.personCenter(id, this));
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
        initList(new ArrayList<SquareLive>());
        tv_high_quality.setVisibility(View.GONE);
        tv_free_subscription.setVisibility(View.GONE);
        if ((userLogin.getId() + "").equals(id)) {
            //我的个人中心
            tv_title_group.setText(CommonUtils.getString(R.string.str_my_group));
            tv_title_live.setText(CommonUtils.getString(R.string.str_my_live));
            tv_title_circle.setText(CommonUtils.getString(R.string.str_my_circle));
            tv_is_focuse.setVisibility(View.GONE);
            tv_my_subscribe.setVisibility(View.VISIBLE);
        }
    }

    private void initUser(UserHomePage userHomePage) {
        UserHomePage.UserBean data = userHomePage.getUser();
        viewDelegate.getmToolbarTitle().setText(data.getNickName());
        tv_name.setText(data.getNickName());
        tv_fans_num.setText(userHomePage.getFansCount() + "");
        tv_focuse_num.setText(userHomePage.getAttentionCount() + "");
        if (!(userLogin.getId() + "").equals(id)) {
            tv_high_quality.setVisibility(data.getBigv() > 0 ? View.VISIBLE : View.GONE);
            tv_free_subscription.setVisibility(data.getSubscribeCharge() == 0 ? View.VISIBLE : View.GONE);
            tv_is_focuse.setText(userHomePage.isAttention() ? CommonUtils.getString(R.string.str_already_fucose) : CommonUtils.getString(R.string.str_focuse));
        }
        GlideUtils.loadImage(data.getAvatar(), ic_pic);
    }


}
