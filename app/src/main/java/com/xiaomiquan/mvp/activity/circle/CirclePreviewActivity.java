package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.CircleDynamicAdapter;
import com.xiaomiquan.adapter.circle.SquareLiveAdapter;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.circle.CirclePreviewBinder;
import com.xiaomiquan.mvp.delegate.circle.CirclePreviewDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.xiaomiquan.widget.CircleDialogHelper;

import java.util.ArrayList;
import java.util.List;

public class CirclePreviewActivity extends BaseDataBindActivity<CirclePreviewDelegate, CirclePreviewBinder> {


    CircleDynamicAdapter circleDynamicAdapter;

    @Override
    protected Class<CirclePreviewDelegate> getDelegateClass() {
        return CirclePreviewDelegate.class;
    }

    @Override
    public CirclePreviewBinder getDataBinder(CirclePreviewDelegate viewDelegate) {
        return new CirclePreviewBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(userCircle.getName()));
        initUserTopic(new ArrayList<SquareLive>());
        addRequest(binder.getCicleInfo(userCircle.getId(), CirclePreviewActivity.this));
    }

    private void initView() {
        viewDelegate.viewHolder.tv_title.setText(userCircle.getName());
        if (userCircle.isFree()) {
            viewDelegate.viewHolder.lin_free.setVisibility(View.VISIBLE);
            viewDelegate.viewHolder.lin_charge.setVisibility(View.GONE);
        } else {
            viewDelegate.viewHolder.lin_free.setVisibility(View.GONE);
            viewDelegate.viewHolder.lin_charge.setVisibility(View.VISIBLE);
        }
        viewDelegate.viewHolder.tv_title.setText(userCircle.getName());
        viewDelegate.viewHolder.tv_con.setText(userCircle.getBrief());
        viewDelegate.viewHolder.tv_creator.setText(userCircle.getNickName());
        viewDelegate.viewHolder.tv_num.setText(userCircle.getMemberCount());
        GlideUtils.loadImage(userCircle.getAvatar(), viewDelegate.viewHolder.iv_head);

        viewDelegate.viewHolder.tv_free.setOnClickListener(this);
        viewDelegate.viewHolder.tv_code.setOnClickListener(this);
        viewDelegate.viewHolder.tv_pay.setOnClickListener(this);

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<SquareLive> datas = GsonUtil.getInstance().toList(data, SquareLive.class);
                initUserTopic(datas);
                break;
            case 0x124:
                CircleContentActivity.startAct(CirclePreviewActivity.this, userCircle);
                onBackPressed();
                break;
        }
    }

    private void initUserTopic(List<SquareLive> squareLives) {
        if (squareLives.size() <= 3) {
            if (circleDynamicAdapter == null) {

                circleDynamicAdapter = new CircleDynamicAdapter(binder, CirclePreviewActivity.this, squareLives);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CirclePreviewActivity.this) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                viewDelegate.viewHolder.ry_dynamic.setLayoutManager(linearLayoutManager);
                viewDelegate.viewHolder.ry_dynamic.setAdapter(circleDynamicAdapter);
            } else {
                circleDynamicAdapter.setDatas(squareLives);
            }
        }
    }

    public static void startAct(Activity activity,
                                UserCircle userCircle
    ) {
        Intent intent = new Intent(activity, CirclePreviewActivity.class);
        intent.putExtra("userCircle", userCircle);
        activity.startActivity(intent);
    }

    UserCircle userCircle;

    private void getIntentData() {
        Intent intent = getIntent();
        userCircle = intent.getParcelableExtra("userCircle");
        initView();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_pay:
                break;
            case R.id.tv_free:
                addRequest(binder.joinCircle(userCircle.getId(), "", CirclePreviewActivity.this));
                break;
            case R.id.tv_code:
                CircleDialogHelper.initDefaultInputDialog(CirclePreviewActivity.this, "请输入加圈码", "请输入加圈码", "加入", new OnInputClickListener() {
                    @Override
                    public void onClick(String text, View v) {
                        addRequest(binder.joinCircle(userCircle.getId(), text, CirclePreviewActivity.this));
                    }
                }).show();
                break;

        }
    }
}
