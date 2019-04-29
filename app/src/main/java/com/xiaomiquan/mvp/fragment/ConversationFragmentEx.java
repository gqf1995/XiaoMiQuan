package com.xiaomiquan.mvp.fragment;

import android.view.View;
import android.widget.AbsListView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;

import io.rong.imkit.fragment.ConversationFragment;

/**
 * 会话 Fragment 继承自ConversationFragment
 * onResendItemClick: 重发按钮点击事件. 如果返回 false,走默认流程,如果返回 true,走自定义流程
 * onReadReceiptStateClick: 已读回执详情的点击事件.
 * 如果不需要重写 onResendItemClick 和 onReadReceiptStateClick ,可以不必定义此类,直接集成 ConversationFragment 就可以了
 * Created by Yuejunhong on 2016/10/10.
 */
public class ConversationFragmentEx extends ConversationFragment implements AbsListView.OnScrollListener {

    DefaultClickLinsener defaultClickLinsener;

    boolean isCanTalk;
    boolean isClose = false;

    public void setClose(boolean close) {
        isClose = close;
    }

    public boolean isCanTalk() {
        return isCanTalk;
    }

    public void setCanTalk(boolean canTalk) {
        isCanTalk = canTalk;
    }

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }


    public void onWarningDialog(String msg) {
        String typeStr = getUri().getLastPathSegment();
        if (!typeStr.equals("chatroom")) {
            super.onWarningDialog(msg);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSendToggleClick(View v, String text) {
        if (isClose) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_close_chat));
            return;
        }
        if (isCanTalk) {
            super.onSendToggleClick(v, text);
        } else {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_cannot_talk));
        }
    }


}
