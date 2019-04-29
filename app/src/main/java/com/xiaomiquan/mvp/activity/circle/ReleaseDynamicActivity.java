package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GlobleContext;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.view.AddPicAdapter;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.ReleaseDynamicAdapter;
import com.xiaomiquan.mvp.databinder.circle.ReleaseDynamicBinder;
import com.xiaomiquan.mvp.delegate.circle.ReleaseDynamicDelegate;
import com.xiaomiquan.utils.UiHeplUtils;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class ReleaseDynamicActivity extends BaseDataBindActivity<ReleaseDynamicDelegate, ReleaseDynamicBinder> {

    AddPicAdapter addPicAdapter;
    List<String> choosePaths;

    @Override
    protected Class<ReleaseDynamicDelegate> getDelegateClass() {
        return ReleaseDynamicDelegate.class;
    }

    @Override
    public ReleaseDynamicBinder getDataBinder(ReleaseDynamicDelegate viewDelegate) {
        return new ReleaseDynamicBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_release_dynamic)).setSubTitle(CommonUtils.getString(R.string.str_release)));
        getIntentData();
        initView();
        choosePaths = new ArrayList<>();
        initImg(choosePaths);


    }

    public void initImg(List<String> paths) {
        addPicAdapter = new AddPicAdapter(this, choosePaths);
        UiHeplUtils.initChoosePicRv(choosePaths,
                addPicAdapter,
                this,
                viewDelegate.viewHolder.rv_img,
                3,
                R.dimen.trans_120px,
                0,
                true,
                6
        );
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if (check()) {
            if (platform.equals("1")) {
                imgLuban(choosePaths);
            } else {
                if (choosePaths.size() > 0) {
                    UiHeplUtils.stringsToFiles(choosePaths);
                    initRealeseCircle(UiHeplUtils.stringsToFiles(choosePaths), String.valueOf(viewDelegate.viewHolder.ck_live.isChecked()));

                } else {
                    initRealeseCircle(null, String.valueOf(viewDelegate.viewHolder.ck_live.isChecked()));
                }

            }
        }
    }

    private void initView() {
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

    private void initRealeseSquare(List<File> files, String sync) {
        addRequest(binder.releaseDynamic(files,
                viewDelegate.viewHolder.et_con.getText().toString(),
                type,
                platform,
                sync,
                this));
    }

    private void initRealeseCircle(List<File> files, String sync) {
        addRequest(binder.releaseDynamicCircle(
                files,
                viewDelegate.viewHolder.et_con.getText().toString(),
                type,
                platform,
                sync,
                CircleContentActivity.groupId,
                this));
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
            case 0x123:
                onBackPressed();
                break;
            case 0x124:
                onBackPressed();
                break;
        }
    }

    String type;
    String platform;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        platform = intent.getStringExtra("platform");
    }

    public static void startAct(Activity activity, String type, String platform) {
        Intent intent = new Intent(activity, ReleaseDynamicActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("platform", platform);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_con.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_article_con));
            return false;
        }
        return true;
    }

    //图片压缩
    private void imgLuban(List<String> paths) {
        Flowable.just(paths)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@io.reactivex.annotations.NonNull List<String> list) throws Exception {
                        // 同步方法直接返回压缩后的文件
                        return Luban.with(GlobleContext.getInstance().getApplicationContext()).load(list).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {//onNext()
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<File> stringResponse) throws Exception {
                        initRealeseSquare(stringResponse, String.valueOf(viewDelegate.viewHolder.ck_circle.isChecked()));
                    }
                });
    }

}
