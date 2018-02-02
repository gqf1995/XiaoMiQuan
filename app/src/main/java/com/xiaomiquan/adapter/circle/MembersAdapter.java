package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.LiveData;
import com.xiaomiquan.entity.bean.circle.CirclePerson;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class MembersAdapter extends CommonAdapter<CirclePerson> {


    private CircleImageView cv_head;
    private TextView tv_name;

    public MembersAdapter(Context context, List<CirclePerson> datas) {
        super(context, R.layout.adapter_members, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CirclePerson s, final int position) {
        tv_name = holder.getView(R.id.tv_name);
        cv_head = holder.getView(R.id.cv_head);

        tv_name.setText(s.getNickName());
        GlideUtils.loadImage(s.getAvatar(),cv_head);

    }

}