package com.xiaomiquan.mvp.fragment;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.user.SecurityActivity;
import com.xiaomiquan.mvp.activity.user.ChangeDefaultSetActivity;
import com.xiaomiquan.mvp.activity.user.ConversationActivity;
import com.xiaomiquan.mvp.activity.user.SetActivity;
import com.xiaomiquan.mvp.databinder.UserBinder;
import com.xiaomiquan.mvp.delegate.UserDelegate;

public class UserFragment extends BaseDataBindFragment<UserDelegate, UserBinder> {

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
        viewDelegate.setNoStatusBarFlag(true);
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_user)).setShowBack(false).setSubTitle(CommonUtils.getString(R.string.ic_zhankai)));
        viewDelegate.setOnClickListener(this
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

                break;
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();

    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
