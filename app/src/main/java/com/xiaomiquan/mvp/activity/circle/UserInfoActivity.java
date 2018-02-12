package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.User;
import com.xiaomiquan.entity.bean.circle.UserInfoGroupVos;
import com.xiaomiquan.mvp.databinder.circle.UserInfoBinder;
import com.xiaomiquan.mvp.delegate.circle.UserInfoDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.Serializable;
import java.util.List;

public class UserInfoActivity extends BasePullActivity<UserInfoDelegate, UserInfoBinder> {
    SquareLiveAdapter squareLiveAdapter;

    @Override
    protected Class<UserInfoDelegate> getDelegateClass() {
        return UserInfoDelegate.class;
    }

    @Override
    public UserInfoBinder getDataBinder(UserInfoDelegate viewDelegate) {
        return new UserInfoBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("主页"));
        getIntentData();
        addRequest(binder.getUserInfo(squareLive.getUserId(), UserInfoActivity.this));
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addRequest(binder.getUserInfo(squareLive.getUserId(), UserInfoActivity.this));
            }
        });
        viewDelegate.viewHolder.lin_attention.setOnClickListener(this);
        viewDelegate.viewHolder.lin_fans.setOnClickListener(this);

    }

    private void initUser(User user) {
        GlideUtils.loadImage(user.getAvatar(), viewDelegate.viewHolder.cv_head);
        viewDelegate.viewHolder.tv_name.setText(user.getNickName());

    }

    private void initCircle(UserInfoGroupVos userInfoGroupVos) {
        GlideUtils.loadImage(userInfoGroupVos.getAvatar(), viewDelegate.viewHolder.iv_circle);
        viewDelegate.viewHolder.tv_circle_name.setText(userInfoGroupVos.getName());
        viewDelegate.viewHolder.tv_circle_brief.setText(userInfoGroupVos.getBrief());
//        viewDelegate.viewHolder.tv_attention_num.setText(userInfoGroupVos.getGroupNum());
    }

    User user;

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceSuccess(data,info,status,requestCode);
        switch (requestCode) {
            case 0x123:
                String userInfoGroupVos = GsonUtil.getInstance().getValue(data, "groupVos");
                UserInfoGroupVos userInfoGroupVosS = GsonUtil.getInstance().toObj(userInfoGroupVos, UserInfoGroupVos.class);
                initCircle(userInfoGroupVosS);

                String attentionCount = GsonUtil.getInstance().getValue(data, "attentionCount");
                String fansCount = GsonUtil.getInstance().getValue(data, "fansCount");
                viewDelegate.viewHolder.tv_attention.setText(attentionCount);
                viewDelegate.viewHolder.tv_fans.setText(fansCount);

                List<SquareLive> squareLives = GsonUtil.getInstance().toList(data, "articleTopicVos", SquareLive.class);
                initLive(squareLives);

                user = GsonUtil.getInstance().toObj(GsonUtil.getInstance().getValue(data, "user"), User.class);
                initUser(user);
                break;
        }
    }

    public void initLive(final List<SquareLive> squareLives) {
        squareLiveAdapter = new SquareLiveAdapter(binder,UserInfoActivity.this, squareLives);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserInfoActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        squareLiveAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                Intent intent = new Intent(UserInfoActivity.this, TopicDetailActivity.class);
                intent.putExtra("userTopic", (Serializable) squareLives.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        squareLiveAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, final int position, Object item) {
                if (view.getId() == R.id.tv_comment) {
                    Intent intent = new Intent(UserInfoActivity.this, TopicDetailActivity.class);
                    intent.putExtra("userTopic", (Serializable) squareLives.get(position));
                    startActivity(intent);
                }
                if (view.getId() == R.id.tv_praise) {
                    addRequest(binder.savePraise(squareLiveAdapter.getDatas().get(position).getId(), UserInfoActivity.this));
                }
            }
        });
        viewDelegate.viewHolder.ry_live.setLayoutManager(linearLayoutManager);
        viewDelegate.viewHolder.ry_live.setAdapter(squareLiveAdapter);

    }

    @Override
    protected void refreshData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_attention:
                Intent intent1 = new Intent();
                intent1.putExtra("user", user);
                gotoActivity(AttentionActivity.class).setIntent(intent1).startAct();

                break;
            case R.id.lin_fans:
                Intent intent = new Intent();
                intent.putExtra("user", user);
                gotoActivity(FansActivity.class).setIntent(intent).startAct();
                break;
        }
    }

    SquareLive squareLive;
    private void getIntentData() {
        Intent intent = getIntent();
        squareLive = (SquareLive) intent.getSerializableExtra("squareLive");

    }

    public static void startAct(Activity activity,
                                SquareLive squareLive
    ) {
        Intent intent = new Intent(activity, UserInfoActivity.class);
        intent.putExtra("squareLive", squareLive);
        activity.startActivity(intent);
    }

}
