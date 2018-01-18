package com.xiaomiquan.widget;

import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

/**
 * Created by 郭青枫 on 2018/1/18 0018.
 */

public class HeaderStickyRecyclerHeadersTouchListener extends StickyRecyclerHeadersTouchListener {

    StickyRecyclerHeadersAdapter stickyRecyclerHeadersAdapter;
    private GestureDetector mTapDetector;
    private RecyclerView mRecyclerView;
    private StickyRecyclerHeadersDecoration mDecor;
    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public void setStickyRecyclerHeadersAdapter(StickyRecyclerHeadersAdapter stickyRecyclerHeadersAdapter) {
        this.stickyRecyclerHeadersAdapter = stickyRecyclerHeadersAdapter;
    }

    public HeaderStickyRecyclerHeadersTouchListener(RecyclerView recyclerView, StickyRecyclerHeadersDecoration decor) {
        super(recyclerView, decor);
        mTapDetector = new GestureDetector(recyclerView.getContext(), new SingleTapDetector());
        mRecyclerView = recyclerView;
        mDecor = decor;
    }


    public StickyRecyclerHeadersAdapter getAdapter() {
        return stickyRecyclerHeadersAdapter;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        boolean tapDetectorResponse = this.mTapDetector.onTouchEvent(e);
        return false;
    }

    private class SingleTapDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
            if (position != -1) {
                View headerView = mDecor.getHeaderView(mRecyclerView, position);
                long headerId = getAdapter().getHeaderId(position);
                headerView.dispatchTrackballEvent(e);

                float x = e.getRawX(); // 获取相对于屏幕左上角的 x 坐标值
                float y = e.getRawY(); // 获取相对于屏幕左上角的 y 坐标值

                for (int i = 0; i < ((ViewGroup) headerView).getChildCount(); i++) {
                    RectF rect = calcViewScreenLocation(((ViewGroup) headerView).getChildAt(i));
                    boolean isInViewRect = rect.contains(x, y);
                    if (isInViewRect) {
                        defaultClickLinsener.onClick(((ViewGroup) headerView).getChildAt(i), position, null);
                    }
                }

                mDecor.invalidateHeaders();
                return false;
            }
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }

    public RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }

}
