package com.fivefivelike.mybaselibrary.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.mvp.presenter.FragmentPresenter;

/**
 * Created by 郭青枫 on 2017/7/7.
 */

public abstract class BaseFragment<T extends BaseDelegate> extends FragmentPresenter<T> implements View.OnClickListener {

    /**
     * 去掉状态栏
     */
    protected void addNoStatusBarFlag() {
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 显示状态栏
     */
    protected void clearNoStatusBarFlag() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.toolbar_img) {
                clickRightIv();
            }
            if (id == R.id.toolbar_img1) {
                clickRightIv1();
            } else if (id == R.id.toolbar_img2) {
                clickRightIv2();
            } else if (id == R.id.toolbar_subtitle) {
                clickRightTv();
            }
        }
    };


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewDelegate.isNoStatusBarFlag()) {
            addNoStatusBarFlag();
        } else {
            clearNoStatusBarFlag();
        }
    }

    /**
     * 初始化标题
     *
     * @param toolbarBuilder
     */
    protected void initToolbar(ToolbarBuilder toolbarBuilder) {
        viewDelegate.initToolBar((AppCompatActivity) getActivity(), onClickListener, toolbarBuilder);
    }

    @Override
    public void onClick(View v) {

    }

    protected void clickRightIv() {
    }

    protected void clickRightIv1() {
    }

    protected void clickRightIv2() {
    }

    protected void clickRightTv() {
    }

}
