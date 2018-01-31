package com.xiaomiquan.mvp.activity.user;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.blankj.utilcode.util.CacheUtils;
import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.CustomRateActivity;
import com.xiaomiquan.mvp.delegate.SetDelegate;
import com.xiaomiquan.utils.UiHeplUtils;
import com.xiaomiquan.widget.CircleDialogHelper;

/**
 * 设置页面
 */
public class SetActivity extends BaseActivity<SetDelegate> {

    @Override
    protected Class<SetDelegate> getDelegateClass() {
        return SetDelegate.class;
    }

    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    viewDelegate.viewHolder.tv_cache_num.setText((String) msg.obj);
                    break;
            }
        }
    };
    Thread thread;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_setting)));
        viewDelegate.setOnClickListener(this, R.id.lin_custom_rate, R.id.lin_clean_cache, R.id.lin_about_us);
        getCacheSize();
    }

    private void getCacheSize() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long cacheSize = CacheUtils.getInstance().getCacheSize();
                Message message = new Message();
                message.what = 1;
                message.obj = UiHeplUtils.getPrintSize(cacheSize);
                handler.sendMessage(message);
            }
        });
        thread.start();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.lin_custom_rate:
                //自定义汇率
                gotoActivity(CustomRateActivity.class).startAct();
                break;
            case R.id.lin_clean_cache:
                //清理缓存
                CircleDialogHelper.initDefaultDialog(SetActivity.this, CommonUtils.getString(R.string.str_is_clean_cache), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CacheUtils.getInstance().clear();
                        getCacheSize();
                    }
                }).show();
                break;
            case R.id.lin_about_us:
                //关于我们

                break;
        }
    }
}
