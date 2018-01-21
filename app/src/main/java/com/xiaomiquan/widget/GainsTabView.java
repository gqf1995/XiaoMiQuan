package com.xiaomiquan.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.tablayout.widget.MsgView;
import com.xiaomiquan.R;

import skin.support.widget.SkinCompatRelativeLayout;

/**
 * Created by 郭青枫 on 2018/1/11 0011.
 */

public class GainsTabView extends SkinCompatRelativeLayout {

    int isTop = 0;//默认模式  1涨幅  2跌幅
    private TextView tv_tab_title;
    private IconFontTextview tv_gains_top;
    private IconFontTextview tv_gains_up;
    private MsgView rtv_msg_tip;

    OnChange onChange;

    public void setOnChange(OnChange onChange) {
        this.onChange = onChange;
    }

    public interface OnChange {
        void onChange(int isTop);
    }

    public GainsTabView(Context context) {
        super(context);
        init(context);
    }

    public GainsTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GainsTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setText(String txt) {
        tv_tab_title.setText(txt);
    }

    public void setTextColor(int color) {
        tv_tab_title.setTextColor(color);
    }


    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_gains, this);
        tv_tab_title = findViewById(R.id.tv_tab_title);
        tv_gains_top = findViewById(R.id.tv_gains_top);
        tv_gains_up = findViewById(R.id.tv_gains_up);
        rtv_msg_tip = findViewById(R.id.rtv_msg_tip);
    }

    public void setDefault(View view) {
        TextView title = view.findViewById(R.id.tv_tab_title);
        tv_tab_title.setText(title.getText().toString());
        tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, title.getTextSize());
        tv_tab_title.setTextColor(title.getCurrentTextColor());
    }

    public void onClick() {
        isTop++;
        if (isTop > 2) {
            isTop = 0;
        }
        if (isTop == 0) {
            tv_gains_top.setTextColor(CommonUtils.getColor(R.color.color_font4));
            tv_gains_up.setTextColor(CommonUtils.getColor(R.color.color_font4));
        }
        if (isTop == 1) {
            tv_gains_top.setTextColor(CommonUtils.getColor(R.color.color_blue));
            tv_gains_up.setTextColor(CommonUtils.getColor(R.color.color_font4));
        }
        if (isTop == 2) {
            tv_gains_top.setTextColor(CommonUtils.getColor(R.color.color_font4));
            tv_gains_up.setTextColor(CommonUtils.getColor(R.color.color_blue));
        }
        if (onChange != null) {
            onChange.onChange(isTop);
        }
    }


}
