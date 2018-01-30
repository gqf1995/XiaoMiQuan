package com.xiaomiquan.adapter.circle;

import android.content.Context;
import android.widget.TextView;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.circle.UserCircle;
import com.xiaomiquan.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class CircleAllDvpAdapter extends CommonAdapter<UserCircle> {


    private CircleImageView cv_head;
    private TextView tv_name;
    private TextView tv_creator;
    private TextView tv_num;

    public CircleAllDvpAdapter(Context context, List<UserCircle> datas) {
        super(context, R.layout.adapter_circle_dvp, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserCircle userCircle, int position) {
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_creator = holder.getView(R.id.tv_creator);

        tv_creator.setText(userCircle.getNickName());
        tv_name.setText(userCircle.getName());

        GlideUtils.loadImage(userCircle.getAvatar(), cv_head);

    }
}