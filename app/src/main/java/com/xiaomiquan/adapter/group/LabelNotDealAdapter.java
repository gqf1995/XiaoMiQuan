package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.HistoryTrading;
import com.xiaomiquan.utils.BigUIUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class LabelNotDealAdapter extends CommonAdapter<HistoryTrading> {


    private TextView tv_type;
    private TextView tv_currency;
    private TextView tv_state;
    private TextView tv_entrust_price;
    private TextView tv_num;
    private TextView tv_commit;

    DefaultClickLinsener defaultClickLinsener;

    public void setDefaultClickLinsener(DefaultClickLinsener defaultClickLinsener) {
        this.defaultClickLinsener = defaultClickLinsener;
    }

    public LabelNotDealAdapter(Context context, List<HistoryTrading> datas) {
        super(context, R.layout.adapter_label_not_deal, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HistoryTrading s, final int position) {
        tv_type = holder.getView(R.id.tv_type);
        tv_currency = holder.getView(R.id.tv_currency);
        tv_state = holder.getView(R.id.tv_state);
        tv_entrust_price = holder.getView(R.id.tv_entrust_price);
        tv_num = holder.getView(R.id.tv_num);
        tv_commit = holder.getView(R.id.tv_commit);

        tv_type.setText("1".equals(s.getType()) ? CommonUtils.getString(R.string.str_buy) : CommonUtils.getString(R.string.str_sell));
        tv_currency.setText(s.getSymbol());
        tv_state.setText(s.getStatusText());
        tv_entrust_price.setText(BigUIUtil.getinstance().bigPrice(s.getPrice()));
        tv_num.setText(s.getCount());

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