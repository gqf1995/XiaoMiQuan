package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.circle.EditCircleBinder;
import com.xiaomiquan.mvp.delegate.circle.EditCircleDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;

public class EditCircleActivity extends BaseDataBindActivity<EditCircleDelegate, EditCircleBinder> {

    @Override
    protected Class<EditCircleDelegate> getDelegateClass() {
        return EditCircleDelegate.class;
    }

    @Override
    public EditCircleBinder getDataBinder(EditCircleDelegate viewDelegate) {
        return new EditCircleBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("编辑币圈信息").setSubTitle("完成"));
        getIntentData();
        initView();
        viewDelegate.viewHolder.lin_set1.setOnClickListener(this);
        viewDelegate.viewHolder.lin_set2.setOnClickListener(this);
        viewDelegate.viewHolder.lin_set3.setOnClickListener(this);
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        addRequest(binder.editCircleInfo(
                userCircle.getId(),
                viewDelegate.viewHolder.tv_title.getText().toString(),
                viewDelegate.viewHolder.tv_con.getText().toString(),
                this
        ));
    }

    public void initView() {
        GlideUtils.loadImage(userCircle.getAvatar(), viewDelegate.viewHolder.iv_head);
        viewDelegate.viewHolder.tv_title.setText(userCircle.getName());
        viewDelegate.viewHolder.tv_con.setText(userCircle.getBrief());
        viewDelegate.viewHolder.tv_people_num.setText(userCircle.getMemberCount());
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                onBackPressed();
                break;

        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_set1:
//                gotoActivity(EditCircleActivity.class).startAct();
                break;
            case R.id.lin_set2:
                gotoActivity(JurisdictionActivity.class).startAct();
                break;
            case R.id.lin_set3:
                gotoActivity(MembersActivity.class).startAct();
                break;
        }
    }

    UserCircle userCircle;

    private void getIntentData() {
        Intent intent = getIntent();
        userCircle = (UserCircle) intent.getParcelableExtra("userCircle");
    }

    public static void startAct(Activity activity,
                                UserCircle userCircle
    ) {
        Intent intent = new Intent(activity, EditCircleActivity.class);
        intent.putExtra("userCircle", userCircle);
        activity.startActivity(intent);
    }

}
