package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.GroupDynamic;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UserSet;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class GroupDynamicAdapter extends CommonAdapter<GroupDynamic> {


    private CircleImageView ic_pic;
    private TextView tv_nike_name;
    private TextView tv_group_name;
    private TextView tv_time;
    private TextView tv_all_rate;
    private TextView tv_type;
    private TextView tv_coin;
    private TextView tv_price;
    private TextView tv_rate_from;
    private TextView tv_rate_to;
    private TextView tv_commit;
    private IconFontTextview tv_rate_ic;

    public GroupDynamicAdapter(Context context, List<GroupDynamic> datas) {
        super(context, R.layout.adapter_group_dynamic, datas);
    }

    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    @Override
    protected void convert(ViewHolder holder, GroupDynamic s, final int position) {
        ic_pic = holder.getView(R.id.ic_pic);
        tv_nike_name = holder.getView(R.id.tv_nike_name);
        tv_group_name = holder.getView(R.id.tv_group_name);
        tv_time = holder.getView(R.id.tv_time);
        tv_all_rate = holder.getView(R.id.tv_all_rate);
        tv_type = holder.getView(R.id.tv_type);
        tv_coin = holder.getView(R.id.tv_coin);
        tv_price = holder.getView(R.id.tv_price);
        tv_rate_from = holder.getView(R.id.tv_rate_from);
        tv_rate_to = holder.getView(R.id.tv_rate_to);
        tv_commit = holder.getView(R.id.tv_commit);
        tv_rate_ic = holder.getView(R.id.tv_rate_ic);
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, position, null);
                }
            }
        });
        tv_nike_name.setText(s.getNickName());
        tv_group_name.setText(s.getName());
        tv_time.setText(com.blankj.utilcode.util.TimeUtils.millis2String(s.getDealTime()));

        BigUIUtil.getinstance().rateTextView(Double.parseDouble(s.getTotalRate()), tv_all_rate);
        tv_type.setText(s.getType() == 1 ? CommonUtils.getString(R.string.str_buy_in) : CommonUtils.getString(R.string.str_sell_out));
        tv_type.setTextColor(s.getType() == 1 ? CommonUtils.getColor(UserSet.getinstance().getRiseColor()) : CommonUtils.getColor(UserSet.getinstance().getDropColor()));
        tv_coin.setText(s.getSymbol());

        GlideUtils.loadImage(s.getAvatar(), ic_pic);
        tv_price.setText(BigUIUtil.getinstance().bigPrice(s.getPrice()));
        BigUIUtil.getinstance().rateTextView(Double.parseDouble(s.getPositionRateAfter()), tv_rate_from);
        BigUIUtil.getinstance().rateTextView(Double.parseDouble(s.getPositionRateBefore()), tv_rate_to);

        if (Double.parseDouble(s.getPositionRateAfter()) < Double.parseDouble(s.getPositionRateBefore())) {
            tv_rate_ic.setText(CommonUtils.getString(R.string.ic_Combined));
            tv_rate_ic.setTextColor(CommonUtils.getColor(UserSet.getinstance().getDropColor()));
        } else {
            tv_rate_ic.setText(CommonUtils.getString(R.string.ic_down_zuhe));
            tv_rate_ic.setTextColor(CommonUtils.getColor(UserSet.getinstance().getRiseColor()));
        }

    }

}