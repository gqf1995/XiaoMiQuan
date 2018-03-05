package com.fivefivelike.mybaselibrary.base;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circledialog.CircleDialog;
import com.circledialog.params.ProgressParams;
import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.mvp.view.IDelegateImpl;
import com.fivefivelike.mybaselibrary.utils.AndroidUtil;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.fivefivelike.mybaselibrary.view.dialog.NetWorkDialog;
import com.githang.statusbar.StatusBarCompat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭青枫 on 2017/7/7.
 */

public abstract class BaseDelegate extends IDelegateImpl {
    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private Toolbar mToolbar;
    private IconFontTextview mToolbarRightImg1;
    private IconFontTextview mToolbarRightImg2;
    private IconFontTextview mToolbarRightImg3;

    private View viewImg2Point;
    private View viewImg1Point;
    private View viewImgPoint;
    private View viewBackPoint;
    private DialogFragment netConnectDialog;
    private LinearLayout layoutTitleBar;
    private FrameLayout fl_content;


    private ImageButton mNavButtonView;
    private int cuurentFragmentPosition = -1;
    private int fragmentContainId = -1;
    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;
    private boolean isNoStatusBarFlag = true;
    private CircleDialog.CircleDialogLinsener circleDialogLinsener;
    private LinearLayout mToolbarBackLin;
    private TextView mToolbarBackTxt;
    private IconFontTextview mToolbarBack;
    private View mViewSubtitlePoint;

    private String FRAGMENT_TAG = "fragment_tag";

    public NetWorkDialog getNetConnectDialog() {
        return initDialog("加载中...");
    }

    public NetWorkDialog getNetConnectDialog(String title) {
        return initDialog(title);
    }

    private NetWorkDialog initDialog(String title) {
        netConnectDialog = new CircleDialog.Builder(this.getActivity())
                .setProgressText(title)
                .setProgressStyle(ProgressParams.STYLE_SPINNER)
                .setCancelable(true)
                .setCanceledOnTouchOutside(false)
                .setCircleDialogLinsener(circleDialogLinsener)
                .create();
        if (fragmentManager == null) {
            fragmentManager = getActivity().getSupportFragmentManager();
        }
        return new NetWorkDialog(netConnectDialog, fragmentManager);
    }


    public boolean isNoStatusBarFlag() {
        return isNoStatusBarFlag;
    }

    public void setNoStatusBarFlag(boolean noStatusBarFlag) {
        isNoStatusBarFlag = noStatusBarFlag;
    }

    public void setCircleDialogLinsener(CircleDialog.CircleDialogLinsener circleDialogLinsener) {
        this.circleDialogLinsener = circleDialogLinsener;
    }

    public void setShowMsg(boolean isShow) {
        if (mViewSubtitlePoint != null) {
            mViewSubtitlePoint.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置返回按钮 文字 或者 iconfont
     *
     * @param txt
     */
    public void setBackIconFontText(String txt) {
        if (mToolbarBack != null) {
            //重置属性
            mToolbarBack.setRotation(0);
            mToolbarBack.setScaleX(1);
            mToolbarBack.setScaleY(1);
            mToolbarBack.setText(txt);
        }
    }

    /**
     * 初始化标题栏
     *
     * @param builder 设置标题栏参数的对象
     */
    protected void initToolBar(AppCompatActivity activity, View.OnClickListener listener, ToolbarBuilder builder) {
        mToolbar = getViewById(R.id.toolbar);
        mToolbarTitle = getViewById(R.id.toolbar_title);
        mToolbarSubTitle = getViewById(R.id.toolbar_subtitle);
        layoutTitleBar = getViewById(R.id.layout_title_bar);
        mToolbarRightImg1 = getViewById(R.id.toolbar_img);
        mToolbarRightImg2 = getViewById(R.id.toolbar_img1);
        mToolbarRightImg3 = getViewById(R.id.toolbar_img2);
        viewImg2Point = getViewById(R.id.view_img2_point);
        viewImg1Point = getViewById(R.id.view_img1_point);
        viewImgPoint = getViewById(R.id.view_img_point);
        viewBackPoint = getViewById(R.id.view_back_point);
        fl_content = getViewById(R.id.fl_content);


        mToolbarBackTxt = getViewById(R.id.toolbar_back_txt);
        mToolbarBackLin = getViewById(R.id.toolbar_lin_back);
        mToolbarBack = getViewById(R.id.toolbar_back);
        mViewSubtitlePoint = getViewById(R.id.view_subtitle_point);


        //标题总背景
        //        if (layoutTitleBar != null) {
        //            if (builder.getLayoutBarBack() != 0) {
        //                layoutTitleBar.setBackgroundResource(builder.getLayoutBarBack());
        //            } else {
        //                layoutTitleBar.setBackgroundResource(R.color.transparent);
        //            }
        //        }


        //状态栏
        initStatus(activity, builder.getStatusBack());
        if (mToolbar != null) {
            //将Toolbar显示到界面
            activity.setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(activity.getTitle());
            //设置默认的标题不显示
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        //设置标题
        if (!TextUtils.isEmpty(builder.getTitle())) {
            mToolbarTitle.setText(builder.getTitle());
        }
        //设置右边的文字并显示
        if (!TextUtils.isEmpty(builder.getSubTitle())) {
            mToolbarSubTitle.setVisibility(View.VISIBLE);
            mToolbarSubTitle.setText(builder.getSubTitle());
            mToolbarSubTitle.setOnClickListener(listener);
        }
        //设置右边第一个按钮并显示
        if (!TextUtils.isEmpty(builder.getmRightImg1())) {
            mToolbarRightImg1.setVisibility(View.VISIBLE);
            mToolbarRightImg1.setText(builder.getmRightImg1());
            mToolbarRightImg1.setOnClickListener(listener);
        }
        //设置右边第二个按钮并显示
        if (!TextUtils.isEmpty(builder.getmRightImg2())) {
            mToolbarRightImg2.setVisibility(View.VISIBLE);
            mToolbarRightImg2.setText(builder.getmRightImg2());
            mToolbarRightImg2.setOnClickListener(listener);
        }   //设置右边第二个按钮并显示
        if (!TextUtils.isEmpty(builder.getmRightImg3())) {
            mToolbarRightImg3.setVisibility(View.VISIBLE);
            mToolbarRightImg3.setText(builder.getmRightImg3());
            mToolbarRightImg3.setOnClickListener(listener);
        }
        //设置显示返回按钮  可自定义图标
        if (builder.isShowBack()) {
            showBack(activity, builder.getBackTxt());

        }
        //        //设置标题栏的背景颜色
        if (builder.getmToolbarBackColor() != 0) {
            mToolbar.setBackgroundColor(builder.getmToolbarBackColor());
        } else {
            mToolbar.setBackgroundColor(CommonUtils.getColor(R.color.toolbar_bg));
        }
        //设置标题是否显示
        if (!builder.isTitleShow()) {
            mToolbarTitle.setVisibility(View.GONE);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 头部高度
     *
     * @param activity
     */
    public void initStatus(Activity activity, @DrawableRes int statusBack) {
        View v_status = getViewById(R.id.v_status);
        if (v_status != null && isNoStatusBarFlag) {
            if (statusBack != 0) {
                v_status.setBackgroundResource(statusBack);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                v_status.getLayoutParams().height = AndroidUtil.getStatusHeight(activity);
            } else {
                v_status.getLayoutParams().height = 0;
            }
            v_status.requestLayout();
        }
    }

    public View getStatus() {
        View v_status = getViewById(R.id.v_status);
        return v_status;
    }


    public void setStatusBg(int colorId, boolean isLight) {
        View v_status = getViewById(R.id.v_status);
        v_status.setBackgroundColor(CommonUtils.getColor(colorId));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompat.setLightStatusBar(getActivity().getWindow(), isLight);
            if (isLight) {
                if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                    StatusBarCompat.setLightStatusBar(getActivity().getWindow(), false);
                    v_status.setBackgroundColor(CommonUtils.getColor(R.color.font_grey));
                }
            }
        }
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack(final AppCompatActivity activity, String backTxt) {
        //        mToolbar.setNavigationIcon(navigationBar);
        //        initNavigation();
        //        mNavButtonView.setMinimumWidth(0);
        //        mNavButtonView.setPadding(activity.getResources().getDimensionPixelSize(R.dimen.trans_20px), 0,
        //                activity.getResources().getDimensionPixelSize(R.dimen.trans_40px), 0);
        //        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                if (navigationBar == R.drawable.lefticon) {
        //                    activity.onBackPressed();
        //                }
        //            }
        //        });
        mToolbarBackLin.setVisibility(View.VISIBLE);
        mToolbarBackLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        mToolbarBack.setText(CommonUtils.getString(R.string.ic_Back));
        mToolbarBackTxt.setText(backTxt);
    }


    /**
     * 设置左图标padding
     */
    public void initNavigation() {
        try {
            Field file = mToolbar.getClass().getDeclaredField("mNavButtonView");
            file.setAccessible(true);
            mNavButtonView = (ImageButton) file.get(mToolbar);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果要添加fragment 先调用此方法
     *
     * @param fragmentContainId FrameLayout id
     * @param fragmentManager   fragment管理
     */
    public void initAddFragment(int fragmentContainId, FragmentManager fragmentManager) {
        this.fragmentContainId = fragmentContainId;
        this.fragmentManager = fragmentManager;
        if (this.fragmentManager.getFragments() != null) {
            if (this.fragmentManager.getFragments().size() != 0) {
                int backStackCount = this.fragmentManager.getBackStackEntryCount();
                for (int i = 0; i < backStackCount; i++) {
                    this.fragmentManager.popBackStack();
                }
                if (fragmentList != null) {
                    fragmentList.clear();
                }
            }
        }
    }

    /**
     * 添加fragment  在{@link #initAddFragment(int, FragmentManager)}之后使用
     *
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(fragment);
    }

    public void initFromSave() {
        if (fragmentContainId == -1) {//没有设置容器
            return;
        }
        if (fragmentManager == null) {//没有初始化管理器
            return;
        }
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        if (!ListUtils.isEmpty(fragmentList)) {
            if (fragmentManager.getFragments() != null) {
                if (fragmentManager.getFragments().size() > 0) {
                    for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
                        if (fragmentManager.getFragments().get(i).getTag().contains(FRAGMENT_TAG)) {
                            fragmentList.add(fragmentManager.getFragments().get(i));
                        }
                    }
                }
            }
        }
    }

    /**
     * 显示某一个fragment
     * 在{@link #addFragment(Fragment)}之后使用
     *
     * @param index 指定索引
     */
    public void showFragment(int index) {
        if (fragmentContainId == -1) {//没有设置容器
            return;
        }
        if (fragmentManager == null) {//没有初始化管理器
            return;
        }
        if (fragmentList == null || fragmentList.size() == 0) {//如果没有添加
            return;
        }
        if (cuurentFragmentPosition != -1 && cuurentFragmentPosition == index) {//当前选择与显示一致
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment frl = fragmentList.get(index);
        if (frl.isAdded()) {
            frl.onResume();
        }
        if (!ListUtils.isEmpty(fragmentManager.getFragments())) {
            for (int i = 0; i < fragmentList.size(); i++) {
                transaction.add(fragmentContainId, fragmentList.get(i), FRAGMENT_TAG + index);
            }
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            if (fragmentList.get(i).getTag().contains(FRAGMENT_TAG)) {
                Fragment fragment = fragmentList.get(i);
                FragmentTransaction ft = fragmentManager.beginTransaction();
                if (index == i) {
                    ft.show(fragment);
                } else {
                    ft.hide(fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }
        transaction.commitAllowingStateLoss();
        cuurentFragmentPosition = index;
    }

    public String getFramentTag(int index) {
        return FRAGMENT_TAG + index;
    }

    public Fragment getFragmentByTag(String tag) {
        if (fragmentManager == null) {
            return null;
        }
        return fragmentManager.findFragmentByTag(tag);
    }

    public Fragment getFragmentByIndex(int index) {
        if (!ListUtils.isEmpty(fragmentList)) {
            return null;
        }
        if (index > fragmentList.size()) {
            return null;
        }
        return fragmentList.get(index);
    }

    public void replaceFragment(int index, Fragment fragment) {
        if (fragmentContainId == -1) {//没有设置容器
            return;
        }
        if (fragmentManager == null) {//没有初始化管理器
            return;
        }
        if (fragmentList == null || fragmentList.size() == 0) {//如果没有添加
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragmentList.get(index));
        transaction.add(fragmentContainId, fragment);
        transaction.commitAllowingStateLoss();
        fragmentList.remove(index);
        fragmentList.add(index, fragment);
        showFragment(index);
    }

    /**
     * 得到当前的fragment
     *
     * @return
     */
    public Fragment getCurrentFrgment() {
        return fragmentList.get(cuurentFragmentPosition);
    }

    public TextView getmToolbarTitle() {
        return mToolbarTitle;
    }

    public TextView getmToolbarSubTitle() {
        return mToolbarSubTitle;
    }

    public Toolbar getmToolbar() {
        return mToolbar;
    }

    public IconFontTextview getmToolbarRightImg1() {
        return mToolbarRightImg1;
    }

    public IconFontTextview getmToolbarRightImg2() {
        return mToolbarRightImg2;
    }

    public IconFontTextview getmToolbarRightImg3() {
        return mToolbarRightImg3;
    }

    public LinearLayout getLayoutTitleBar() {
        return layoutTitleBar;
    }

    public ImageButton getmNavButtonView() {
        return mNavButtonView;
    }

    public int getCuurentFragmentPosition() {
        return cuurentFragmentPosition;
    }

    public int getFragmentContainId() {
        return fragmentContainId;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public LinearLayout getmToolbarBackLin() {
        return mToolbarBackLin;
    }

    public TextView getmToolbarBackTxt() {
        return mToolbarBackTxt;
    }

    public IconFontTextview getmToolbarBack() {
        return mToolbarBack;
    }

    public View getViewImg2Point() {
        return viewImg2Point;
    }

    public View getViewImg1Point() {
        return viewImg1Point;
    }

    public View getViewImgPoint() {
        return viewImgPoint;
    }

    public FrameLayout getFl_content() {
        return fl_content;
    }

    public View getViewBackPoint() {
        return viewBackPoint;
    }
}
