package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Andy on 2018/1/26.
 */

public class GroupDealCurrencyAdapyer extends CommonAdapter<String> {
    private AppCompatImageView iv_img;
    private TextView tv_type;
    private IconFontTextview icf_check;

    int selectPosition = 0;

    public GroupDealCurrencyAdapyer(Context context, List<String> datas) {
        super(context, R.layout.adapter_deal_currency, datas);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        int oldPosition = this.selectPosition;
        this.selectPosition = selectPosition;
        this.notifyItemChanged(oldPosition);
        this.notifyItemChanged(selectPosition);
    }

    @Override
    protected void convert(ViewHolder holder, String exchangeData, int position) {
        iv_img = holder.getView(R.id.iv_img);
        tv_type = holder.getView(R.id.tv_type);
        icf_check = holder.getView(R.id.icf_check);


        if (selectPosition == position) {
            icf_check.setVisibility(View.VISIBLE);
        } else {
            icf_check.setVisibility(View.GONE);
        }
    }
}
