package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.databinder.ChangeUserInfoBinder;
import com.xiaomiquan.mvp.delegate.ChangeUserInfoDelegate;
import com.xiaomiquan.utils.UiHeplUtils;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;

public class ChangeUserInfoActivity extends BaseDataBindActivity<ChangeUserInfoDelegate, ChangeUserInfoBinder> {

    UserLogin userLogin;

    @Override
    protected Class<ChangeUserInfoDelegate> getDelegateClass() {
        return ChangeUserInfoDelegate.class;
    }

    @Override
    public ChangeUserInfoBinder getDataBinder(ChangeUserInfoDelegate viewDelegate) {
        return new ChangeUserInfoBinder(viewDelegate);
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_title_user_info)).setSubTitle(CommonUtils.getString(R.string.str_save)));
        userLogin = SingSettingDBUtil.getUserLogin();
        GlideUtils.loadImage(userLogin.getAvatar(), viewDelegate.viewHolder.ic_pic);
        viewDelegate.viewHolder.tv_nick_name.setText(userLogin.getNickName() + "");
        viewDelegate.viewHolder.tv_introduction.setText(userLogin.getBrief() + "");
        viewDelegate.viewHolder.ic_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPic();
            }
        });
    }

    private void getPic() {
        UiHeplUtils.getPhoto(this, new Action<String>() {
            @Override
            public void onAction(int requestCode, @NonNull String result) {
                showPic(result);
            }
        }, new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                showPic(result.get(0).getPath());
            }
        }, 1);
    }

    File pictureFile;

    private void showPic(String path) {
        pictureFile = new File(path);
        //Bitmap bitmap = BitmapFactory.decodeFile(path);
        //viewDelegate.viewHolder.ic_pic.setImageBitmap(bitmap);
        GlideUtils.loadImage(Uri.fromFile(pictureFile), viewDelegate.viewHolder.ic_pic);
    }


    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //保存
        if (TextUtils.isEmpty(viewDelegate.viewHolder.tv_nick_name.getText().toString())) {
            CommonUtils.getString(R.string.str_toast_input_nike_name);
            return;
        }
        if (TextUtils.isEmpty(viewDelegate.viewHolder.tv_introduction.getText().toString())) {
            CommonUtils.getString(R.string.str_toast_introduction);
            return;
        }
        if (pictureFile == null &&
                viewDelegate.viewHolder.tv_nick_name.getText().toString().equals(userLogin.getNickName()) &&
                viewDelegate.viewHolder.tv_introduction.getText().toString().equals(userLogin.getBrief())
                ) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_no_change));
            return;
        }
        addRequest(binder.editUserInfo(
                 viewDelegate.viewHolder.tv_nick_name.getText().toString(),
                 viewDelegate.viewHolder.tv_introduction.getText().toString(),
                pictureFile, this));
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                UserLogin datas = GsonUtil.getInstance().toObj(data, UserLogin.class);
                SingSettingDBUtil.setNewUserLogin(datas);
                setResult(RESULT_OK);
                onBackPressed();
                break;
        }
    }

    public static void startAct(Fragment activity,
                                int requestCode) {
        Intent intent = new Intent(activity.getContext(), ChangeUserInfoActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
    public static void startAct(Activity activity,
                                int requestCode) {
        Intent intent = new Intent(activity, ChangeUserInfoActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
}
