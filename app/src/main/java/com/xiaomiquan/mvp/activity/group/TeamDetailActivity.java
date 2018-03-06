package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.MyGroupAdapter;
import com.xiaomiquan.adapter.group.RankTeampeopleAdapter;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.entity.bean.group.HotTeam;
import com.xiaomiquan.entity.bean.group.TeamDetails;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.user.PersonalHomePageActivity;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamDetailActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {

    MyGroupAdapter myGroupAdapter;
    RankTeampeopleAdapter rankTeampeopleAdapter;
    HeaderAndFooterWrapper adapter;
    UserLogin userLogin;
    boolean isJsoin = false;

    @Override
    protected Class<BaseActivityPullDelegate> getDelegateClass() {
        return BaseActivityPullDelegate.class;
    }

    @Override
    public BaseActivityPullBinder getDataBinder(BaseActivityPullDelegate viewDelegate) {
        return new BaseActivityPullBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle("").setSubTitle(CommonUtils.getString(R.string.str_apply_to_join_team)));
        initRankTeam(new ArrayList<TeamDetails.Player>());
    }

    private void initRankTeam(List<TeamDetails.Player> datas) {
        if (rankTeampeopleAdapter == null) {
            rankTeampeopleAdapter = new RankTeampeopleAdapter(this, datas);
            rankTeampeopleAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    HisAccountActivity.startAct(TeamDetailActivity.this, rankTeampeopleAdapter.getDatas().get(position - adapter.getHeadersCount()).getDemoId() + "");
                }
            });
            adapter = new HeaderAndFooterWrapper(rankTeampeopleAdapter);
            adapter.addHeaderView(initTop());
            initRecycleViewPull(adapter, adapter.getHeadersCount(), new LinearLayoutManager(this));
            viewDelegate.setIsLoadMore(false);
            viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            getDataBack(rankTeampeopleAdapter.getDatas(), datas, adapter);
        }
    }

    public CircleImageView ic_pic;
    public TextView tv_name;
    public TextView tv_nick_name;
    public TextView tv_rate;
    public TextView tv_content;
    public FontTextview tv_team_people_num;
    public FontTextview tv_team_earnings;
    public TextView tv_apply_to_join;
    public RecyclerView rv_my_group;
    public LinearLayout lin_my;

    private View initTop() {
        View rootView = getLayoutInflater().inflate(R.layout.layout_team_detail_top, null);
        this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
        this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        this.tv_nick_name = (TextView) rootView.findViewById(R.id.tv_nick_name);
        this.tv_rate = (TextView) rootView.findViewById(R.id.tv_rate);
        this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
        this.tv_team_people_num = (FontTextview) rootView.findViewById(R.id.tv_team_people_num);
        this.tv_team_earnings = (FontTextview) rootView.findViewById(R.id.tv_team_earnings);
        this.tv_apply_to_join = (TextView) rootView.findViewById(R.id.tv_apply_to_join);
        this.rv_my_group = (RecyclerView) rootView.findViewById(R.id.rv_my_group);
        this.lin_my = (LinearLayout) rootView.findViewById(R.id.lin_my);

        return rootView;
    }

    HotTeam mHotTeam;

    private void initDatas(HotTeam hotTeam) {
        mHotTeam = hotTeam;
        initToolbar(new ToolbarBuilder().setTitle(hotTeam.getTeamName()).setSubTitle(CommonUtils.getString(R.string.str_apply_to_join_team)));
        GlideUtils.loadImage(hotTeam.getAvatar(), ic_pic);
        ic_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalHomePageActivity.startAct(TeamDetailActivity.this,mHotTeam.getOwnerId()+"");
            }
        });
        tv_name.setText(hotTeam.getTeamName());
        tv_nick_name.setText(hotTeam.getNickName());
        if (!TextUtils.isEmpty(hotTeam.getAverage())) {
            BigUIUtil.getinstance().rateTextView(Double.parseDouble(hotTeam.getAverage()), tv_rate);
        }
        tv_content.setText(hotTeam.getRemark());
        tv_team_people_num.setText(hotTeam.getTeamCount() + CommonUtils.getString(R.string.str_people));
        tv_team_earnings.setText(hotTeam.getBonusPoolStr());
        if (teamDetails.isSelf()) {
            tv_apply_to_join.setText(CommonUtils.getString(R.string.str_invite_friends));
            initToolbar(new ToolbarBuilder().setTitle(hotTeam.getTeamName()).setSubTitle(CommonUtils.getString(R.string.str_edit_abstract)));
        } else if (isJsoin) {
            //邀请好友
            tv_apply_to_join.setText(CommonUtils.getString(R.string.str_invite_friends));
            initToolbar(new ToolbarBuilder().setTitle(""));
        } else {
            tv_apply_to_join.setText(CommonUtils.getString(R.string.str_apply_to_join));
            initToolbar(new ToolbarBuilder().setTitle("").setSubTitle(CommonUtils.getString(R.string.str_apply_to_join_team)));
        }
        tv_apply_to_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teamDetails.isSelf() || isJsoin) {
                    //邀请好友
                    gotoActivity(InviteFriendsActivity.class).startAct();
                } else {
                    //申请加入
                    join();
                }
            }
        });
    }

    private void getIntentData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    private void initMyGroup(GroupItem item) {
        if (item == null) {
            lin_my.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(item.getName())) {
            lin_my.setVisibility(View.GONE);
            return;
        }
        List<GroupItem> datas = new ArrayList<>();
        datas.add(item);
        if (myGroupAdapter == null) {
            myGroupAdapter = new MyGroupAdapter(this, datas);
            myGroupAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, final int position, Object item) {
                    if (view.getId() == R.id.tv_deal) {
                        SimulatedTradingActivity.startAct(TeamDetailActivity.this, (ArrayList) myGroupAdapter.getDatas(), position, true);
                    }
                    if (view.getId() == R.id.tv_look) {
                        CombinationActivity.startAct(TeamDetailActivity.this, myGroupAdapter.getDatas().get(position), true);
                    }
                }
            });
            rv_my_group.setLayoutManager(new LinearLayoutManager(this) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            rv_my_group.setAdapter(myGroupAdapter);
        } else {
            myGroupAdapter.setDatas(datas);
        }
    }

    public static void startAct(Activity activity,
                                String id) {
        Intent intent = new Intent(activity, TeamDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    String id;

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if (teamDetails.isSelf()) {
            //编辑简介
            EditTeamIntroductionActivity.startAct(this, mHotTeam.getRemark(), 0x123);
        } else {
            join();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                String remark = data.getStringExtra("remark");
                tv_content.setText(remark);
            }
        }
    }

    private void join() {
        if (SingSettingDBUtil.getUserLogin() == null) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
            return;
        }
        CircleDialogHelper.initDefaultInputDialog(this,
                CommonUtils.getString(R.string.str_apply_to_join_team_reason),
                CommonUtils.getString(R.string.str_toast_input_reason),
                CommonUtils.getString(R.string.str_determine),
                new OnInputClickListener() {
                    @Override
                    public void onClick(String text, View v) {
                        //申请加入战队
                        addRequest(binder.join(id, text, TeamDetailActivity.this));
                    }
                }
        ).show();
    }

    TeamDetails teamDetails;

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                //成员信息
                List<TeamDetails.Player> players = GsonUtil.getInstance().toList(data, TeamDetails.Player.class);
                initRankTeam(players);
                break;
            case 0x124:
                //战队信息
                teamDetails = GsonUtil.getInstance().toObj(data, TeamDetails.class);
                UserLogin userLogin = SingSettingDBUtil.getUserLogin();
                if (userLogin != null) {
                    for (int i = 0; i < teamDetails.getPlayerList().size(); i++) {
                        if (teamDetails.getPlayerList().get(i).getUserId() == userLogin.getId()) {
                            isJsoin = true;
                            break;
                        }
                    }
                }
                initMyGroup(teamDetails.getMyAccount());
                initDatas(teamDetails.getGameTeamOwnerVo());
                initRankTeam(teamDetails.getPlayerList());
                break;
        }
    }

    @Override
    protected void refreshData() {
        if (viewDelegate.page == viewDelegate.defaultPage) {
            //刷新头部
            addRequest(binder.getTeamDetail(id, this));
        } else {
            addRequest(binder.getPlayers(id, this));
        }
    }
}
