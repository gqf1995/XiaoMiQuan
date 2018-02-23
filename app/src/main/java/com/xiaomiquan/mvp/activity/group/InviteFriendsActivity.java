package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GlobleContext;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.InviteFriendsBinder;
import com.xiaomiquan.mvp.delegate.InviteFriendsDelegate;
import com.xiaomiquan.mvp.dialog.ShareDialog;
import com.xiaomiquan.utils.UiHeplUtils;

import java.util.ArrayList;
import java.util.List;

public class InviteFriendsActivity extends BaseDataBindActivity<InviteFriendsDelegate, InviteFriendsBinder> {

    @Override
    protected Class<InviteFriendsDelegate> getDelegateClass() {
        return InviteFriendsDelegate.class;
    }

    @Override
    public InviteFriendsBinder getDataBinder(InviteFriendsDelegate viewDelegate) {
        return new InviteFriendsBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();

        viewDelegate.setOnClickListener(this, R.id.tv_save, R.id.tv_send);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_save:
                //保存本地
                doSave();
                break;
            case R.id.tv_send:
                //发送给好友
                send();
                break;
        }
    }


    public static void startAct(Activity activity,
                                String type) {
        Intent intent = new Intent(activity, InviteFriendsActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    private String type;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (TextUtils.isEmpty(type)) {
            initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_invite_friends)));
            addRequest(binder.getInviteCode(this));
        } else {
            Glide.with(GlobleContext.getInstance().getApplicationContext())
                    .load(type)
                    .apply(new RequestOptions().skipMemoryCache(true))
                    .into(viewDelegate.viewHolder.iv_pic);
            initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_recommended)));
        }
    }

    private void send() {
        View view = viewDelegate.viewHolder.iv_pic;
        if (view instanceof ImageView) {
            Bitmap image = ((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap();
            List<Bitmap> bitmaps = new ArrayList<>();
            bitmaps.add(image);
            UiHeplUtils.shareImgs(this, bitmaps);
        }
    }

    private void doSave() {
        //保存当前展示图片到手机
        View view = viewDelegate.viewHolder.iv_pic;
        if (view instanceof ImageView) {
            Bitmap image = ((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap();
            List<Bitmap> bitmaps = new ArrayList<>();
            List<String> names = new ArrayList<>();
            bitmaps.add(image);
            names.add("/sdcard/" + "BCoin" + "_pic" + ".png");
            ShareDialog.downBitmapToFile(this, bitmaps, names, true);
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                Glide.with(GlobleContext.getInstance().getApplicationContext())
                        .load(data)
                        .apply(new RequestOptions().skipMemoryCache(true))
                        .into(viewDelegate.viewHolder.iv_pic);
                break;
        }
    }

}
