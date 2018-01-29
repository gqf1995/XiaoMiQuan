package com.xiaomiquan.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;

/**
 * Created by 郭青枫 on 2018/1/26 0026.
 */

public class JudgeNestedScrollView extends NestedScrollView {
    private boolean isNeedScroll = true;
    View view;
    private float xDistance, yDistance, xLast, yLast;
    int toolBarPositionY = 0;
    ViewPager viewPager;


    public JudgeNestedScrollView(@NonNull Context context) {
        super(context);
        init();
    }

    public void setTabAndPager(View tab, int viewHeight, ViewPager viewPager, boolean isHaveToolbar) {
        this.view = tab;
        toolBarPositionY = viewHeight + (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_90px);
        //        if (isHaveToolbar) {
        //            toolBarPositionY = BarUtils.getStatusBarHeight() + viewHeight + (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_90px);
        //        } else {
        //            toolBarPositionY = viewHeight;
        //        }
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) viewPager.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenHeight() - viewHeight - (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_90px) - BarUtils.getStatusBarHeight();
        viewPager.setLayoutParams(layoutParams);
    }

    public JudgeNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JudgeNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                int xPosition = location[0];
                int yPosition = location[1];
                if (yPosition < toolBarPositionY) {
                    setNeedScroll(false);
                } else {
                    setNeedScroll(true);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (xDistance > yDistance) {
                    return false;
                }
                return isNeedScroll;

        }
        return super.onInterceptTouchEvent(ev);
    }

    /*
    改方法用来处理NestedScrollView是否拦截滑动事件
     */
    public void setNeedScroll(boolean isNeedScroll) {
        this.isNeedScroll = isNeedScroll;
    }

}
