package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.utils.BigUIUtil;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.xiaomiquan.utils.TimeUtils.DEFAULT_FORMAT;

/**
 * Created by Andy on 2018/1/25.
 */

public class FocuseGroupAdapter extends CommonAdapter<GroupItem> {


    private TextView tv_title;
    private CircleImageView cv_head;
    private TextView tv_name;
    private TextView tv_time;
    private TextView tv_num;
    private TextView tv_add_percent;
    private TextView tv_today_percent;
    private TextView tv_deal;
    private TextView tv_look;

    DefaultClickLinsener defaultClickLinsener;

    public FocuseGroupAdapter(Context context, List<GroupItem> datas) {
        super(context, R.layout.adapter_group, datas);
    }

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    @Override
    protected void convert(ViewHolder holder, GroupItem s, final int position) {
        tv_title = holder.getView(R.id.tv_title);
        cv_head = holder.getView(R.id.cv_head);
        tv_name = holder.getView(R.id.tv_name);
        tv_time = holder.getView(R.id.tv_time);
        tv_num = holder.getView(R.id.tv_num);
        tv_add_percent = holder.getView(R.id.tv_add_percent);
        tv_today_percent = holder.getView(R.id.tv_today_percent);
        tv_deal = holder.getView(R.id.tv_deal);
        tv_look = holder.getView(R.id.tv_look);
        tv_look.setVisibility(View.GONE);
        tv_title.setText(s.getName());
        GlideUtils.loadImage(s.getAvatar(), cv_head);
        tv_name.setText(s.getNickName());
        tv_time.setText(com.blankj.utilcode.util.TimeUtils.millis2String(s.getCreateTime(), DEFAULT_FORMAT));
        tv_num.setText(s.getAttentionCount());

        BigUIUtil.getinstance().rateTextView(s.getTotalProfit(),tv_add_percent);
        BigUIUtil.getinstance().rateTextView(s.getCurrProfit(),tv_today_percent);

        if (s.getIsAttention() == 0) {
            tv_deal.setText(CommonUtils.getString(R.string.str_detail));
        } else if (s.getIsAttention() == 1) {
            tv_deal.setText(CommonUtils.getString(R.string.str_focuse));
        } else if (s.getIsAttention() == 2) {
            tv_deal.setVisibility(View.GONE);
        }
        tv_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(v, position, null);
                }
            }
        });
        tv_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (defaultClickLinsener != null) {
                    defaultClickLinsener.onClick(view, position, null);
                }
            }
        });
    }
}
