package com.xiaomiquan.widget;

import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.fivefivelike.mybaselibrary.view.spinnerviews.NiceSpinner;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;
import com.xiaomiquan.R;

import java.util.ArrayList;

/**
 * Created by 郭青枫 on 2018/1/18 0018.
 */

public class HeaderStickyRecyclerHeadersTouchListener extends StickyRecyclerHeadersTouchListener {

    StickyRecyclerHeadersAdapter stickyRecyclerHeadersAdapter;
    private GestureDetector mTapDetector;
    private RecyclerView mRecyclerView;
    private StickyRecyclerHeadersDecoration mDecor;
    OnHeaderClickListener onHeaderClickListener;

    public interface OnHeaderClickListener {
        void onHeaderClick(View header,MotionEvent e, int position, long headerId);
    }

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        this.onHeaderClickListener = onHeaderClickListener;
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
        if (this.onHeaderClickListener != null) {
            boolean tapDetectorResponse = this.mTapDetector.onTouchEvent(e);
            if (tapDetectorResponse) {
                // Don't return false if a single tap is detected
                return true;
            }
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                int position = mDecor.findHeaderPositionUnder((int)e.getX(), (int)e.getY());
                return position != -1;
            }
        }
        return false;
    }

    private class SingleTapDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
            if (position != -1) {
                View headerView = mDecor.getHeaderView(mRecyclerView, position);
                long headerId = getAdapter().getHeaderId(position);
                e.setEdgeFlags(1);
                headerView.dispatchTrackballEvent(e);
                float x = e.getX() - headerView.getLeft(); // 获取相对于父左上角的 x 坐标值
                float y = 0; // 获取相对于父左上角的 y 坐标值
                for (int i = 0; i < ((ViewGroup) headerView).getChildCount(); i++) {
                    RectF rect = calcViewScreenLocation(((ViewGroup) headerView).getChildAt(i));
                    boolean isInViewRect = rect.contains(x, y);
                    if (isInViewRect) {
                        onHeaderClickListener.onHeaderClick(((ViewGroup) headerView).getChildAt(i),e, position, headerId);
                        View view=((ViewGroup) headerView).getChildAt(i);
                        if (view.getId() == R.id.tv_unit) {
                            //价格
                            if (view instanceof NiceSpinner) {
                                ((NiceSpinner) view).showDropDown((int) e.getRawX() - ((int) e.getRawX() - view.getLeft()), (int) e.getY());
                            }
                        } else if (view.getId() == R.id.tv_rise) {
                            //涨幅
                            if (view instanceof GainsTabView) {
                                view.performClick();
                            }
                        }
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

    private View getTouchTarget(View view, int x, int y) {
        View targetView = null;
        // 判断view是否可以聚焦
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                targetView = child;
                break;
            }
        }
        return targetView;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    public RectF calcViewScreenLocation(View view) {
        return new RectF(view.getLeft(), view.getTop(), view.getLeft() + view.getWidth(),
                view.getTop() + view.getHeight());
    }

}
