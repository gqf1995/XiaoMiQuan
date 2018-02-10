package com.xiaomiquan.mvp.activity.market;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.callback.DefaultClickLinsener;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.SortingAdapter;
import com.xiaomiquan.entity.bean.ExchangeData;
import com.xiaomiquan.mvp.databinder.SortingUserCoinBinder;
import com.xiaomiquan.mvp.delegate.SortingUserCoinDelegate;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class SortingUserCoinActivity extends BaseDataBindActivity<SortingUserCoinDelegate, SortingUserCoinBinder> {

    boolean isShow = true;

    SortingAdapter sortingAdapter;
    Disposable disposable;
    List<String> onlyKey;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_change_coin_market)).setSubTitle(CommonUtils.getString(R.string.str_add)));
        strDatas = new ArrayList<>();
        initList(strDatas);
    }

    List<ExchangeData> strDatas;

    private void initList(List<ExchangeData> datas) {
        if (sortingAdapter == null) {
            sortingAdapter = new SortingAdapter(this, datas);
            sortingAdapter.setDefaultClickLinsener(new DefaultClickLinsener() {
                @Override
                public void onClick(View view, int position, Object item) {
                    int type = (int) item;
                    if (type == R.id.tv_select) {
                        ExchangeData s = strDatas.get(position);
                        sortingAdapter.getDatas().remove(position);
                        sortingAdapter.getDatas().add(0, s);
                        sortingAdapter.notifyDataSetChanged();
                        sendHttp(true);
                    } else if (type == R.id.tv_star) {
                        ExchangeData exchangeData = strDatas.get(position);
                        //取消自选
                        onlyKey.remove(onlyKey.indexOf(exchangeData.getOnlyKey()));
                        binder.singlesubs(exchangeData.getOnlyKey(), SortingUserCoinActivity.this);
                        sortingAdapter.getDatas().remove(position);
                        sortingAdapter.notifyDataSetChanged();
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
                    Collections.swap(strDatas, fromPosition, toPosition);
                    sortingAdapter.notifyItemMoved(fromPosition, toPosition);
                    if (toPosition == 0 || fromPosition == 0) {
                        if (sortingAdapter.getDatas().size() > 1) {
                            sortingAdapter.notifyItemChanged(0);
                            sortingAdapter.notifyItemChanged(1);
                        }
                    }
                    sendHttp(true);
                    return true;
                }

                @Override
                public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                    int position = srcHolder.getAdapterPosition();
                    // Item被侧滑删除时，删除数据，并更新adapter。
                    strDatas.remove(position);
                    sortingAdapter.notifyItemRemoved(position);
                }
            };
            viewDelegate.viewHolder.recycler_view.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。
        } else {
            sortingAdapter.setData(datas);
            strDatas = sortingAdapter.getDatas();
            sendHttp(false);
        }
    }

    private void sendHttp(boolean isSend) {
        if (disposable != null) {
            disposable.dispose();
        }
        if (onlyKey == null) {
            onlyKey = new ArrayList<>();
        } else {
            onlyKey.clear();
        }
        for (int i = 0; i < sortingAdapter.getDatas().size(); i++) {
            onlyKey.add(sortingAdapter.getDatas().get(i).getOnlyKey());
        }
        if (isSend) {
            disposable = binder.order(onlyKey, SortingUserCoinActivity.this);
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        AddCoinActivity.startAct(this, (ArrayList) onlyKey, 0x123);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh(isShow);
    }

    private void refresh(boolean isShow) {
        addRequest(binder.marketdata(isShow, this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x123) {
                refresh(true);
            }
        }
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:
                List<ExchangeData> datas = GsonUtil.getInstance().toList(data, ExchangeData.class);
                isShow = false;
                initList(datas);
                break;
            case 0x124:
                setResult(RESULT_OK);
                break;
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
