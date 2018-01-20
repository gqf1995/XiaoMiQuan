package com.xiaomiquan.mvp.activity.market;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.SortingAdapter;
import com.xiaomiquan.mvp.databinder.SortingUserCoinBinder;
import com.xiaomiquan.mvp.delegate.SortingUserCoinDelegate;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingUserCoinActivity extends BaseDataBindActivity<SortingUserCoinDelegate, SortingUserCoinBinder> {


    SortingAdapter sortingAdapter;


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_change_coin_market)).setSubTitle(CommonUtils.getString(R.string.str_add)));
        initList();
    }

    List<String> datas;

    private void initList() {
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("");
        }
        sortingAdapter = new SortingAdapter(this, datas);
        sortingAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
            @Override
            public void onClick(View view, int position, Object item) {
                int type = (int) item;
                if (type == R.id.iv_select) {
                    String s = datas.get(position);
                    datas.remove(position);
                    datas.add(0, s);
                    sortingAdapter.setData(datas);
                } else if (type == R.id.iv_start) {
                    datas.remove(position);
                    sortingAdapter.setData(datas);
                }
            }
        });
        viewDelegate.viewHolder.recycler_view.setLayoutManager(new LinearLayoutManager(this));
        viewDelegate.viewHolder.recycler_view.setAdapter(sortingAdapter);
        viewDelegate.viewHolder.recycler_view.setLongPressDragEnabled(true);

        OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                int fromPosition = srcHolder.getAdapterPosition();
                int toPosition = targetHolder.getAdapterPosition();

                // Item被拖拽时，交换数据，并更新adapter。
                Collections.swap(datas, fromPosition, toPosition);
                sortingAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                int position = srcHolder.getAdapterPosition();
                // Item被侧滑删除时，删除数据，并更新adapter。
                datas.remove(position);
                sortingAdapter.notifyItemRemoved(position);
            }
        };
        viewDelegate.viewHolder.recycler_view.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。


    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        gotoActivity(AddCoinActivity.class).startAct();
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

    @Override
    protected Class<SortingUserCoinDelegate> getDelegateClass() {
        return SortingUserCoinDelegate.class;
    }

    @Override
    public SortingUserCoinBinder getDataBinder(SortingUserCoinDelegate viewDelegate) {
        return new SortingUserCoinBinder(viewDelegate);
    }
}
