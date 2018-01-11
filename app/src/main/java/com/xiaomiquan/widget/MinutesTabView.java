package com.xiaomiquan.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.tablayout.widget.MsgView;
import com.xiaomiquan.R;

/**
 * Created by 郭青枫 on 2018/1/11 0011.
 */

public class MinutesTabView extends RelativeLayout {

    private TextView tv_tab_title;
    private IconFontTextview tv_gains_top;
    private IconFontTextview tv_gains_up;
    private MsgView rtv_msg_tip;


    public MinutesTabView(Context context) {
        super(context);
        init(context);
    }

    public MinutesTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MinutesTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_minutes, this);
        tv_tab_title = findViewById(R.id.tv_tab_title);
        tv_gains_up = findViewById(R.id.tv_gains_up);
        rtv_msg_tip = findViewById(R.id.rtv_msg_tip);
    }

    public void setText(String txt) {
        tv_tab_title.setText(txt);
    }

    public void setDefault(View view) {
        TextView title = view.findViewById(R.id.tv_tab_title);
        tv_tab_title.setText(title.getText().toString());
        tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, title.getTextSize());
        tv_tab_title.setTextColor(title.getCurrentTextColor());
        tv_gains_up.setTextColor(title.getCurrentTextColor());
    }

}
