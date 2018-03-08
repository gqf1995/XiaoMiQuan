package com.xiaomiquan.mvp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.base.Application;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.group.InviteFriendsActivity;
import com.xiaomiquan.mvp.activity.user.ChangeUserInfoActivity;
import com.xiaomiquan.mvp.activity.user.KlineSetActivity;
import com.xiaomiquan.mvp.activity.user.LoginAndRegisteredActivity;
import com.xiaomiquan.mvp.activity.user.PersonalDetailsActivity;
import com.xiaomiquan.mvp.activity.user.SetActivity;
import com.xiaomiquan.mvp.databinder.UserDrawerBinder;
import com.xiaomiquan.mvp.delegate.UserDrawerDelegate;
import com.xiaomiquan.widget.CircleDialogHelper;

import static android.app.Activity.RESULT_OK;

public class UserDrawerFragment extends BaseDataBindFragment<UserDrawerDelegate, UserDrawerBinder> {
    String defaultSharePath = "http://topcoin.oss-cn-hangzhou.aliyuncs.com/topcoin/share/template/invite.png";

    public interface Linsener {
        void logout();
    }

    Linsener linsener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        linsener = (Linsener) activity;
    }

    UserLogin userLogin;

    @Override
    protected Class<UserDrawerDelegate> getDelegateClass() {
        return UserDrawerDelegate.class;
    }

    @Override
    public UserDrawerBinder getDataBinder(UserDrawerDelegate viewDelegate) {
        return new UserDrawerBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.setOnClickListener(this,
                R.id.lin1,
                R.id.lin2,
                R.id.lin3,
                R.id.lin4,
                R.id.lin5,
                R.id.lin6,
                R.id.lin7,
                R.id.lin8,
                R.id.lin_user
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                userLogin = SingSettingDBUtil.getUserLogin();
                viewDelegate.initUserMsg(userLogin);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userLogin = SingSettingDBUtil.getUserLogin();
        viewDelegate.initUserMsg(userLogin);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin1:
                if (SingSettingDBUtil.isLogin(getActivity())) {
                    PersonalDetailsActivity.startAct(getActivity(), userLogin.getId() + "",0x123);
                }
                break;
            case R.id.lin2:
                break;
            case R.id.lin3:
                break;
            case R.id.lin4:
                //在线客服
                if (SingSettingDBUtil.isLogin(getActivity())) {
                    Application.getInstance().startCustomerService(getActivity());
                }
                break;
            case R.id.lin5:
                gotoActivity(KlineSetActivity.class).startAct();
                break;
            case R.id.lin6:
                //设置
                gotoActivity(SetActivity.class).startAct();
                break;
            case R.id.lin7:
                //推荐给朋友
                InviteFriendsActivity.startAct(getActivity(), defaultSharePath);
                break;
            case R.id.lin8:
                //退出登录
                logout();
                break;
            case R.id.lin_user:
                if (SingSettingDBUtil.isLogin(getActivity())) {
                    //修改用户信息
                    ChangeUserInfoActivity.startAct(this, 0x123);
                }
                break;
        }
    }

    private void logout() {
        CircleDialogHelper.initDefaultDialog(getActivity(), CommonUtils.getString(R.string.str_warning_islogout), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingSettingDBUtil.logout();
                linsener.logout();
                //退出登录接口
                addRequest(binder.loginOut());
                gotoActivity(LoginAndRegisteredActivity.class).setIsFinish(true).startAct();
            }
        }).show();
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
