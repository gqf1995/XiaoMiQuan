package com.xiaomiquan.mvp.delegate.circle;

import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.xiaomiquan.R;

public class CirclePayDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jioncircle_pay;
    }


    public static class ViewHolder {
        public View rootView;

        public TextView tv_input_label1;
        public TextView tv_join;
        public MaterialEditText et_input2;

        public ViewHolder(View rootView) {
            this.rootView = rootView;

            this.tv_input_label1 = (TextView) rootView.findViewById(R.id.tv_input_label1);
            this.tv_join = (TextView) rootView.findViewById(R.id.tv_join);
            this.et_input2 = (MaterialEditText) rootView.findViewById(R.id.et_input2);
        }

    }
}