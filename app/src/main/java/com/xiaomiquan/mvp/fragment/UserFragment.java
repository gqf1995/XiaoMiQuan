package com.xiaomiquan.mvp.fragment;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.user.ChangeDefaultSetActivity;
import com.xiaomiquan.mvp.activity.user.ConversationActivity;
import com.xiaomiquan.mvp.activity.user.HomePageActivity;
import com.xiaomiquan.mvp.activity.user.LoginAndRegisteredActivity;
import com.xiaomiquan.mvp.activity.user.SecurityActivity;
import com.xiaomiquan.mvp.activity.user.SetActivity;
import com.xiaomiquan.mvp.databinder.UserBinder;
import com.xiaomiquan.mvp.delegate.UserDelegate;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.widget.CircleDialogHelper;

/**
 * 个人中心
 */
public class UserFragment extends BaseDataBindFragment<UserDelegate, UserBinder> {

    UserLogin userLogin;

    @Override
    public void onResume() {
        super.onResume();
        //更新用户信息
        userLogin = SingSettingDBUtil.getUserLogin();
        viewDelegate.initUserMsg(userLogin);
    }

    @Override
    protected Class<UserDelegate> getDelegateClass() {
        return UserDelegate.class;
    }

    @Override
    public UserBinder getDataBinder(UserDelegate viewDelegate) {
        return new UserBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_user)).setShowBack(false).setmRightImg1(CommonUtils.getString(R.string.ic_Chat)));
        viewDelegate.setOnClickListener(this
                , R.id.checkbox_night_model
                , R.id.checkbox_red_sticker
                , R.id.lin_user
                , R.id.lin_set0
                , R.id.lin_set3
                , R.id.lin_set4
                , R.id.lin_set5
                , R.id.lin_set6
                , R.id.lin_set7
                , R.id.lin_set8
                , R.id.lin_set9
        );
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.checkbox_night_model:
                //选择夜间模式
                UserSet.getinstance().setNight(!viewDelegate.viewHolder.checkbox_night_model.isChecked());
                viewDelegate.viewHolder.layout_title_bar.setVisibility(View.GONE);
                break;
            case R.id.checkbox_red_sticker:
                //红涨绿跌
                UserSet.getinstance().setRedRise(viewDelegate.viewHolder.checkbox_night_model.isChecked());
                break;
            case R.id.lin_user:
                if (SingSettingDBUtil.isLogin(getActivity())) {
                    //修改用户信息
                }
                break;
            case R.id.lin_set0:
                //我的个人主页
                gotoActivity(HomePageActivity.class).startAct();
                break;
            case R.id.lin_set3:
                //显示默认价格
                ChangeDefaultSetActivity.startAct(getActivity(), ChangeDefaultSetActivity.TYPE_UNIT);
                break;
            case R.id.lin_set4:
                //在线客服
                gotoActivity(ConversationActivity.class).startAct();
                break;
            case R.id.lin_set5:
                //推荐给朋友
                break;
            case R.id.lin_set6:
                //语言切换
                ChangeDefaultSetActivity.startAct(getActivity(), ChangeDefaultSetActivity.TYPE_LANGUAGE);
                break;
            case R.id.lin_set7:
                //安全中心
                gotoActivity(SecurityActivity.class).startAct();
                break;
            case R.id.lin_set8:
                //设置
                gotoActivity(SetActivity.class).startAct();
                break;
            case R.id.lin_set9:
                //退出登录
                logout();
                break;
        }
    }

    private void logout() {
        CircleDialogHelper.initDefaultDialog(getActivity(), CommonUtils.getString(R.string.str_warning_islogout), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingSettingDBUtil.logout();
                //退出登录接口
                addRequest(binder.loginOut());
                gotoActivity(LoginAndRegisteredActivity.class).setIsFinish(true).startAct();
            }
        }).show();
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
        }
    }

}
