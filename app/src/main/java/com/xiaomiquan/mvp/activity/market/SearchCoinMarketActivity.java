package com.xiaomiquan.mvp.activity.market;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.SearchCoinMarketBinder;
import com.xiaomiquan.mvp.delegate.SearchCoinMarketDelegate;
import com.xiaomiquan.mvp.fragment.SearchCoinMarketDefaultFragment;
import com.xiaomiquan.mvp.fragment.SearchCoinMarketResultFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索行情 添加自选
 */
public class SearchCoinMarketActivity extends BaseDataBindActivity<SearchCoinMarketDelegate, SearchCoinMarketBinder> {

    SearchCoinMarketDefaultFragment searchCoinMarketDefaultFragment;
    SearchCoinMarketResultFragment searchCoinMarketResultFragment;

    @Override
    protected Class<SearchCoinMarketDelegate> getDelegateClass() {
        return SearchCoinMarketDelegate.class;
    }

    @Override
    public SearchCoinMarketBinder getDataBinder(SearchCoinMarketDelegate viewDelegate) {
        return new SearchCoinMarketBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_search_coin_market)).setShowBack(false).setSubTitle(CommonUtils.getString(R.string.str_cancel)));
        viewDelegate.getmToolbarTitle().setVisibility(View.GONE);
        initToolBar();
        initFragment();
    }

    public EditText et_search;
    public TextView subTitle;

    private void initToolBar() {
        View rootView = getLayoutInflater().inflate(R.layout.layout_top_search, null);
        viewDelegate.getFl_content().addView(rootView);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        layoutParams.leftMargin = (int) CommonUtils.getDimensionPixelSize(R.dimen.trans_40px);
        rootView.setLayoutParams(layoutParams);
        this.et_search = (EditText) rootView.findViewById(R.id.et_search);
        subTitle = viewDelegate.getmToolbarSubTitle();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    viewDelegate.showFragment(0);
                } else {
                    //实时搜索
                    viewDelegate.showFragment(1);
                    searchCoinMarketResultFragment.searchInput(editable.toString());
                }
            }
        });
        subTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });
    }


    private void initFragment() {
        viewDelegate.initAddFragment(R.id.fl_root, getSupportFragmentManager());
        searchCoinMarketDefaultFragment = new SearchCoinMarketDefaultFragment();
        searchCoinMarketResultFragment = new SearchCoinMarketResultFragment();
        searchCoinMarketResultFragment.setUserChooseCoin(userSelectKeys);
        viewDelegate.addFragment(searchCoinMarketDefaultFragment);
        viewDelegate.addFragment(searchCoinMarketResultFragment);
        viewDelegate.showFragment(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBack();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void onBack() {
        onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        switch (requestCode) {
            case 0x123:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        setResult(RESULT_OK);
        super.onDestroy();
    }

    public static void startAct(FragmentActivity activity,
                                ArrayList<String> userSelectKeys,
                                int code
    ) {
        Intent intent = new Intent(activity, SearchCoinMarketActivity.class);
        intent.putStringArrayListExtra("userSelectKeys", userSelectKeys);
        activity.startActivityForResult(intent, code);
    }


    List<String> userSelectKeys;

    private void getIntentData() {
        Intent intent = getIntent();
        userSelectKeys = intent.getStringArrayListExtra("userSelectKeys");
    }

}
