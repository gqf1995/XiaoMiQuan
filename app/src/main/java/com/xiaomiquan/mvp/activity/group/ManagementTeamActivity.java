package com.xiaomiquan.mvp.activity.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.ApplyForPeopleAdapter;
import com.xiaomiquan.entity.bean.group.ManageTeam;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManagementTeamActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {

    ApplyForPeopleAdapter applyForPeopleAdapter;
    HeaderAndFooterWrapper adapter;
    ManageTeam manageTeam;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_my_management_team)));
        initList(new ArrayList<ManageTeam.ApprovesDataBean>());
    }

    public CircleImageView iv_pic;
    public TextView tv_title;
    public TextView tv_team_home;
    public LinearLayout lin_edit;
    public LinearLayout lin_add;

    private View initTopView() {
        View rootView = getLayoutInflater().inflate(R.layout.layout_management_top, null);
        this.iv_pic = (CircleImageView) rootView.findViewById(R.id.iv_pic);
        this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        this.tv_team_home = (TextView) rootView.findViewById(R.id.tv_team_home);
        this.lin_edit = (LinearLayout) rootView.findViewById(R.id.lin_edit);
        this.lin_add = (LinearLayout) rootView.findViewById(R.id.lin_add);
        tv_team_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamDetailActivity.startAct(ManagementTeamActivity.this, manageTeam.getTeamId() + "");
            }
        });
        lin_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTeamIntroductionActivity.startAct(ManagementTeamActivity.this, "", 0x123);
            }
        });
        lin_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(InviteFriendsActivity.class).startAct();
            }
        });
        return rootView;
    }

    int agreedPosition;

    private void initList(List<ManageTeam.ApprovesDataBean> datas) {
        if (adapter == null) {
            applyForPeopleAdapter = new ApplyForPeopleAdapter(this, datas);
            applyForPeopleAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    //同意加入
                    agreedPosition = position - adapter.getHeadersCount();
                    addRequest(binder.teamApprove(applyForPeopleAdapter.getDatas().get(agreedPosition).getId() + "", ManagementTeamActivity.this));
                }
            });
            adapter = new HeaderAndFooterWrapper(applyForPeopleAdapter);
            adapter.addHeaderView(initTopView());
            onRefresh();
            initRecycleViewPull(adapter, adapter.getHeadersCount(), new LinearLayoutManager(this));
        } else {
            getDataBack(applyForPeopleAdapter.getDatas(), datas, adapter);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceSuccess(data,info,status,requestCode);
        switch (requestCode) {
            case 0x124:
                //我的战队管理
                manageTeam = GsonUtil.getInstance().toObj(data, ManageTeam.class);
                GlideUtils.loadImage(manageTeam.getTeamAvatar(), iv_pic);
                tv_title.setText(manageTeam.getTeamName());
                initList(manageTeam.getApprovesData());
                break;
            case 0x123:
                List<ManageTeam.ApprovesDataBean> list = GsonUtil.getInstance().toList(data, ManageTeam.ApprovesDataBean.class);
                initList(list);
                break;
            case 0x125:
                //同意
                applyForPeopleAdapter.getDatas().get(agreedPosition).setPassFlag(true);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void refreshData() {
        if (viewDelegate.page == viewDelegate.defaultPage) {
            addRequest(binder.teamManage(this));
        }else {
            addRequest(binder.approvePage(this));
        }
    }

    @Override
    protected Class<BaseActivityPullDelegate> getDelegateClass() {
        return BaseActivityPullDelegate.class;
    }

    @Override
    public BaseActivityPullBinder getDataBinder(BaseActivityPullDelegate viewDelegate) {
        return new BaseActivityPullBinder(viewDelegate);
    }


}
