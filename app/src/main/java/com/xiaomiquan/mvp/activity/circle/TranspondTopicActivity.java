package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.mvp.databinder.circle.TranspondTopicBinder;
import com.xiaomiquan.mvp.delegate.circle.TranspondTopicDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;

public class TranspondTopicActivity extends BaseDataBindActivity<TranspondTopicDelegate, TranspondTopicBinder> {

    @Override
    protected Class<TranspondTopicDelegate> getDelegateClass() {
        return TranspondTopicDelegate.class;
    }

    @Override
    public TranspondTopicBinder getDataBinder(TranspondTopicDelegate viewDelegate) {
        return new TranspondTopicBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_transpond_topic)).setSubTitle(CommonUtils.getString(R.string.str_release)));

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

    private void initView(SquareLive squareLive) {
        viewDelegate.viewHolder.tv_shared_name.setText(squareLive.getNickName());
        viewDelegate.viewHolder.tv_shared_brief.setText(squareLive.getContent());
        GlideUtils.loadImage(squareLive.getAvatar(), viewDelegate.viewHolder.cv_shard_head);
        GlideUtils.loadImage(squareLive.getShowImg(), viewDelegate.viewHolder.iv_shared);
    }

    SquareLive squareLive;

    private void getIntentData() {
        Intent intent = getIntent();
        squareLive = (SquareLive) intent.getParcelableExtra("squareLive");
        initView(squareLive);

    }

    public static void startAct(Activity activity,
                                SquareLive squareLive
    ) {
        Intent intent = new Intent(activity, TranspondTopicActivity.class);
        intent.putExtra("squareLive", squareLive);
        activity.startActivity(intent);
    }


}
