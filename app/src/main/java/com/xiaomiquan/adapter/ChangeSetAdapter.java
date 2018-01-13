package com.xiaomiquan.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class ChangeSetAdapter extends CommonAdapter<String> {


    private TextView tv_name;
    private IconFontTextview tv_select;
    private LinearLayout lin_select;


    private int selectPosition = 0;

    public void setSelectPosition(int position) {
        if (selectPosition != position) {
            int oldPosition = selectPosition;
            selectPosition = position;
            this.notifyItemChanged(position);
            this.notifyItemChanged(oldPosition);
        }
    }

    public ChangeSetAdapter(Context context, List<String> datas, String defaultSet) {
        super(context, R.layout.adapter_change_set, datas);
        selectPosition = datas.indexOf(defaultSet);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        tv_name = holder.getView(R.id.tv_name);
        tv_select = holder.getView(R.id.tv_select);
        lin_select = holder.getView(R.id.lin_select);

        tv_name.setText(s);
        if (selectPosition == position) {
            tv_select.setVisibility(View.VISIBLE);
        } else {
            tv_select.setVisibility(View.GONE);
        }

    }


}