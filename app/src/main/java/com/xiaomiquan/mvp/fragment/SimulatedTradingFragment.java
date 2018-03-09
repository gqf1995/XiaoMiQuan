package com.xiaomiquan.mvp.fragment;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.fivefivelike.mybaselibrary.base.BaseDataBindFragment;
import com.fivefivelike.mybaselibrary.base.BasePullFragment;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.fivefivelike.mybaselibrary.utils.GsonUtil;
import com.fivefivelike.mybaselibrary.utils.ListUtils;
import com.fivefivelike.mybaselibrary.utils.ToastUtil;
import com.fivefivelike.mybaselibrary.utils.glide.GlideUtils;
import com.fivefivelike.mybaselibrary.view.InnerPagerAdapter;
import com.tablayout.TabEntity;
import com.tablayout.listener.CustomTabEntity;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.xiaomiquan.entity.bean.group.TradingResult;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.ShareHistoryTradingActivity;
import com.xiaomiquan.mvp.activity.group.MyAccountActivity;
import com.xiaomiquan.mvp.databinder.group.GroupDealBinder;
import com.xiaomiquan.mvp.delegate.group.GroupDealDelegate;
import com.xiaomiquan.mvp.fragment.group.CurrencyFragment;
import com.xiaomiquan.mvp.fragment.group.GroupDetailListFragment;
import com.xiaomiquan.mvp.fragment.group.HistoryEntrustFragment;
import com.xiaomiquan.mvp.fragment.group.HistoryTradingFragment;
import com.xiaomiquan.mvp.fragment.group.NotDealFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fivefivelike.mybaselibrary.utils.glide.GlideUtils.BASE_URL;

/**
 * Created by Andy on 2018/3/6.
 */

public class SimulatedTradingFragment extends BaseDataBindFragment<GroupDealDelegate, GroupDealBinder> implements CurrencyFragment.OnSelectLinsener {
    CurrencyFragment currencyFragmentBuy;
    CoinDetail coinDetail;
    InnerPagerAdapter innerPagerAdapter;
    int index = 0;
    String id = "0";

    public interface Linsener {
        void openDrawerLayout();
    }

    Linsener linsener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        linsener = (Linsener) activity;
    }

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
    public void onDestroyView() {
        super.onDestroyView();
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
        initTool();
        initGroup();
    }

    public CircleImageView ic_pic;
    public FrameLayout fl_pic;

    private void initTool() {
        initToolbar(new ToolbarBuilder().setShowBack(false)
                .setTitle(CommonUtils.getString(R.string.str_simulation))
                .setSubTitle(CommonUtils.getString(R.string.str_management_accounts)));
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.layout_home_top, null);
        viewDelegate.getFl_content().addView(rootView);
        this.ic_pic = (CircleImageView) rootView.findViewById(R.id.ic_pic);
        this.fl_pic = (FrameLayout) rootView.findViewById(R.id.fl_pic);
        if (SingSettingDBUtil.getUserLogin() != null) {
            GlideUtils.loadImage(SingSettingDBUtil.getUserLogin().getAvatar(), ic_pic);
        } else {
            GlideUtils.loadImage(BASE_URL, ic_pic);
        }
        ic_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开抽屉
                linsener.openDrawerLayout();
            }
        });
    }

    @Override
    protected void clickRightIv() {
        super.clickRightIv();
        ShareHistoryTradingActivity.startAct(getActivity(),
                id);
    }

    private void initGroup() {
        viewDelegate.onSelectLinsener(null);
        initTablelayout();
        initCurrency();
        //upData(false);
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
                //资产明细
                MyAccountActivity.startAct(getActivity(),
                        null);
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

        addRequest(binder.deal(
                id,
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
        if (!ListUtils.isEmpty(getChildFragmentManager().getFragments())) {
            fragments = new ArrayList<>();
            fragments.add(GroupDetailListFragment.newInstance(id));
            fragments.add(NotDealFragment.newInstance(id));
            fragments.add(HistoryTradingFragment.newInstance(id));
            fragments.add(HistoryEntrustFragment.newInstance(id));
            if (innerPagerAdapter == null) {
                mTitles = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_combination));
                mTabEntities = new ArrayList<>();
                for (int i = 0; i < mTitles.size(); i++) {
                    mTabEntities.add(new TabEntity(mTitles.get(i), 0, 0));
                }
                viewDelegate.viewHolder.tl_2.setTabData(mTabEntities);
                innerPagerAdapter = new InnerPagerAdapter(getChildFragmentManager(), fragments, mTitles.toArray(new String[mTitles.size()]));
                viewDelegate.viewHolder.tl_2.setViewPager(innerPagerAdapter, viewDelegate.viewHolder.vp_sliding);
            } else {
                innerPagerAdapter.setDatas(fragments);
            }
        }
    }

//    public static void startAct(Activity activity,
//                                List<GroupItem> groupItems,
//                                int position,
//                                boolean isMy
//    ) {
//        if (SingSettingDBUtil.getUserLogin() == null) {
//            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
//            return;
//        }
//        Intent intent = new Intent(activity, SimulatedTradingFragment.class);
//        intent.putParcelableArrayListExtra("groupItems", (ArrayList<? extends Parcelable>) groupItems);
//        intent.putExtra("isMy", isMy);
//        intent.putExtra("position", position);
//        activity.startActivity(intent);
//    }

    public void initCurrency() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (getChildFragmentManager().findFragmentByTag("CurrencyFragment") == null) {
            currencyFragmentBuy = CurrencyFragment.newInstance(CurrencyFragment.TYPE_CURRENCY_BUY, "");
            transaction.add(R.id.fl_currency, currencyFragmentBuy, "CurrencyFragment");
        } else {
            currencyFragmentBuy = (CurrencyFragment) getChildFragmentManager().findFragmentByTag("CurrencyFragment");
            transaction.show(currencyFragmentBuy);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onSelectLinsener(CoinDetail coinDetail) {
        this.coinDetail = coinDetail;
        viewDelegate.onSelectLinsener(coinDetail);
    }

    //价格更新
    @Override
    public void onUpdata(String data) {
        viewDelegate.setUpdata(data);
    }

}
