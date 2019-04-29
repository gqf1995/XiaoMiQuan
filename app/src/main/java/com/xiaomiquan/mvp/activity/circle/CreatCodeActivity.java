package com.xiaomiquan.mvp.activity.circle;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.CleanUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.StringUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.mvp.databinder.circle.CreatCodeBinder;
import com.xiaomiquan.mvp.delegate.circle.CreatCodeDelegate;

public class CreatCodeActivity extends BaseDataBindActivity<CreatCodeDelegate, CreatCodeBinder> {

    //剪切板管理工具类
    private ClipboardManager mClipboardManager;
    //剪切板Data对象
    private ClipData mClipData;

    @Override
    protected Class<CreatCodeDelegate> getDelegateClass() {
        return CreatCodeDelegate.class;
    }

    @Override
    public CreatCodeBinder getDataBinder(CreatCodeDelegate viewDelegate) {
        return new CreatCodeBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle("生成邀请码"));
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        viewDelegate.viewHolder.tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRequest(binder.creatCircleCode(CircleContentActivity.groupId,CreatCodeActivity.this));
            }
        });

        viewDelegate.viewHolder.tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copy = viewDelegate.viewHolder.tv_code.getText().toString().trim();
                if (TextUtils.isEmpty(copy)) {
                   ToastUtil.show("请生成邀请码");
                    return;
                }
                //创建一个新的文本clip对象
                mClipData = ClipData.newPlainText("Simple test", copy);
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);
                ToastUtil.show("复制成功");
            }
        });


    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                viewDelegate.viewHolder.tv_code.setText(data + "");
                break;
        }
    }

}
