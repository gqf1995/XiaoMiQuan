package com.xiaomiquan.mvp.activity.user;

import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.delegate.KlineSetDelegate;
import com.xiaomiquan.utils.UserSet;

/**
 * Created by 郭青枫 on 2018/3/5 0005.
 */

public class KlineSetActivity extends BaseActivity<KlineSetDelegate> {

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_kline_setting)));
        viewDelegate.setOnClickListener(this,
                R.id.lin_set2,
                R.id.checkbox_red_sticker,
                R.id.lin_set3);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_set2:
                //显示默认价格
                ChangeDefaultSetActivity.startAct(this, ChangeDefaultSetActivity.TYPE_UNIT);
                break;
            case R.id.lin_set3:
                //自定义汇率
                gotoActivity(CustomRateActivity.class).startAct();
                break;
            case R.id.checkbox_red_sticker:
                //红涨绿跌
                UserSet.getinstance().setRedRise(viewDelegate.viewHolder.checkbox_red_sticker.isChecked());
                break;
        }
    }

    @Override
    protected Class<KlineSetDelegate> getDelegateClass() {
        return KlineSetDelegate.class;
    }
}
