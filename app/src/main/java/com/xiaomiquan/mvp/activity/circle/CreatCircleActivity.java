package com.xiaomiquan.mvp.activity.circle;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.circledialog.view.listener.OnInputClickListener;
import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.mvp.databinder.circle.CreatCircleBinder;
import com.xiaomiquan.mvp.delegate.circle.CreatCircleDelegate;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.xiaomiquan.utils.UiHeplUtils;
import com.xiaomiquan.widget.CircleDialogHelper;
import com.xiaomiquan.widget.circle.CreatPopupWindow;
import com.xiaomiquan.widget.circle.SquarePopupWindow;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.durban.Durban;

import java.io.File;
import java.util.ArrayList;

public class CreatCircleActivity extends BaseDataBindActivity<CreatCircleDelegate, CreatCircleBinder> {

    @Override
    protected Class<CreatCircleDelegate> getDelegateClass() {
        return CreatCircleDelegate.class;
    }

    @Override
    public CreatCircleBinder getDataBinder(CreatCircleDelegate viewDelegate) {
        return new CreatCircleBinder(viewDelegate);
    }

    String isFree = "true";

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        if (userCircle != null) {
            initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_edit_circle)).setSubTitle(CommonUtils.getString(R.string.str_complete)));
            editCircle(userCircle);
        } else {
            initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_create_circle)).setSubTitle(CommonUtils.getString(R.string.str_complete)));
            initView();
        }

    }

    private void editCircle(UserCircle userCircle) {
        viewDelegate.viewHolder.lin_choose.setVisibility(View.GONE);
        viewDelegate.viewHolder.lin_next.setVisibility(View.VISIBLE);
        viewDelegate.viewHolder.et_name.setText(userCircle.getName());
        viewDelegate.viewHolder.et_brief.setText(userCircle.getBrief());
        GlideUtils.loadImage(userCircle.getAvatar(), viewDelegate.viewHolder.iv_img);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        if (check()) {
            if (viewDelegate.viewHolder.ck_free.isChecked()) {
                addRequest(binder.creatCircle(file,
                        viewDelegate.viewHolder.et_name.getText().toString(),
                        "科技",
                        viewDelegate.viewHolder.et_brief.getText().toString(),
                        isFree,
                        "0",
                        CreatCircleActivity.this
                ));
            } else if (viewDelegate.viewHolder.ck_charge.isChecked()) {
                CircleDialogHelper.initDefaultInputDialog(CreatCircleActivity.this, "设置入圈费用", "请输入金额", "创建", new OnInputClickListener() {
                    @Override
                    public void onClick(String text, View v) {
                        addRequest(binder.creatCircle(file,
                                viewDelegate.viewHolder.et_name.getText().toString(),
                                "科技",
                                viewDelegate.viewHolder.et_brief.getText().toString(),
                                isFree,
                                text,
                                CreatCircleActivity.this
                        ));
                    }
                }).show();
            } else {
                ToastUtil.show("选择个类型吧");
            }
        }
    }

    private void initView() {
        viewDelegate.viewHolder.icf_update_img.setOnClickListener(this);
        viewDelegate.viewHolder.lin_photo.setOnClickListener(this);
        viewDelegate.viewHolder.lin_choose.setVisibility(View.VISIBLE);
        viewDelegate.viewHolder.lin_next.setVisibility(View.GONE);
        viewDelegate.viewHolder.tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CreatPopupWindow creatPopupWindow = new CreatPopupWindow(CreatCircleActivity.this);
                creatPopupWindow.setOnItemClickListener(new CreatPopupWindow.OnItemClickListener() {
                    @Override
                    public void setOnItemClick(View v) {
                        switch (v.getId()) {
                            case R.id.tv_next:
                                if (creatPopupWindow.ck_agree.isChecked()) {
                                    creatPopupWindow.dismiss();
                                    viewDelegate.viewHolder.lin_choose.setVisibility(View.GONE);
                                    viewDelegate.viewHolder.lin_next.setVisibility(View.VISIBLE);
                                } else {
                                    ToastUtil.show(CommonUtils.getString(R.string.str_toast_user));
                                }
                                break;
                        }
                    }
                });
                creatPopupWindow.showAtLocation(viewDelegate.viewHolder.ck_free, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    File file;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case UiHeplUtils.CROP_CODE_1:
                    String path = Durban.parseResult(data).get(0);
                    file = new File(path);
                    Uri uri = Uri.fromFile(file);
                    GlideUtils.loadImage(uri, viewDelegate.viewHolder.iv_img);
                    break;
            }
        }
    }

    private void initPop() {
        UiHeplUtils.getPhoto(this, new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        //拍照
                        UiHeplUtils.cropPhoto(CreatCircleActivity.this, result);
                    }
                }, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        //相册
                        String path = result.get(0).getPath();
                        UiHeplUtils.cropPhoto(CreatCircleActivity.this, path);
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
            case R.id.lin_photo:
                initPop();
                break;
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_brief.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_circle_con));
            return false;
        }
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_name.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_circle_title));
            return false;
        }
        if (!file.exists()) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_cirlce_img));
            return false;
        }
        return true;
    }

    public static void startAct(Activity activity,
                                UserCircle userCircle
    ) {
        Intent intent = new Intent(activity, CreatCircleActivity.class);
        intent.putExtra("userCircle", userCircle);
        activity.startActivity(intent);
    }

    UserCircle userCircle;

    private void getIntentData() {
        Intent intent = getIntent();
        userCircle = intent.getParcelableExtra("userCircle");
    }

}
