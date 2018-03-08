package com.xiaomiquan.adapter.group;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.view.IconFontTextview;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Andy on 2018/1/26.
 */

public class GroupDealCurrencyAdapter extends CommonAdapter<CoinDetail> {
    private AppCompatImageView iv_img;
    private TextView tv_type;
    private IconFontTextview icf_check;

    int selectPosition = 0;
    private LinearLayout lin_root;

    public void setSelectDefault(String unit){
        for(int i=0;i<getDatas().size();i++){
            if(getDatas().get(i).getSymbol().equals(unit)){
                selectPosition=i;
            }
        }
    }

    public GroupDealCurrencyAdapter(Context context, List<CoinDetail> datas) {
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
    protected void convert(ViewHolder holder, CoinDetail s, int position) {
        iv_img = holder.getView(R.id.iv_img);
        tv_type = holder.getView(R.id.tv_type);
        icf_check = holder.getView(R.id.icf_check);
        lin_root = holder.getView(R.id.lin_root);

        tv_type.setText(s.getSymbol());
        if (selectPosition == position) {
            icf_check.setVisibility(View.VISIBLE);
            tv_type.setTextColor(CommonUtils.getColor(R.color.white));
//            lin_root.setBackgroundColor(getDatas().size()>=50 ? CommonUtils.getColor(R.color.decreasing_color) : CommonUtils.getColor(R.color.increasing_color));
            lin_root.setBackgroundColor(CommonUtils.getColor(R.color.decreasing_color));
        } else {
            icf_check.setVisibility(View.INVISIBLE);
            tv_type.setTextColor(CommonUtils.getColor(R.color.color_font1));
            if (position % 2 == 0) {
                lin_root.setBackgroundColor(CommonUtils.getColor(R.color.base_mask));
            } else {
                lin_root.setBackground(null);
            }
        }
    }
}
