package com.xiaomiquan.mvp.fragment;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
import com.xiaomiquan.entity.bean.MyAsset;
import com.xiaomiquan.entity.bean.UserLogin;
import com.xiaomiquan.entity.bean.group.CoinDetail;
import com.xiaomiquan.entity.bean.group.GroupItem;
import com.xiaomiquan.entity.bean.group.TradingResult;
import com.xiaomiquan.greenDaoUtils.SingSettingDBUtil;
import com.xiaomiquan.mvp.activity.group.MyAccountActivity;
import com.xiaomiquan.mvp.activity.group.MyPropertyDetailActivity;
import com.xiaomiquan.mvp.databinder.group.GroupDealBinder;
import com.xiaomiquan.mvp.delegate.group.GroupDealDelegate;
import com.xiaomiquan.mvp.fragment.group.CurrencyFragment;
import com.xiaomiquan.mvp.fragment.group.GroupDetailListFragment;
import com.xiaomiquan.mvp.fragment.group.HistoryEntrustFragment;
import com.xiaomiquan.mvp.fragment.group.HistoryTradingFragment;
import com.xiaomiquan.mvp.fragment.group.NotDealFragment;
import com.xiaomiquan.utils.BigUIUtil;

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
    //String id = "0";
    GroupItem groupItem;
    UserLogin userLogin;
    List<String> mTitles;
    private ArrayList<CustomTabEntity> mTabEntities;
    ArrayList fragments;
    String myAssetData;

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

    //用户长时间停留 定时刷新币种价格  点击选择币种列表item直接刷新
    public void upData(boolean isUpdataNow) {
        if (!isUpdataNow) {
            index++;
            if (index == 15) {
                index = 0;
                //更新
                currencyFragmentBuy.onUpdata();
                onBinderRefresh();
            }
            handler.sendEmptyMessageDelayed(1, 1000);
        } else {
            index = 0;
            //直接更新
            currencyFragmentBuy.onUpdata();
            onBinderRefresh();
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
        switch (requestCode) {
            case 0x123:
                //买
                TradingResult tradingResultBuy = GsonUtil.getInstance().toObj(data, TradingResult.class);
                currencyFragmentBuy.setSellResult(tradingResultBuy);
                currencyFragmentBuy.getSelectPositionData(coinDetail == null ? "" : coinDetail.getSymbol());
                upData(true);
                updataFragment();
                break;
            case 0x124:
                //卖
                TradingResult tradingResultSell = GsonUtil.getInstance().toObj(data, TradingResult.class);
                //刷新卖列表
                currencyFragmentBuy.setSellResult(tradingResultSell);
                currencyFragmentBuy.getSelectPositionData(coinDetail == null ? "" : coinDetail.getSymbol());
                upData(true);
                updataFragment();
                break;
            case 0x125:
                //组合详情
                groupItem = GsonUtil.getInstance().toObj(data, GroupItem.class);
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
                if (groupItem != null) {
                    viewDelegate.initTopData(groupItem);
                    for (int i = 0; i < fragments.size(); i++) {
                        if (fragments.get(i) instanceof FragmentLinsener) {
                            ((FragmentLinsener) fragments.get(i)).setId(groupItem.getId());
                        }
                    }
                    if (currencyFragmentBuy != null) {
                        if (TextUtils.isEmpty(currencyFragmentBuy.getDemoId())) {
                            currencyFragmentBuy.setDemoId(groupItem.getId());
                        }
                    }
                    addRequest(binder.myAsset(groupItem.getId() + "", this));
                }
                break;
            case 0x126:
                //我的资产
                myAssetData = data;
                MyAsset myAsset = GsonUtil.getInstance().toObj(data, MyAsset.class);
                viewDelegate.viewHolder.tv_total_assets.setText(BigUIUtil.getinstance().bigPrice(myAsset.getTotalAmount() + ""));
                viewDelegate.viewHolder.tv_usable.setText(BigUIUtil.getinstance().bigPrice(myAsset.getBalance() + ""));
                break;
        }
    }

    private void updataFragment() {
        if (!ListUtils.isEmpty(getChildFragmentManager().getFragments())) {
            if (!ListUtils.isEmpty(fragments)) {
                for (int i = 0; i < fragments.size(); i++) {
                    if (fragments.get(i) instanceof BasePullFragment) {
                        ((BasePullFragment) fragments.get(i)).onRefresh();
                    }
                }
            }
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
        viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(true);
        viewDelegate.viewHolder.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onBinderRefresh();
            }
        });
        viewDelegate.viewHolder.lin_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SingSettingDBUtil.isLogin(getActivity())) {
                    if (groupItem != null) {
                        //我的资产
                        MyPropertyDetailActivity.startAct(getActivity(), groupItem);
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //每次进入刷新
        onBinderRefresh();
    }

    private void onBinderRefresh() {
        userLogin = SingSettingDBUtil.getUserLogin();
        if (userLogin != null) {
            if (binder != null) {
                addRequest(binder.getDemoByUserId(userLogin.getId() + "", this));
            }
        } else {
            if (viewDelegate != null) {
                viewDelegate.viewHolder.swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //管理账户
        if (SingSettingDBUtil.isLogin(getActivity())) {
            if (groupItem != null) {
                MyAccountActivity.startAct(getActivity(), groupItem, myAssetData);
            }
        }
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
    }

    /**
     * @param type 0 buy 1 sale
     */
    private void commit(int type) {
        if (SingSettingDBUtil.getUserLogin() == null) {
            ToastUtil.show(CommonUtils.getString(R.string.str_toast_need_login));
            return;
        }
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
        if (viewDelegate.isSelectStorehouse) {
            viewDelegate.sellChoose(viewDelegate.selectId);
        }
        addRequest(binder.deal(
                groupItem.getId(),
                type == 0 ? "1" : "2",
                type == 0 ? viewDelegate.mCoinDetail.getCoinId() : viewDelegate.mCoinDetail.getCoinId(),
                viewDelegate.viewHolder.et_sell_price.getText().toString(),
                viewDelegate.selectType + 1 + "",
                viewDelegate.isSelectStorehouse ? (type == 0 ? viewDelegate.buynum : viewDelegate.salenum) : viewDelegate.viewHolder.et_sell_num.getText().toString(),
                viewDelegate.mCoinDetail.getType(),
                type == 0 ? 0x123 : 0x124,
                this));
    }

    @Override
    protected Class<GroupDealDelegate> getDelegateClass() {
        return GroupDealDelegate.class;
    }


    private void initTablelayout() {
        fragments = new ArrayList<>();
        fragments.add(GroupDetailListFragment.newInstance("0"));
        fragments.add(NotDealFragment.newInstance("0"));
        fragments.add(HistoryTradingFragment.newInstance("0"));
        fragments.add(HistoryEntrustFragment.newInstance("0"));
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

    public void initCurrency() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        ViewGroup.LayoutParams layoutParams = viewDelegate.viewHolder.fl_currency.getLayoutParams();
        layoutParams.height = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_463px) + (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_60px);
        viewDelegate.viewHolder.fl_currency.setLayoutParams(layoutParams);
        if (getChildFragmentManager().findFragmentByTag("CurrencyFragment") == null) {
            currencyFragmentBuy = CurrencyFragment.newInstance(userLogin == null ? "" : userLogin.getDemoId(), CurrencyFragment.TYPE_CURRENCY_BUY, "");
            transaction.add(R.id.fl_currency, currencyFragmentBuy, "CurrencyFragment");
        } else {
            currencyFragmentBuy = (CurrencyFragment) getChildFragmentManager().findFragmentByTag("CurrencyFragment");
            transaction.show(currencyFragmentBuy);
        }
        currencyFragmentBuy.setOnSelectLinsener(this);
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

    public interface FragmentLinsener {
        public void setId(String id);
    }

}
