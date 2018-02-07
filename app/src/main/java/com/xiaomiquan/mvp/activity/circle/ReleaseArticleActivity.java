package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.circle.ReleaseArticleBinder;
import com.xiaomiquan.mvp.delegate.circle.ReleaseArticleDelegate;
import com.xiaomiquan.utils.UiHeplUtils;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.durban.Durban;

import java.io.File;
import java.util.ArrayList;


public class ReleaseArticleActivity extends BaseDataBindActivity<ReleaseArticleDelegate, ReleaseArticleBinder> {


    @Override
    protected Class<ReleaseArticleDelegate> getDelegateClass() {
        return ReleaseArticleDelegate.class;
    }

    @Override
    public ReleaseArticleBinder getDataBinder(ReleaseArticleDelegate viewDelegate) {
        return new ReleaseArticleBinder(viewDelegate);
    }

    String title;
    File file;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initView();
        initToolbar(new ToolbarBuilder().setTitle(title).setSubTitle(CommonUtils.getString(R.string.str_release)));
    }

    private void initView() {
        viewDelegate.viewHolder.icf_update_img.setOnClickListener(this);
        if (wechat.equals("0")) {
            title = CommonUtils.getString(R.string.str_release_article);
            viewDelegate.viewHolder.et_input2.setVisibility(View.VISIBLE);
            viewDelegate.viewHolder.et_con.setHint(R.string.str_et_con);
        } else {
            title = CommonUtils.getString(R.string.str_deal_article);
            viewDelegate.viewHolder.et_input2.setVisibility(View.INVISIBLE);
            viewDelegate.viewHolder.et_con.setHint(R.string.str_et_url);
        }
        if (platform.equals("1")) {
            viewDelegate.viewHolder.ck_live.setChecked(true);
            viewDelegate.viewHolder.ck_circle.setChecked(false);
            viewDelegate.viewHolder.ck_live.setClickable(false);
            viewDelegate.viewHolder.ck_circle.setClickable(true);

        } else {
            viewDelegate.viewHolder.ck_live.setChecked(false);
            viewDelegate.viewHolder.ck_circle.setChecked(true);
            viewDelegate.viewHolder.ck_live.setClickable(true);
            viewDelegate.viewHolder.ck_circle.setClickable(false);

        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

        switch (requestCode) {
            case 0x123:
                onBackPressed();
                break;
            case 0x124:
                onBackPressed();
                break;
            case 0x125:
                onBackPressed();
                break;
        }
    }

    private void save(Boolean sync, File file, String groupId) {
        addRequest(binder.releaseArticle(
                file,
                viewDelegate.viewHolder.et_input2.getText().toString(),
                viewDelegate.viewHolder.et_con.getText().toString(),
                type,
                platform,
                sync,
                groupId, this));
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if (check()) {
            if (platform.equals("1")) {
                if (wechat.equals("0")) {
                    save(viewDelegate.viewHolder.ck_circle.isChecked(), file, "");
                } else {
                    addRequest(binder.leadSquareArticle(file,
                            viewDelegate.viewHolder.et_con.getText().toString(),
                            viewDelegate.viewHolder.ck_live.isChecked(),
                            this
                    ));
                }
            } else {
                if (wechat.equals("0")) {
                    save(viewDelegate.viewHolder.ck_live.isChecked(), file, CircleContentActivity.groupId);
                } else {
                    addRequest(binder.leadCircleArticle(file,
                            CircleContentActivity.groupId,
                            viewDelegate.viewHolder.et_con.getText().toString(),
                            viewDelegate.viewHolder.ck_live.isChecked(),
                            this
                    ));
                }

            }
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_con.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_article_con));
            return false;
        }
        if (!file.exists()) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_img));
            return false;
        }
        return true;
    }


    String type;
    String platform;
    String wechat;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        platform = intent.getStringExtra("platform");
        wechat = intent.getStringExtra("wechat");
    }

    /**
     * @param activity
     * @param type     发布类型
     * @param platform
     * @param wechat
     */
    public static void startAct(Activity activity, String type, String platform, String wechat) {
        Intent intent = new Intent(activity, ReleaseArticleActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("platform", platform);
        intent.putExtra("wechat", wechat);
        activity.startActivity(intent);
    }

    private void initPop() {
        UiHeplUtils.getPhoto(this, new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        //拍照
                        file = new File(result);
                        Uri uri = Uri.fromFile(file);
                        GlideUtils.loadImage(uri, viewDelegate.viewHolder.iv_img);
                    }
                }, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        //相册
                        file = new File(result.get(0).getPath());
                        Uri uri = Uri.fromFile(file);
                        GlideUtils.loadImage(uri, viewDelegate.viewHolder.iv_img);
                    }
                }, 1
        );
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.icf_update_img:
                initPop();
                break;
        }
    }


}
