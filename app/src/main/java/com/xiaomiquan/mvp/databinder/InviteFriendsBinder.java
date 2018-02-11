package com.xiaomiquan.mvp.databinder;

import com.xiaomiquan.mvp.delegate.InviteFriendsDelegate;
import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.fivefivelike.mybaselibrary.http.HttpRequest;
import com.fivefivelike.mybaselibrary.http.RequestCallback;

import io.reactivex.disposables.Disposable;

public class InviteFriendsBinder extends BaseDataBind<InviteFriendsDelegate> {

    public InviteFriendsBinder(InviteFriendsDelegate viewDelegate) {
        super(viewDelegate);
    }


}