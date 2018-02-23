package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UiHeplUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/11 0011.
 */

public class AllGroupMyGroupAdapter extends CommonAdapter<GroupItem> {
    private TextView tv_name;
    private TextView tv_rate;
    private TextView tv_commit;
    private LinearLayout lin_root;
    int[] ints;
    DefaultClickLinsener defaultClickLinsener;
    private LinearLayout lin_add;
    private FrameLayout fl_root;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public AllGroupMyGroupAdapter(Context context, List<GroupItem> datas) {
        super(context, R.layout.adapter_all_group_my_group, datas);
        ints = UiHeplUtils.cacularWidAndHei(mContext, R.dimen.trans_3px, 3, R.dimen.trans_10px, R.dimen.trans_10px);
    }

    public void setDatas( List<GroupItem> datas){
        getDatas().clear();
        getDatas().addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, GroupItem s, final int position) {
        tv_name = holder.getView(R.id.tv_name);
        tv_rate = holder.getView(R.id.tv_rate);
        tv_commit = holder.getView(R.id.tv_commit);
        lin_root = holder.getView(R.id.lin_root);
        lin_add = holder.getView(R.id.lin_add);
        fl_root = holder.getView(R.id.fl_root);
        UiHeplUtils.setCacularWidAndHei(ints, fl_root);

        if (position != getDatas().size() - 1) {
            tv_name.setText(s.getName());
            BigUIUtil.getinstance().rateTextView(s.getTotalProfit(), tv_rate);
            tv_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (defaultClickLinsener != null) {
                        defaultClickLinsener.onClick(v, position, null);
                    }
                }
            });
            lin_root.setVisibility(View.VISIBLE);
            lin_add.setVisibility(View.GONE);
        } else {
            lin_root.setVisibility(View.GONE);
            lin_add.setVisibility(View.VISIBLE);
            lin_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (defaultClickLinsener != null) {
                        defaultClickLinsener.onClick(v, position, null);
                    }
                }
            });
        }
    }

}