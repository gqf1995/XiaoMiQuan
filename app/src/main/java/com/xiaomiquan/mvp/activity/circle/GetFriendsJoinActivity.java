package com.xiaomiquan.mvp.activity.circle;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.xiaomiquan.mvp.databinder.GetFriendsJoinBinder;
import com.xiaomiquan.mvp.delegate.GetFriendsJoinDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.widget.CircleDialogHelper;

public class GetFriendsJoinActivity extends BaseDataBindActivity<GetFriendsJoinDelegate, GetFriendsJoinBinder> {

    @Override
    protected Class<GetFriendsJoinDelegate> getDelegateClass() {
        return GetFriendsJoinDelegate.class;
    }

    @Override
    public GetFriendsJoinBinder getDataBinder(GetFriendsJoinDelegate viewDelegate) {
        return new GetFriendsJoinBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));
//        CircleDialogHelper.initDefaultInputDialog()
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
        }
    }

}
