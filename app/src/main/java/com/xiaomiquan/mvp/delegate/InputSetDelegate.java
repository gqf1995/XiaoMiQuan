package com.xiaomiquan.mvp.delegate;

import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.xiaomiquan.R;

public class InputSetDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_set;
    }

    //重置密码
    public void initSetPassword() {
        viewHolder.tv_input_label1.setText(CommonUtils.getString(R.string.str_new) + CommonUtils.getString(R.string.str_login_label_pass));
        viewHolder.tv_input_label2.setText(CommonUtils.getString(R.string.str_confirm) + CommonUtils.getString(R.string.str_login_label_pass));
        viewHolder.et_input1.setHint(CommonUtils.getString(R.string.str_set_password));
        viewHolder.et_input1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        viewHolder.et_input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        viewHolder.et_input2.setHint(CommonUtils.getString(R.string.str_set_password));
        viewHolder.tv_commit.setText(CommonUtils.getString(R.string.str_complete));
    }

    //手机找回密码
    public void initPhoneFindPass() {
        viewHolder.tv_commit.setText(CommonUtils.getString(R.string.str_next_step));
        viewHolder.lin_input2.setVisibility(View.GONE);
        viewHolder.tv_input_label1.setText(CommonUtils.getString(R.string.str_please_enter) + CommonUtils.getString(R.string.str_registered) + CommonUtils.getString(R.string.str_login_label_phone));
        viewHolder.et_input1.setHint(CommonUtils.getString(R.string.str_login_et_phone));
    }

    //邮箱找回密码
    public void initEmailFindPass() {
        viewHolder.tv_commit.setText(CommonUtils.getString(R.string.str_next_step));
        viewHolder.lin_input2.setVisibility(View.GONE);
        viewHolder.tv_input_label1.setText(CommonUtils.getString(R.string.str_please_enter) + CommonUtils.getString(R.string.str_registered) + CommonUtils.getString(R.string.str_login_label_email));
        viewHolder.et_input1.setHint(CommonUtils.getString(R.string.str_login_et_email));
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_input_label1;
        public MaterialEditText et_input1;
        public LinearLayout lin_input1;
        public TextView tv_input_label2;
        public MaterialEditText et_input2;
        public LinearLayout lin_input2;
        public TextView tv_commit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.et_input1 = (MaterialEditText) rootView.findViewById(R.id.et_input1);
            this.lin_input1 = (LinearLayout) rootView.findViewById(R.id.lin_input1);
            this.tv_input_label2 = (TextView) rootView.findViewById(R.id.tv_input_label2);
            this.et_input2 = (MaterialEditText) rootView.findViewById(R.id.et_input2);
            this.lin_input2 = (LinearLayout) rootView.findViewById(R.id.lin_input2);
            this.tv_commit = (TextView) rootView.findViewById(R.id.tv_commit);
        }

    }
}