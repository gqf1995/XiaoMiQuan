package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
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
import com.xiaomiquan.mvp.activity.circle.ArticleDetailsActivity;
import com.xiaomiquan.mvp.activity.circle.TopicDetailActivity;
import com.xiaomiquan.mvp.activity.group.CombinationActivity;
import com.xiaomiquan.mvp.activity.group.GroupDealActivity;
import com.xiaomiquan.mvp.databinder.PersonalHomePageBinder;
import com.xiaomiquan.mvp.delegate.PersonalHomePageDelegate;
import com.xiaomiquan.mvp.fragment.group.PersonalHomePageLiveFragment;

import java.util.ArrayList;
import java.util.List;

public class PersonalHomePageActivity extends BaseDataBindActivity<PersonalHomePageDelegate, PersonalHomePageBinder> {

    MyGroupAdapter myGroupAdapter;
    CircleMyAdapter circleMyAdapter;
    SquareLiveAdapter squareLiveAdapter;
    PersonalHomePageLiveFragment personalHomePageLiveFragment;
    boolean isMy = false;


    @Override
    protected Class<PersonalHomePageDelegate> getDelegateClass() {
        return PersonalHomePageDelegate.class;
    }

    @Override
    public PersonalHomePageBinder getDataBinder(PersonalHomePageDelegate viewDelegate) {
        return new PersonalHomePageBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(""));
    }

    private String id;
    UserLogin userLogin;

    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        userLogin = SingSettingDBUtil.getUserLogin();
        viewDelegate.viewHolder.tv_high_quality.setVisibility(View.GONE);
        viewDelegate.viewHolder.tv_free_subscription.setVisibility(View.GONE);
        viewDelegate.viewHolder.tv_is_focuse.setVisibility(View.GONE);
        if (userLogin != null) {
            if ((userLogin.getId() + "").equals(id)) {
                //我的个人中心
                isMy = true;
                viewDelegate.viewHolder.tv_title_group.setText(CommonUtils.getString(R.string.str_my_group));
                viewDelegate.viewHolder.tv_live_title.setText(CommonUtils.getString(R.string.str_my_live));
                viewDelegate.viewHolder.tv_title_circle.setText(CommonUtils.getString(R.string.str_my_circle));
                viewDelegate.viewHolder.tv_my_subscribe.setVisibility(View.GONE);
            }
        }
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onDataRefresh();
            }
        });
        initCircleList(new ArrayList<UserCircle>());
        initGroupList(new ArrayList<GroupItem>());
        initList(new ArrayList<SquareLive>());
        onDataRefresh();
    }

    private void onDataRefresh() {
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        addRequest(binder.personCenter(id, this));
    }

    public static void startAct(Activity activity,
                                String id) {
        Intent intent = new Intent(activity, PersonalHomePageActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                UserHomePage userHomePage = GsonUtil.getInstance().toObj(data, UserHomePage.class);
                initList(userHomePage.getArticleTopicVos());
                initCircleList(userHomePage.getGroupVos());
                initUser(userHomePage);
                if (isMy) {
                    addRequest(binder.listDemo(this));
                } else {
                    addRequest(binder.getDemoByUserId(id, this));
                }
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
                break;
            case 0x124:
                //我的组合
                List<GroupItem> groupItems = GsonUtil.getInstance().toList(data, GroupItem.class);
                initGroupList(groupItems);
                break;
            case 0x125:
                //他人组合 只显示一个
                List<GroupItem> groupItems1 = new ArrayList<>();
                GroupItem groupItem = GsonUtil.getInstance().toObj(data, GroupItem.class);
                groupItems1.add(groupItem);
                initGroupList(groupItems1);
                break;
        }
    }

    private void initList(List<SquareLive> datas) {
        if (squareLiveAdapter == null) {
            squareLiveAdapter = new SquareLiveAdapter(binder, this, datas);
            squareLiveAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if (view.getId() == R.id.lin_comment) {
                        if (squareLiveAdapter.getDatas().get(position).getType().equals("1")) {
                            ArticleDetailsActivity.startAct(PersonalHomePageActivity.this, squareLiveAdapter.getDatas().get(position));
                        } else {
                            TopicDetailActivity.startAct(PersonalHomePageActivity.this, squareLiveAdapter.getDatas().get(position));
                        }
                    }
                }
            });
            viewDelegate.viewHolder.recycler_view.setLayoutManager(new LinearLayoutManager(this));
            viewDelegate.viewHolder.recycler_view.setAdapter(squareLiveAdapter);
            viewDelegate.setSquareLiveAdapter(squareLiveAdapter);
        } else {
            squareLiveAdapter.setDatas(datas);
        }
    }

    private void initGroupList(List<GroupItem> groupItems) {
        if (myGroupAdapter == null) {
            myGroupAdapter = new MyGroupAdapter(this, groupItems);
            myGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if (view.getId() == R.id.tv_deal) {
                        GroupDealActivity.startAct(PersonalHomePageActivity.this, (ArrayList) myGroupAdapter.getDatas(), position, true);
                    }
                    if (view.getId() == R.id.tv_look) {
                        CombinationActivity.startAct(PersonalHomePageActivity.this, myGroupAdapter.getDatas().get(position), true);
                    }
                }
            });
            viewDelegate.viewHolder.rv_group.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewDelegate.viewHolder.rv_group.setAdapter(myGroupAdapter);
        } else {
            myGroupAdapter.setDatas(groupItems);
            viewDelegate.viewHolder.lin_group.setVisibility(groupItems.size() == 0 ? View.GONE : View.VISIBLE);
        }
    }

    private void initCircleList(List<UserCircle> circles) {
        if (circleMyAdapter == null) {
            circleMyAdapter = new CircleMyAdapter(this, circles);
            viewDelegate.viewHolder.rv_group.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            viewDelegate.viewHolder.rv_circle.setAdapter(circleMyAdapter);
        } else {
            circleMyAdapter.setDatas(circles);
            if (circles.size() == 0) {

            }
        }
    }

    private void initUser(UserHomePage userHomePage) {
        UserHomePage.UserBean data = userHomePage.getUser();
        viewDelegate.getmToolbarTitle().setText(data.getNickName());
        viewDelegate.viewHolder.tv_name.setText(data.getNickName());
        viewDelegate.viewHolder.tv_fans_num.setText(userHomePage.getFansCount() + "");
        viewDelegate.viewHolder.tv_focuse_num.setText(userHomePage.getAttentionCount() + "");
        if (userLogin != null) {
            if (!(userLogin.getId() + "").equals(id)) {
                viewDelegate.viewHolder.tv_high_quality.setVisibility(data.getBigv() > 0 ? View.VISIBLE : View.GONE);
                viewDelegate.viewHolder.tv_free_subscription.setVisibility(data.getSubscribeCharge() == 0 ? View.VISIBLE : View.GONE);
                viewDelegate.viewHolder.tv_is_focuse.setText(userHomePage.isAttention() ? CommonUtils.getString(R.string.str_already_fucose) : CommonUtils.getString(R.string.str_focuse));
            }
        } else {
            viewDelegate.viewHolder.tv_high_quality.setVisibility(data.getBigv() > 0 ? View.VISIBLE : View.GONE);
            viewDelegate.viewHolder.tv_free_subscription.setVisibility(data.getSubscribeCharge() == 0 ? View.VISIBLE : View.GONE);
            viewDelegate.viewHolder.tv_is_focuse.setText(userHomePage.isAttention() ? CommonUtils.getString(R.string.str_already_fucose) : CommonUtils.getString(R.string.str_focuse));
        }
        GlideUtils.loadImage(data.getAvatar(), viewDelegate.viewHolder.ic_pic);
    }
}
