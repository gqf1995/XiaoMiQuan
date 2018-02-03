package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.blankj.utilcode.util.SDCardUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.circle.ReleaseDynamicAdapter;
import com.xiaomiquan.mvp.databinder.circle.ReleaseDynamicBinder;
import com.xiaomiquan.mvp.delegate.circle.ReleaseDynamicDelegate;
import com.xiaomiquan.widget.circle.PhotoPopupWindow;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.durban.Controller;
import com.yanzhenjie.durban.Durban;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReleaseDynamicActivity extends BaseDataBindActivity<ReleaseDynamicDelegate, ReleaseDynamicBinder> {

    public static final int PERMISSIONS_CODE_1 = 101;
    public static final int RESULT_CODE_1 = 201;
    public static final int RESULT_CODE_2 = 202;

    /**
     * 图片路径
     */
    private String mFilepath = SDCardUtils.getSDCardPaths().get(0) + "/AndroidSamples";

    PhotoPopupWindow photoPopupWindow;
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
        if (platform.equals("1")) {
            initRealeseSquare(releaseDynamicAdapter.fileList, String.valueOf(viewDelegate.viewHolder.ck_circle.isChecked()));
        } else {
            initRealeseCircle(releaseDynamicAdapter.fileList, String.valueOf(viewDelegate.viewHolder.ck_live.isChecked()));
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
                    if (releaseDynamicAdapter.getItemCount() <= 10) {
                        initPop();
                    } else {
                        ToastUtil.show(CommonUtils.getString(R.string.str_rv_img));
                    }
                } else {
                    /// TODO: 2018/2/2  删除图片操作
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

    /**
     * 相册选图
     */

    private void selectImg() {
        Album.gallery(this);
        Album.image(this) // 选择图片。
                .multipleChoice()
                .requestCode(RESULT_CODE_2)
                .camera(false)
                .columnCount(2) //显示行数
                .selectCount(9) //图片选择数量
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        if (requestCode == RESULT_CODE_2) {
                            releaseDynamicAdapter.albumFiles.remove(0);
                            releaseDynamicAdapter.albumFiles.addAll(result);
                            isFirst = false;
                            initImg(releaseDynamicAdapter.albumFiles);
                        }
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {

                    }
                })
                .start();
    }


    /**
     * 拍照
     */
    private void camera() {
        Album.camera(this) // 相机功能。
                .image() // 拍照。
                .filePath(mFilepath) // 文件保存路径，非必须。
                .requestCode(RESULT_CODE_1)
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        if (requestCode == RESULT_CODE_1) {
                            crophoto(result);
                        }
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                    }
                })
                .start();
    }

    private void crophoto(String path) {
        Durban.with(this)
                // 裁剪界面的标题。
                .title("Crop")
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                // 图片路径list或者数组。
                .inputImagePaths(path)
                // 图片输出文件夹路径。
                .outputDirectory(mFilepath)
                // 裁剪图片输出的最大宽高。
                .maxWidthHeight(500, 500)
                // 裁剪时的宽高比。
                .aspectRatio(1, 1)
                // 图片压缩格式：JPEG、PNG。
                .compressFormat(Durban.COMPRESS_JPEG)
                // 图片压缩质量，请参考：Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
                .compressQuality(90)
                // 裁剪时的手势支持：ROTATE, SCALE, ALL, NONE.
                .gesture(Durban.GESTURE_ALL)
                .controller(
                        Controller.newBuilder()
                                .enable(false) // 是否开启控制面板。
                                .rotation(true) // 是否有旋转按钮。
                                .rotationTitle(true) // 旋转控制按钮上面的标题。
                                .scale(true) // 是否有缩放按钮。
                                .scaleTitle(true) // 缩放控制按钮上面的标题。
                                .build()) // 创建控制面板配置。
                .requestCode(PERMISSIONS_CODE_1)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PERMISSIONS_CODE_1:
                    String cameraPath = Durban.parseResult(data).get(0);
//                    fileLsit.add(new File(cameraPath));
//                    initImg(fileLsit);
                    break;
            }
        }
    }

    private void initPop() {
        photoPopupWindow = new PhotoPopupWindow(ReleaseDynamicActivity.this);
        photoPopupWindow.setOnItemClickListener(new PhotoPopupWindow.OnItemClickListener() {
            @Override
            public void setOnItemClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_camera:
                        camera();
                        photoPopupWindow.dismiss();
                        break;
                    case R.id.btn_photo:
                        selectImg();
                        photoPopupWindow.dismiss();
                        break;
                    case R.id.btn_cancel:
                        photoPopupWindow.dismiss();
                        break;
                }
            }
        });
        photoPopupWindow.showAtLocation(viewDelegate.viewHolder.et_con, Gravity.BOTTOM, 0, 0);
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
