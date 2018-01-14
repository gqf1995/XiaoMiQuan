package com.xiaomiquan.mvp.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.WebActivityBinder;
import com.xiaomiquan.mvp.delegate.WebActivityDelegate;
import com.xiaomiquan.mvp.fragment.WebFragment;

public class WebActivityActivity extends BaseDataBindActivity<WebActivityDelegate, WebActivityBinder> {

    WebFragment webFragment;

    @Override
    protected Class<WebActivityDelegate> getDelegateClass() {
        return WebActivityDelegate.class;
    }

    @Override
    public WebActivityBinder getDataBinder(WebActivityDelegate viewDelegate) {
        return new WebActivityBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        getIntentData();
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(""));
        viewDelegate.initAddFragment(R.id.fl_root, getSupportFragmentManager());
        webFragment = WebFragment.newInstance(type);
        viewDelegate.addFragment(webFragment);
        viewDelegate.getmToolbarBackLin().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webFragment.goBack()) {
                    onBackPressed();
                }
            }
        });
        viewDelegate.showFragment(0);

        //        ViewGroup.LayoutParams layoutParams = viewDelegate.viewHolder.fl_root.getLayoutParams();
        //        layoutParams.height = AndroidUtil.getScreenW(this, true) - (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_110px);
        //        viewDelegate.viewHolder.fl_root.setLayoutParams(layoutParams);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webFragment.goBack()) {
                onBackPressed();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public static void startAct(Activity activity,
                                String type
    ) {
        Intent intent = new Intent(activity, WebActivityActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    private String type;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
