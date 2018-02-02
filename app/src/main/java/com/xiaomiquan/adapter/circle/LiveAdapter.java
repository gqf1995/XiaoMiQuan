package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.LiveData;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class LiveAdapter extends CommonAdapter<LiveData> {



    public LiveAdapter(Context context, List<LiveData> datas) {
        super(context, R.layout.adapter_live, datas);
    }

    @Override
    protected void convert(ViewHolder holder, LiveData s, final int position) {


    }

}