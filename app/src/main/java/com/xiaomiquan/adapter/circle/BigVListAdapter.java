package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.SquareLive;
import com.xiaomiquan.entity.bean.circle.UserFriende;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Andy on 2018/1/26.
 */

public class BigVListAdapter extends CommonAdapter<UserFriende> {


    DefaultClickLinsener defaultClickLinsener;
    private CircleImageView cv_head;
    private TextView tv_name;
    private TextView tv_attention;
    private TextView tv_num;
    public List<UserFriende> userFriendes;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public BigVListAdapter(Context context, List<UserFriende> datas) {
        super(context, R.layout.adapter_bigv_list, datas);
        this.userFriendes = datas;
    }
    public void setDatas(List<UserFriende> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, UserFriende s2, final int position) {
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_num = holder.getView(R.id.tv_num);
        tv_attention = holder.getView(R.id.tv_attention);

        GlideUtils.loadImage(userFriendes.get(position).getAvatar(), cv_head);
        tv_name.setText(userFriendes.get(position).getNickName());
        tv_num.setText(userFriendes.get(position).getAttentionedCount() + "");

        if (userFriendes.get(position).getAttention()) {
            tv_attention.setText("取消关注");
        } else {
            tv_attention.setText("关注");
        }

        tv_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });


    }

}