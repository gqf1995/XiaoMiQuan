package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.TeamDetails;
import com.xiaomiquan.mvp.activity.group.TeamCombinationActivity;
import com.xiaomiquan.utils.BigUIUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class RankTeampeopleAdapter extends CommonAdapter<TeamDetails.Player> {

    private TextView tv_num;
    private TextView tv_last_week_earnings;
    private TextView tv_rate_title;
    private TextView tv_name;
    private TextView tv_focuse_num;
    private TextView tv_focuse;

    DefaultClickLinsener defaultClickLinsener;
    private CircleImageView ic_pic;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public RankTeampeopleAdapter(Context context, List<TeamDetails.Player> datas) {
        super(context, R.layout.adapter_hot_group, datas);
    }

    @Override
    protected void convert(ViewHolder holder, TeamDetails.Player s, final int position) {
        tv_num = holder.getView(R.id.tv_num);
        tv_last_week_earnings = holder.getView(R.id.tv_last_week_earnings);
        tv_rate_title = holder.getView(R.id.tv_rate_title);
        tv_name = holder.getView(R.id.tv_name);
        tv_focuse_num = holder.getView(R.id.tv_focuse_num);
        tv_focuse = holder.getView(R.id.tv_focuse);
        ic_pic = holder.getView(R.id.ic_pic);
        tv_focuse_num.setVisibility(View.GONE);
        ic_pic.setVisibility(View.VISIBLE);
        GlideUtils.loadImage(s.getAvatar(), ic_pic);
        BigUIUtil.getinstance().rateTextView(Double.parseDouble(s.getTotalIncome() + ""), tv_last_week_earnings);
        tv_name.setText(s.getNickName());


        tv_rate_title.setText(CommonUtils.getString(R.string.str_cumulative_earnings));
        tv_focuse.setText(CommonUtils.getString(R.string.str_details));
        tv_focuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamCombinationActivity.startAct((FragmentActivity) mContext, getDatas().get(position).getDemoId() + "");
            }
        });
    }

}