package com.xiaomiquan.mvp.activity.group;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.ApplyForPeopleAdapter;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ManagementTeamActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {

    ApplyForPeopleAdapter applyForPeopleAdapter;
    HeaderAndFooterWrapper adapter;


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_my_management_team)));
        initList(new ArrayList<String>());
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
                gotoActivity(TeamDetailActivity.class).startAct();
            }
        });
        lin_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(EditTeamIntroductionActivity.class).startAct();
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

    private void initList(List<String> datas) {
        for (int i = 0; i < 10; i++) {
            datas.add("");
        }
        if (adapter == null) {
            applyForPeopleAdapter = new ApplyForPeopleAdapter(this, datas);
            applyForPeopleAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    //同意加入
                }
            });
            adapter = new HeaderAndFooterWrapper(applyForPeopleAdapter);
            adapter.addHeaderView(initTopView());
            initRecycleViewPull(adapter, adapter.getHeadersCount(), new LinearLayoutManager(this));
        } else {
            getDataBack(applyForPeopleAdapter.getDatas(), datas, adapter);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
        }
    }

    @Override
    protected void refreshData() {

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
