package com.xiaomiquan.mvp.delegate;

import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.tablayout.CommonTabLayout;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.user.InputSetActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginAndRegisteredDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    public boolean isLoginInputPhone = true;
    public boolean isRegisteredInputPhone = true;

    private List<String> mTitles = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private String loginName = "";
    private String registeredName = "";
    private String loginPassword = "";
    private String registeredPassword = "";


    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_and_registered;
    }

    public void init() {
        viewHolder.tv_choose_type.setOnClickListener(onClickListener);
        viewHolder.tv_forget_pass.setOnClickListener(onClickListener);
        viewHolder.tv_commit.setOnClickListener(onClickListener);
        tablayout();
        initLogin();
        initPhone();
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_choose_type:
                    if (viewHolder.tl_2.getCurrentTab() == 0) {
                        isLoginInputPhone = !isLoginInputPhone;
                        if (isLoginInputPhone) {
                            initPhone();
                        } else {
                            initEmail();
                        }
                    } else {
                        isRegisteredInputPhone = !isRegisteredInputPhone;
                        if (isRegisteredInputPhone) {
                            initPhone();
                        } else {
                            initEmail();
                        }
                    }
                    initChooseType();
                    cleanInput();
                    break;
                case R.id.tv_forget_pass:
                    if (isLoginInputPhone) {
                        //手机密码找回
                        InputSetActivity.startAct((FragmentActivity) viewHolder.rootView.getContext(), InputSetActivity.FIND_PASSWORD_PHONE);
                    } else {
                        //邮箱密码找回
                        InputSetActivity.startAct((FragmentActivity) viewHolder.rootView.getContext(), InputSetActivity.FIND_PASSWORD_EMAIL);
                    }
                    break;
                case R.id.tv_commit:
                    ToastUtil.show("commit");
                    if (viewHolder.tl_2.getCurrentTab() == 0) {
                        //登录
                        if (isLoginInputPhone) {
                            //手机登录
                        } else {
                            //邮箱登录
                        }

                    } else {
                        //注册
                        if (isLoginInputPhone) {

                        } else {

                        }
                    }
                    break;
            }
        }
    };


    private void tablayout() {
        mTitles.add(CommonUtils.getString(R.string.str_login));
        mTitles.add(CommonUtils.getString(R.string.str_registered));
        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
        }
        viewHolder.tl_2.setIconVisible(false);
        viewHolder.tl_2.setmIndicatorId(R.drawable.shape_white_maxradiu);
        viewHolder.tl_2.setTabData(mTabEntities);
        viewHolder.tl_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    initLogin();
                } else {
                    initRegistered();
                }
                initChooseType();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    public void initLogin() {
        saveInput();
        viewHolder.tv_commit.setText(CommonUtils.getString(R.string.str_login));
        viewHolder.tv_forget_pass.setVisibility(View.VISIBLE);
        viewHolder.lin_agreement.setVisibility(View.GONE);
        setInput();
        if (isLoginInputPhone) {
            initPhone();
        } else {
            initEmail();
        }
    }

    public void initRegistered() {
        saveInput();
        viewHolder.tv_commit.setText(CommonUtils.getString(R.string.str_registered));
        viewHolder.tv_forget_pass.setVisibility(View.GONE);
        viewHolder.lin_agreement.setVisibility(View.GONE);
        setInput();
        if (isRegisteredInputPhone) {
            initPhone();
        } else {
            initEmail();
        }
    }

    private void saveInput() {
        if (viewHolder.tl_2.getCurrentTab() == 1) {
            loginName = viewHolder.et_input1.getText().toString();
            loginPassword = viewHolder.et_input2.getText().toString();
        } else {
            registeredName = viewHolder.et_input1.getText().toString();
            registeredPassword = viewHolder.et_input2.getText().toString();
        }
    }

    private void setInput() {
        if (viewHolder.tl_2.getCurrentTab() == 0) {
            viewHolder.et_input1.setText(loginName);
            viewHolder.et_input2.setText(loginPassword);
        } else {
            viewHolder.et_input1.setText(registeredName);
            viewHolder.et_input2.setText(registeredPassword);
        }
    }

    public void initPhone() {
        viewHolder.tv_input_label1.setText(CommonUtils.getString(R.string.str_login_label_phone));
        viewHolder.et_input1.setHint(CommonUtils.getString(R.string.str_login_et_phone));
        viewHolder.et_input1.setInputType(InputType.TYPE_CLASS_PHONE);
        viewHolder.et_input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        initChooseType();
    }

    private void cleanInput() {
        viewHolder.et_input1.setText("");
        viewHolder.et_input2.setText("");
        if (viewHolder.tl_2.getCurrentTab() == 0) {
            loginName = "";
            loginPassword = "";
        } else {
            registeredName = "";
            registeredPassword = "";
        }
    }


    public void initEmail() {
        viewHolder.tv_input_label1.setText(CommonUtils.getString(R.string.str_login_label_email));
        viewHolder.et_input1.setHint(CommonUtils.getString(R.string.str_login_et_email));
        viewHolder.et_input1.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        viewHolder.et_input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        initChooseType();

    }


    private void initChooseType() {
        String string = "";
        if (viewHolder.tl_2.getCurrentTab() == 0) {
            if (isLoginInputPhone) {
                string = CommonUtils.getString(R.string.str_login_label_email);
            } else {
                string = CommonUtils.getString(R.string.str_login_label_phone);
            }
        } else {
            if (isRegisteredInputPhone) {
                string = CommonUtils.getString(R.string.str_login_label_email);
            } else {
                string = CommonUtils.getString(R.string.str_login_label_phone);
            }
        }
        String string2 = (viewHolder.tl_2.getCurrentTab() == 0 ? CommonUtils.getString(R.string.str_login) : CommonUtils.getString(R.string.str_registered));
        viewHolder.tv_choose_type.setText(string + string2);
    }


    public static class ViewHolder {
        public View rootView;
        public LinearLayout layout_title_bar;
        public CommonTabLayout tl_2;
        public TextView tv_input_label1;
        public MaterialEditText et_input1;
        public LinearLayout lin_input1;
        public TextView tv_input_label2;
        public MaterialEditText et_input2;
        public LinearLayout lin_input2;
        public TextView tv_forget_pass;
        public TextView tv_choose_type;
        public TextView tv_commit;
        public TextView tv_agreement;
        public LinearLayout lin_agreement;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.layout_title_bar = (LinearLayout) rootView.findViewById(R.id.layout_title_bar);
            this.tl_2 = (CommonTabLayout) rootView.findViewById(R.id.tl_2);
            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.et_input1 = (MaterialEditText) rootView.findViewById(R.id.et_input1);
            this.lin_input1 = (LinearLayout) rootView.findViewById(R.id.lin_input1);
            this.tv_input_label2 = (TextView) rootView.findViewById(R.id.tv_input_label2);
            this.et_input2 = (MaterialEditText) rootView.findViewById(R.id.et_input2);
            this.lin_input2 = (LinearLayout) rootView.findViewById(R.id.lin_input2);
            this.tv_forget_pass = (TextView) rootView.findViewById(R.id.tv_forget_pass);
            this.tv_choose_type = (TextView) rootView.findViewById(R.id.tv_choose_type);
            this.tv_commit = (TextView) rootView.findViewById(R.id.tv_commit);
            this.tv_agreement = (TextView) rootView.findViewById(R.id.tv_agreement);
            this.lin_agreement = (LinearLayout) rootView.findViewById(R.id.lin_agreement);
        }

    }
}