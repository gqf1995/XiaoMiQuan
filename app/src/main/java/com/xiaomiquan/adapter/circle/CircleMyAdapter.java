package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by
 */

public class CircleMyAdapter extends CommonAdapter<UserCircle> {


    private LinearLayout lin_creat;
    private ImageView iv_head;
    private TextView tv_title;
    private TextView tv_num;
    private TextView tv_name;
    private RelativeLayout riv_info;

    public CircleMyAdapter(Context context, List<UserCircle> datas) {
        super(context, R.layout.adapter_circle_creat, datas);
    }

    public void setDatas( List<UserCircle> datas){
        getDatas().clear();
        getDatas().addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, UserCircle userCircle, int position) {
        lin_creat = holder.getView(R.id.lin_creat);
        iv_head = holder.getView(R.id.iv_head);
        tv_title = holder.getView(R.id.tv_title);
        tv_num = holder.getView(R.id.tv_num);
        tv_name = holder.getView(R.id.tv_name);
        riv_info = holder.getView(R.id.riv_info);

        if (position == 0) {
            lin_creat.setVisibility(View.VISIBLE);
            riv_info.setVisibility(View.GONE);
        } else {
            lin_creat.setVisibility(View.GONE);
            riv_info.setVisibility(View.VISIBLE);
            GlideUtils.loadImage(userCircle.getAvatar(), iv_head);
            tv_title.setText(userCircle.getName());
            tv_name.setText(userCircle.getNickName());
            tv_num.setText(userCircle.getMemberCount());
        }
    }
}