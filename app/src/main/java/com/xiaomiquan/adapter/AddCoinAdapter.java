package com.xiaomiquan.adapter;

import android.content.Context;
import android.view.View;

import com.fivefivelike.mybaselibrary.view.FontTextview;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/1/10 0010.
 */

public class AddCoinAdapter extends CommonAdapter<ExchangeData> {

    private FontTextview tv_name;
    List<String> userSelectKeys;
    private List<Integer> selectPostion;
    boolean isCoin = false;
    private IconFontTextview tv_select;

    public void setCoin(boolean coin) {
        isCoin = coin;
    }

    public void select(int position) {
        if (selectPostion.contains(position)) {
            selectPostion.remove(selectPostion.indexOf(position));
        } else {
            selectPostion.add(position);
        }
        this.notifyItemChanged(position);
    }

    public List<Integer> getSelectPostion() {
        return selectPostion;
    }

    public void setUserSelectKeys(List<String> userSelectKeys) {
        this.userSelectKeys = userSelectKeys;
    }

    public AddCoinAdapter(Context context, List<ExchangeData> datas) {
        super(context, R.layout.adapter_add_coin, datas);
        selectPostion = new ArrayList<>();
    }

    @Override
    protected void convert(ViewHolder holder, ExchangeData s, final int position) {
        tv_name = holder.getView(R.id.tv_name);
        tv_select = holder.getView(R.id.tv_select);
        if (isCoin) {
            tv_name.setText(s.getExchange() + "/" + s.getUnit());
        } else {
            tv_name.setText(s.getSymbol() + "/" + s.getUnit());
        }

        if (userSelectKeys != null) {
            if (userSelectKeys.contains(s.getOnlyKey())) {
                if (!selectPostion.contains(position)) {
                    selectPostion.add(position);
                }
            }
        }

        if (selectPostion.contains(position)) {
            tv_select.setVisibility(View.VISIBLE);
        } else {
            tv_select.setVisibility(View.GONE);
        }
    }

}