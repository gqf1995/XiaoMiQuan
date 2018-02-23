package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.ManageTeam;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class ApplyForPeopleAdapter extends CommonAdapter<ManageTeam.ApprovesDataBean> {


    private CircleImageView ic_pic;
    private TextView tv_name;
    private TextView tv_content;
    private TextView tv_commit;

    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public ApplyForPeopleAdapter(Context context, List<ManageTeam.ApprovesDataBean> datas) {
        super(context, R.layout.adapter_apply_for, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ManageTeam.ApprovesDataBean s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_name = holder.getView(R.id.tv_name);
        tv_content = holder.getView(R.id.tv_content);
        tv_commit = holder.getView(R.id.tv_commit);

        GlideUtils.loadImage(s.getAvatar(), ic_pic);
        tv_name.setText(s.getNickName());
        tv_content.setText(s.getReason());

        tv_commit.setText(s.isPassFlag() ? CommonUtils.getString(R.string.str_agreed) : CommonUtils.getString(R.string.str_consent));
        tv_commit.setEnabled(!s.isPassFlag());
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, position, null);
                }
            }
        });
    }

}