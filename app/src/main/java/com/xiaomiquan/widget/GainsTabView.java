package com.xiaomiquan.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.tablayout.widget.MsgView;
import com.xiaomiquan.R;

/**
 * Created by 郭青枫 on 2018/1/11 0011.
 */

public class GainsTabView extends RelativeLayout {

    boolean isTop = false;
    private TextView tv_tab_title;
    private IconFontTextview tv_gains_top;
    private IconFontTextview tv_gains_up;
    private MsgView rtv_msg_tip;

    OnChange onChange;

    public void setOnChange(OnChange onChange) {
        this.onChange = onChange;
    }

    public interface OnChange {
        void onChange(boolean isTop);
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
        isTop = !isTop;
        tv_gains_top.setTextColor(isTop ? CommonUtils.getColor(R.color.color_font1) : CommonUtils.getColor(R.color.color_font3));
        tv_gains_up.setTextColor(!isTop ? CommonUtils.getColor(R.color.color_font1) : CommonUtils.getColor(R.color.color_font3));
        if (onChange != null) {
            onChange.onChange(isTop);
        }
    }


}
