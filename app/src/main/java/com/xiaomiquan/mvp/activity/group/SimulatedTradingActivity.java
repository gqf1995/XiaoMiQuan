package com.xiaomiquan.mvp.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.tablayout.listener.OnTabSelectListener;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.xiaomiquan.entity.bean.group.GroupBaseDeal;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.entity.bean.group.TradingResult;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.ShareHistoryTradingActivity;
import com.xiaomiquan.mvp.databinder.group.GroupDealBinder;
import com.xiaomiquan.mvp.delegate.group.GroupDealDelegate;
import com.xiaomiquan.mvp.fragment.group.CurrencyFragment;
import com.xiaomiquan.mvp.fragment.group.HistoryEntrustFragment;
import com.xiaomiquan.mvp.fragment.group.HistoryTradingFragment;
import com.xiaomiquan.mvp.fragment.group.NotDealFragment;
import com.xiaomiquan.utils.BigUIUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andy on 2018/3/6.
 */

public class SimulatedTradingActivity extends BaseDataBindActivity<GroupDealDelegate, GroupDealBinder> implements CurrencyFragment.OnSelectLinsener {
    CurrencyFragment currencyFragmentBuy;
    CoinDetail coinDetail;
    InnerPagerAdapter innerPagerAdapter;
    int index = 0;

    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    upData(false);
                    break;
            }
        }
    };


    //更新币种价格
    public void upData(boolean isUpdataNow) {
        if (!isUpdataNow) {
            index++;
            if (index == 15) {
                index = 0;
                //更新
                currencyFragmentBuy.onUpdata();
            }
            handler.sendEmptyMessageDelayed(1, 1000);
        } else {
            index = 0;
            //直接更新
            currencyFragmentBuy.onUpdata();

            handler.removeCallbacksAndMessages(null);//清空消息方便gc回收
            handler.sendEmptyMessageDelayed(1, 1000);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);//清空消息方便gc回收
    }

    @Override
    public GroupDealBinder getDataBinder(GroupDealDelegate viewDelegate) {
        return new GroupDealBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) instanceof BasePullFragment) {
                ((BasePullFragment) fragments.get(i)).onRefresh();
            }
        }
        viewDelegate.viewHolder.et_sell_num.setText(null);
        viewDelegate.viewHolder.et_sell_num.setHint("50%仓");
        switch (requestCode) {
            case 0x123:
                //买
                TradingResult tradingResultBuy = GsonUtil.getInstance().toObj(data, TradingResult.class);
//                groupItems.get(position).setBalance(tradingResultBuy.getBalance());
                //更新余额
//                viewDelegate.onSelectLinsener(viewDelegate.mCoinDetail, groupItems.get(position));
//                viewDelegate.onSelectLinsener(viewDelegate.mCoinDetail, groupItems.get(position));
                currencyFragmentBuy.setSellResult(tradingResultBuy);
                currencyFragmentBuy.getSelectPositionData(coinDetail == null ? "" : coinDetail.getSymbol());
                break;
            case 0x124:
                //卖
                TradingResult tradingResultSell = GsonUtil.getInstance().toObj(data, TradingResult.class);
                //刷新卖列表
                currencyFragmentBuy.setSellResult(tradingResultSell);
                currencyFragmentBuy.getSelectPositionData(coinDetail == null ? "" : coinDetail.getSymbol());


                break;
        }
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle("").setmRightImg1(CommonUtils.getString(R.string.ic_Share1)));
        viewDelegate.viewHolder.tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit(0);

            }
        });
        viewDelegate.viewHolder.tv_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit(1);
            }
        });
        viewDelegate.viewHolder.tv_assets_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MyAccountActivity.startAct(SimulatedTradingActivity.this,groupItems.get(position));
            }
        });
        initGroup();
    }

    @Override
    protected void clickRightIv() {
        super.clickRightIv();
        ShareHistoryTradingActivity.startAct(this, groupItems.get(position).getId());
    }

    private void initGroup() {

        initTablelayout();
        initCurrency();
        upData(false);
//        viewDelegate.viewHolder.tv_commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                commit();
//            }
//        });
        viewDelegate.viewHolder.et_coin_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (currencyFragmentBuy != null) {
                    currencyFragmentBuy.setSearchOrId(s.toString());
                }
            }
        });
    }

    /**
     * @param type 0 buy 1 sale
     */
    private void commit(int type) {
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_sell_price.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_input_price));
            return;
        }
        if (TextUtils.isEmpty(viewDelegate.viewHolder.et_sell_num.getText().toString())) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_input_num));
            return;
        }
        if (viewDelegate.mCoinDetail == null) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_no_select_coin));
            return;
        }
        if (type == 1) {
            if (CommonUtils.getString(R.string.str_now_no_data).equals(viewDelegate.salenum)) {
                ToastUtil.show(CommonUtils.getString(R.string.str_now_no_data));
                return;
            }
        }

        addRequest(binder.deal(groupItems.get(position).getId(),
                type == 0 ? "1" : "2",
                type == 0 ? viewDelegate.mCoinDetail.getCoinId() : viewDelegate.mCoinDetail.getCoinId(),
                viewDelegate.viewHolder.et_sell_price.getText().toString(),
                viewDelegate.selectType + 1 + "",
                type == 0 ? viewDelegate.buynum : viewDelegate.salenum,
                type == 0 ? 0x123 : 0x124,
                this));
    }

    @Override
    protected Class<GroupDealDelegate> getDelegateClass() {
        return GroupDealDelegate.class;
    }

    List<String> mTitles;
    private ArrayList<CustomTabEntity> mTabEntities;
    ArrayList fragments;

    private void initTablelayout() {
        fragments = new ArrayList<>();
        fragments.add(NotDealFragment.newInstance(groupItems.get(position).getId()));
        fragments.add(HistoryTradingFragment.newInstance(groupItems.get(position).getId()));
        fragments.add(HistoryEntrustFragment.newInstance(groupItems.get(position).getId()));
        if (innerPagerAdapter == null) {
            mTitles = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_deal));
            mTabEntities = new ArrayList<>();
            for (int i = 0; i < mTitles.size(); i++) {
                mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
            }
            viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);
            innerPagerAdapter = new InnerPagerAdapter(getSupportFragmentManager(), fragments, mTitles.toArray(new String[mTitles.size()]));
            viewDelegate.viewHolder.tl_2.setViewPager(innerPagerAdapter, viewDelegate.viewHolder.vp_sliding);
        } else {
            innerPagerAdapter.setDatas(fragments);
        }
    }

    public static void startAct(Activity activity,
                                List<GroupItem> groupItems,
                                int position,
                                boolean isMy
    ) {
        if (SingSettingDBUtil.getUserLogin() == null) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
            return;
        }
        Intent intent = new Intent(activity, SimulatedTradingActivity.class);
        intent.putParcelableArrayListExtra("groupItems", (ArrayList<? extends Parcelable>) groupItems);
        intent.putExtra("isMy", isMy);
        intent.putExtra("position", position);
        activity.startActivity(intent);
    }

    private List<GroupItem> groupItems;
    boolean isMy;
    int position;

    private void getIntentData() {
        Intent intent = getIntent();
        groupItems = intent.getParcelableArrayListExtra("groupItems");
        isMy = intent.getBooleanExtra("isMy", false);
        position = intent.getIntExtra("position", 0);
        viewDelegate.onSelectLinsener(null, null);
        viewDelegate.viewHolder.tv_usable.setText(BigUIUtil.getinstance().bigPrice(groupItems.get(position).getBalance()));
    }

    public void initCurrency() {
        if (viewDelegate.getFragmentList() == null) {
            viewDelegate.initAddFragment(R.id.fl_currency, getSupportFragmentManager());
            currencyFragmentBuy = CurrencyFragment.newInstance(CurrencyFragment.TYPE_CURRENCY_BUY, "");
            currencyFragmentBuy.setOnSelectLinsener(this);
            viewDelegate.addFragment(currencyFragmentBuy);
        } else {
            currencyFragmentBuy.setNewDatas(CurrencyFragment.TYPE_CURRENCY_BUY, "");
        }
        viewDelegate.showFragment(0);
        viewDelegate.viewHolder.et_coin_search.getText().clear();
    }

    @Override
    public void onSelectLinsener(CoinDetail coinDetail) {
        this.coinDetail = coinDetail;
        viewDelegate.onSelectLinsener(coinDetail, groupItems.get(position));
    }

    //价格更新
    @Override
    public void onUpdata(String data) {
        viewDelegate.setUpdata(data);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_left:
                if (groupItems.size() == 1) {
                    return;
                }
                if (position == 0) {
                    position = groupItems.size() - 1;
                } else {
                    position--;
                }
                initGroup();
                break;
            case R.id.tv_right:
                if (groupItems.size() == 1) {
                    return;
                }
                if (position == groupItems.size() - 1) {
                    position = 0;
                } else {
                    position++;
                }
                initGroup();
                break;
        }
    }
}
