package com.xiaomiquan.adapter.msg;

import android.content.Context;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class MsgAboutMeAdapter extends CommonAdapter<String> {


    private CircleImageView ic_pic;
    private TextView tv_title;
    private TextView tv_time;
    private TextView tv_content;

    public MsgAboutMeAdapter(Context context, List<String> datas) {
        super(context, R.layout.adapter_msg_about_me, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_title = holder.getView(R.id.tv_title);
        tv_time = holder.getView(R.id.tv_time);
        tv_content = holder.getView(R.id.tv_content);
    }

}