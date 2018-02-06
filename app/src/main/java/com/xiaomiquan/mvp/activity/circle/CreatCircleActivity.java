package com.xiaomiquan.mvp.activity.circle;

import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.circle.CreatCircleBinder;
import com.xiaomiquan.mvp.delegate.circle.CreatCircleDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.xiaomiquan.widget.circle.CreatPopupWindow;
import com.xiaomiquan.widget.circle.SquarePopupWindow;

public class CreatCircleActivity extends BaseDataBindActivity<CreatCircleDelegate, CreatCircleBinder> {

    @Override
    protected Class<CreatCircleDelegate> getDelegateClass() {
        return CreatCircleDelegate.class;
    }

    @Override
    public CreatCircleBinder getDataBinder(CreatCircleDelegate viewDelegate) {
        return new CreatCircleBinder(viewDelegate);
    }

    String isFree = "true";

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_create_circle)).setSubTitle(CommonUtils.getString(R.string.str_complete)));
        initView();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if (viewDelegate.viewHolder.ck_free.isChecked()) {
            addRequest(binder.creatCircle(
                    viewDelegate.viewHolder.et_name.getText().toString(),
                    viewDelegate.viewHolder.et_brief.getText().toString(),
                    "科技",
                    isFree,
                    "0",
                    CreatCircleActivity.this
            ));
        } else if (viewDelegate.viewHolder.ck_charge.isChecked()) {
            CircleDialogHelper.initDefaultInputDialog(CreatCircleActivity.this, "设置入圈费用", "请输入金额", "创建", new OnInputClickListener() {
                @Override
                public void onClick(String text, View v) {
                    addRequest(binder.creatCircle(
                            viewDelegate.viewHolder.et_name.getText().toString(),
                            viewDelegate.viewHolder.et_brief.getText().toString(),
                            "科技",
                            isFree,
                            text,
                            CreatCircleActivity.this
                    ));
                }
            }).show();
        } else {
            ToastUtil.show("选择个类型吧");
        }

    }

    private void initView() {
        viewDelegate.viewHolder.lin_choose.setVisibility(View.VISIBLE);
        viewDelegate.viewHolder.lin_next.setVisibility(View.GONE);
        viewDelegate.viewHolder.tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CreatPopupWindow creatPopupWindow = new CreatPopupWindow(CreatCircleActivity.this);
                creatPopupWindow.setOnItemClickListener(new CreatPopupWindow.OnItemClickListener() {
                    @Override
                    public void setOnItemClick(View v) {
                        switch (v.getId()) {
                            case R.id.tv_next:
                                if (creatPopupWindow.ck_agree.isChecked()) {
                                    creatPopupWindow.dismiss();
                                    viewDelegate.viewHolder.lin_choose.setVisibility(View.GONE);
                                    viewDelegate.viewHolder.lin_next.setVisibility(View.VISIBLE);
                                } else {
                                    ToastUtil.show(CommonUtils.getString(R.string.str_toast_user));
                                }
                                break;
                        }
                    }
                });
                creatPopupWindow.showAtLocation(viewDelegate.viewHolder.ck_free, Gravity.BOTTOM, 0, 0);
            }
        });
    }

}
