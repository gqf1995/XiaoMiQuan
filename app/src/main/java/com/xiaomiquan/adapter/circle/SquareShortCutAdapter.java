package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.LiveData;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class SquareShortCutAdapter extends CommonAdapter<HashMap<String,Object>> {


    private AppCompatImageView iv_img;
    private TextView tv_type;

    public SquareShortCutAdapter(Context context, List<HashMap<String,Object>> datas) {
        super(context, R.layout.adapter_square_shortcut, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HashMap<String,Object> s, final int position) {
        iv_img = holder.getView(R.id.iv_img);
        tv_type = holder.getView(R.id.tv_type);

        tv_type.setText(s.get("title")+"");
    }

}