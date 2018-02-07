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
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.ReleaseDynamicAdapter;
import com.xiaomiquan.mvp.databinder.circle.ReleaseDynamicBinder;
import com.xiaomiquan.mvp.delegate.circle.ReleaseDynamicDelegate;
import com.xiaomiquan.utils.UiHeplUtils;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.durban.Durban;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReleaseDynamicActivity extends BaseDataBindActivity<ReleaseDynamicDelegate, ReleaseDynamicBinder> {

    ReleaseDynamicAdapter releaseDynamicAdapter;
    Boolean isFirst = true;


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


        if (isFirst) {
            ArrayList<AlbumFile> path = new ArrayList<>();
            initImg(path);
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if (check()) {
            if (platform.equals("1")) {
                initRealeseSquare(releaseDynamicAdapter.fileList, String.valueOf(viewDelegate.viewHolder.ck_circle.isChecked()));
            } else {
                initRealeseCircle(releaseDynamicAdapter.fileList, String.valueOf(viewDelegate.viewHolder.ck_live.isChecked()));
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

    private void initImg(ArrayList<AlbumFile> files) {
        files.add(0, null);
        releaseDynamicAdapter = new ReleaseDynamicAdapter(ReleaseDynamicActivity.this, files);
        releaseDynamicAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position == 0) {
                    if (releaseDynamicAdapter.albumFiles.size() < 7) {
                        initPop(7 - releaseDynamicAdapter.albumFiles.size());
                    } else {
                        ToastUtil.show(CommonUtils.getString(R.string.str_rv_img));
                    }
                } else {
                    /// TODO: 2018/2/2  删除图片操作
                    UiHeplUtils.galleryPhoto(ReleaseDynamicActivity.this,
                            new Action<ArrayList<String>>() { // 如果checkable(false)，那么action不用传。
                                @Override
                                public void onAction(int requestCode, @NonNull ArrayList<String> result) {
                                    List<String> list = result;
                                    releaseDynamicAdapter.albumFiles.removeAll(releaseDynamicAdapter.albumFiles);
                                    releaseDynamicAdapter.albumFiles.addAll((UiHeplUtils.stringsToAlbumFiles(list)));
                                    isFirst = false;
                                    initImg(releaseDynamicAdapter.albumFiles);
                                }
                            }, new Action<String>() {
                                @Override
                                public void onAction(int requestCode, @NonNull String result) {
                                }
                            }, true,
                            releaseDynamicAdapter.path,
                            CommonUtils.getString(R.string.str_img_title)
                    );
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ReleaseDynamicActivity.this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewDelegate.viewHolder.rv_img.setLayoutManager(gridLayoutManager);
        viewDelegate.viewHolder.rv_img.setAdapter(releaseDynamicAdapter);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case UiHeplUtils.CROP_CODE_1:
                    releaseDynamicAdapter.albumFiles.remove(0);
                    releaseDynamicAdapter.albumFiles.add(UiHeplUtils.stringToAlbumFile(Durban.parseResult(data).get(0)));
                    isFirst = false;
                    initImg(releaseDynamicAdapter.albumFiles);
                    break;
            }
        }
    }

    private void initPop(int num) {
        UiHeplUtils.getPhoto(this, new Action<String>() {
            @Override
            public void onAction(int requestCode, @NonNull String result) {
                //拍照
                UiHeplUtils.cropPhoto(ReleaseDynamicActivity.this, result);
            }
        }, new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                releaseDynamicAdapter.albumFiles.remove(0);
                releaseDynamicAdapter.albumFiles.addAll(result);
                isFirst = false;
                initImg(releaseDynamicAdapter.albumFiles);
            }
        }, num);
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

}
