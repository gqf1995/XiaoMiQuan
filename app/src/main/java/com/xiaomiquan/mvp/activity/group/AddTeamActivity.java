package com.xiaomiquan.mvp.activity.group;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.HotTeamAdapter;
import com.xiaomiquan.entity.bean.group.HotTeam;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

public class AddTeamActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {


    HeaderAndFooterWrapper adapter;
    HotTeamAdapter hotTeamAdapter;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_apply_to_join_team)));
        initList(new ArrayList<HotTeam>());
    }

    private void initList(List<HotTeam> datas) {
        if (adapter == null) {
            hotTeamAdapter = new HotTeamAdapter(this, datas);
            hotTeamAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    joinTeam();
                }
            });
            adapter = new HeaderAndFooterWrapper(hotTeamAdapter);
            adapter.addHeaderView(initTop());
            initRecycleViewPull(adapter, adapter.getHeadersCount(), new LinearLayoutManager(this));
        } else {
            getDataBack(hotTeamAdapter.getDatas(), datas, adapter);
        }
    }

    private void joinTeam() {
        CircleDialogHelper.initDefaultInputDialog(this,
                CommonUtils.getString(R.string.str_apply_to_join_team_reason),
                CommonUtils.getString(R.string.str_toast_input_reason),
                CommonUtils.getString(R.string.str_determine),
                new OnInputClickListener() {
                    @Override
                    public void onClick(String text, View v) {
                        //申请加入战队

                    }
                }
        ).show();
    }

    public EditText et_invite_code;
    public TextView tv_commit;
    public TextView tv_more_competitio_rules;

    private View initTop() {
        View rootView = getLayoutInflater().inflate(R.layout.layout_add_team_top, null);
        this.et_invite_code = (EditText) rootView.findViewById(R.id.et_invite_code);
        this.tv_commit = (TextView) rootView.findViewById(R.id.tv_commit);
        this.tv_more_competitio_rules = (TextView) rootView.findViewById(R.id.tv_more_competitio_rules);
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeam();
            }
        });
        tv_more_competitio_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更多比赛规则

            }
        });
        return rootView;
    }

    private void addTeam() {
        if (TextUtils.isEmpty(et_invite_code.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_invite_code));
            return;
        }
    }

    public static void startAct(Fragment activity,
                                int requestCode) {
        Intent intent = new Intent(activity.getActivity(), AddTeamActivity.class);
        activity.startActivityForResult(intent, requestCode);
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
