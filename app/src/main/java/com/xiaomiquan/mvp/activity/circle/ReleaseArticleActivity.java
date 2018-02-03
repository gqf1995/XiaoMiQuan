package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.blankj.utilcode.util.SDCardUtils;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.circle.ReleaseArticleBinder;
import com.xiaomiquan.mvp.delegate.circle.ReleaseArticleDelegate;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.xiaomiquan.widget.circle.PhotoPopupWindow;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.durban.Controller;
import com.yanzhenjie.durban.Durban;

import java.io.File;
import java.util.ArrayList;


public class ReleaseArticleActivity extends BaseDataBindActivity<ReleaseArticleDelegate, ReleaseArticleBinder> {

    public static final int PERMISSIONS_CODE_1 = 101;
    public static final int RESULT_CODE_1 = 201;
    public static final int RESULT_CODE_2 = 202;

    /**
     * 图片路径
     */
    private String mFilepath = SDCardUtils.getSDCardPaths().get(0) + "/AndroidSamples";
    /**
     * 返回结果图片路径
     */
    String resultPath;

    PhotoPopupWindow photoPopupWindow;

    @Override
    protected Class<ReleaseArticleDelegate> getDelegateClass() {
        return ReleaseArticleDelegate.class;
    }

    @Override
    public ReleaseArticleBinder getDataBinder(ReleaseArticleDelegate viewDelegate) {
        return new ReleaseArticleBinder(viewDelegate);
    }

    String title;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initView();
        initToolbar(new ToolbarBuilder().setTitle(title).setSubTitle(CommonUtils.getString(R.string.str_release)));
        photoPopupWindow = new PhotoPopupWindow(ReleaseArticleActivity.this);
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


    /**
     * 相册选图
     */
    private void selectImg() {
        Album.image(this) // 选择图片。
                .multipleChoice()
                .requestCode(RESULT_CODE_2)
                .camera(false)
                .columnCount(2) //显示行数
                .selectCount(1) //图片选择数量
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        if (requestCode == RESULT_CODE_2) {
                            crophoto(result.get(0).getPath());
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

    File file;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PERMISSIONS_CODE_1:
                    String path = Durban.parseResult(data).get(0);
                    file = new File(path);
                    Uri uri = Uri.fromFile(file);
                    GlideUtils.loadImage(uri, viewDelegate.viewHolder.iv_img);
                    break;
            }
        }
    }

    private void initPop() {
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
        photoPopupWindow.showAtLocation(viewDelegate.viewHolder.iv_img, Gravity.BOTTOM, 0, 0);
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
