package com.xiaomiquan.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomiquan.R;

import java.util.List;

/**
 * Created by Andy on 2018/1/18.
 */

public class ItemDragAdapter extends BaseItemDraggableAdapter<String,BaseViewHolder> {


    public ItemDragAdapter(List<String> data) {
        super(R.layout.adapter_volume,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name,item);

    }
}
