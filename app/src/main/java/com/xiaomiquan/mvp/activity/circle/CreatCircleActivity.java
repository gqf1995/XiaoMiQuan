package com.xiaomiquan.mvp.activity.circle;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.circle.CreatCircleBinder;
import com.xiaomiquan.mvp.delegate.circle.CreatCircleDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.widget.CircleDialogHelper;

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
        initToolbar(new ToolbarBuilder().setTitle("创建圈子").setSubTitle("完成"));
        initView();
        initRadioGroup();
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
    protected void clickRightTv() {
        super.clickRightTv();
        if (isFree.equals("true")) {
            addRequest(binder.creatCircle(
                    viewDelegate.viewHolder.et_name.getText().toString(),
                    viewDelegate.viewHolder.et_brief.getText().toString(),
                    "科技",
                    isFree,
                    "0",
                    CreatCircleActivity.this
            ));
        } else {
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
        }

    }

    private void initRadioGroup() {
        viewDelegate.viewHolder.rg_choose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_free:
                        isFree = "true";
                        break;
                    case R.id.rb_charge:
                        isFree = "false";
                        break;
                }
            }
        });
    }

    private void initView() {
        viewDelegate.viewHolder.lin_choose.setVisibility(View.VISIBLE);
        viewDelegate.viewHolder.lin_next.setVisibility(View.GONE);

        viewDelegate.viewHolder.tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewDelegate.viewHolder.ck_agree.isChecked()) {
                    viewDelegate.viewHolder.lin_choose.setVisibility(View.GONE);
                    viewDelegate.viewHolder.lin_next.setVisibility(View.VISIBLE);
                } else {
                    ToastUtil.show("请阅读用户规范");
                }

            }
        });
    }

}
