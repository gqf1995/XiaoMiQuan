package com.xiaomiquan.mvp.activity.group;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BasePullActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.group.HotTeamAdapter;
import com.xiaomiquan.entity.bean.group.HotTeam;
import com.xiaomiquan.mvp.activity.main.WebActivityActivity;
import com.xiaomiquan.mvp.activity.user.PersonalHomePageActivity;
import com.xiaomiquan.mvp.databinder.BaseActivityPullBinder;
import com.xiaomiquan.mvp.delegate.BaseActivityPullDelegate;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.xiaomiquan.base.AppConst.rulesUrl;

public class AddTeamActivity extends BasePullActivity<BaseActivityPullDelegate, BaseActivityPullBinder> {


    HeaderAndFooterWrapper adapter;
    HotTeamAdapter hotTeamAdapter;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_apply_to_join_team)));
        initList(new ArrayList<HotTeam>());
    }

    private void initList(List<HotTeam> datas) {
        if (adapter == null) {
            hotTeamAdapter = new HotTeamAdapter(this, datas);
            hotTeamAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    if(view.getId()==R.id.fl_root||view.getId()==R.id.tv_apply_to_join){
                        TeamDetailActivity.startAct(AddTeamActivity.this, hotTeamAdapter.getDatas().get(position-adapter.getHeadersCount()).getId() + "");
                    }else if(view.getId()==R.id.ic_pic){
                        PersonalHomePageActivity.startAct(AddTeamActivity.this,hotTeamAdapter.getDatas().get(position-adapter.getHeadersCount()).getOwnerId()+"");
                    }

                }
            });
            adapter = new HeaderAndFooterWrapper(hotTeamAdapter);
            adapter.addHeaderView(initTop());
            onRefresh();
            initRecycleViewPull(adapter, adapter.getHeadersCount(), new LinearLayoutManager(this));
        } else {
            getDataBack(hotTeamAdapter.getDatas(), datas, adapter);
        }
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
                WebActivityActivity.startAct(AddTeamActivity.this, rulesUrl);
            }
        });
        return rootView;
    }

    private void addTeam() {
        if (TextUtils.isEmpty(et_invite_code.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_invite_code));
            return;
        }
        addRequest(binder.searchTemaCode(et_invite_code.getText().toString(), this));
    }

    String rules;

    public static void startAct(Fragment activity,
                                String rules,
                                int requestCode) {
        Intent intent = new Intent(activity.getActivity(), AddTeamActivity.class);
        intent.putExtra("rules", rules);
        activity.startActivityForResult(intent, requestCode);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        rules = intent.getStringExtra("rules");
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceSuccess(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                //热门战队
                List<HotTeam> list = GsonUtil.getInstance().toList(data, HotTeam.class);
                initList(list);
                break;
            case 0x124:
                //搜索出战队
                HotTeam team = GsonUtil.getInstance().toObj(data, HotTeam.class);
                TeamDetailActivity.startAct(this, team.getId() + "");
                break;
        }
    }

    @Override
    protected void refreshData() {
        addRequest(binder.listHotGameTeam(this));
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
