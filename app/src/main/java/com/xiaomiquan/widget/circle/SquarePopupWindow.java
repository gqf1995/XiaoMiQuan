package com.xiaomiquan.widget.circle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.activity.circle.ReleaseArticleActivity;
import com.xiaomiquan.mvp.activity.circle.ReleaseDynamicActivity;
import com.xiaomiquan.mvp.fragment.circle.SquareFragment;

/**
 * Created by Andy on 2018/1/24.
 */

public class SquarePopupWindow extends PopupWindow implements View.OnClickListener {

    private TextView btn_cancle;
    LinearLayout lin_dynamic,lin_article,lin_wechat;
    private View mPopView;
    private OnItemClickListener mListener;

    public SquarePopupWindow(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
        setPopupWindow();
        btn_cancle.setOnClickListener(this);
        lin_dynamic.setOnClickListener(this);
        lin_article.setOnClickListener(this);
        lin_wechat.setOnClickListener(this);

    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.popup_entrance_layout, null);
        btn_cancle = (TextView) mPopView.findViewById(R.id.btn_cancel);
        lin_dynamic=(LinearLayout) mPopView.findViewById(R.id.lin_dynamic);
        lin_article=(LinearLayout) mPopView.findViewById(R.id.lin_article);
        lin_wechat=(LinearLayout) mPopView.findViewById(R.id.lin_wechat);

    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
//        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.id_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }

    }

}
