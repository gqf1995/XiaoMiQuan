package com.xiaomiquan.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.Participant;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class FansAdapter extends CommonAdapter<Participant> {


    private CircleImageView ic_pic;
    private TextView tv_name;
    private ImageView iv_people;
    private TextView tv_statu;
    private LinearLayout lin_click;
    private TextView tv_content;
    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public FansAdapter(Context context, List<Participant> datas) {
        super(context, R.layout.adapter_people_list, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Participant s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        iv_people = holder.getView(R.id.iv_people);
        tv_statu = holder.getView(R.id.tv_statu);
        tv_content = holder.getView(R.id.tv_content);
        lin_click = holder.getView(R.id.lin_click);

        tv_name.setText(s.getNickName());
        tv_content.setText(s.getBrief());
        GlideUtils.loadImage(s.getAvatar(), ic_pic);
        lin_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, position, null);
                }
            }
        });

        if (!s.isAttentionMyfans()) {
            iv_people.setImageResource(R.drawable.followed_not);
            tv_statu.setText(CommonUtils.getString(R.string.str_focuse_other));
        } else {
            iv_people.setImageResource(R.drawable.followed_each);
            tv_statu.setText(CommonUtils.getString(R.string.str_focuse_each));
        }


    }

}