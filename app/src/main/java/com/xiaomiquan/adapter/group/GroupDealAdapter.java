package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.widget.TextView;

import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.GroupDeal;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Andy on 2018/1/25.
 */

public class GroupDealAdapter extends CommonAdapter<GroupDeal> {

    private TextView tv_type;
    private TextView tv_currency;
    private TextView tv_state;
    private TextView tv_entrust_prase;
    private TextView tv_num;
    private TextView tv_operation;

    public GroupDealAdapter(Context context, List<GroupDeal> datas) {
        super(context, R.layout.adapter_group_deal, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GroupDeal groupDeal, int position) {
        tv_type = holder.getView(R.id.tv_type);
        tv_currency = holder.getView(R.id.tv_currency);
        tv_state = holder.getView(R.id.tv_state);
        tv_entrust_prase = holder.getView(R.id.tv_entrust_prase);
        tv_num = holder.getView(R.id.tv_num);
        tv_operation = holder.getView(R.id.tv_operation);

    }
}