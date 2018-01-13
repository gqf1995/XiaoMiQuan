package com.xiaomiquan.mvp.activity.market;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.SearchCoinMarketBinder;
import com.xiaomiquan.mvp.delegate.SearchCoinMarketDelegate;
import com.xiaomiquan.mvp.fragment.SearchCoinMarketDefaultFragment;
import com.xiaomiquan.mvp.fragment.SearchCoinMarketResultFragment;

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
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_search_coin_market)));
        initFragment();
    }

    private void initFragment() {
        viewDelegate.initAddFragment(R.id.fl_root, getSupportFragmentManager());
        searchCoinMarketDefaultFragment = new SearchCoinMarketDefaultFragment();
        searchCoinMarketResultFragment = new SearchCoinMarketResultFragment();
        viewDelegate.addFragment(searchCoinMarketDefaultFragment);
        viewDelegate.addFragment(searchCoinMarketResultFragment);
        viewDelegate.showFragment(0);
        viewDelegate.viewHolder.et_search.addTextChangedListener(new TextWatcher() {
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
                    viewDelegate.viewHolder.tv_commit.setVisibility(View.GONE);
                }
            }
        });
        viewDelegate.viewHolder.et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索
                    if (!TextUtils.isEmpty(viewDelegate.viewHolder.et_search.getText().toString())) {
                        viewDelegate.showFragment(1);
                        viewDelegate.viewHolder.tv_commit.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
        viewDelegate.viewHolder.tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDelegate.viewHolder.et_search.setText("");
            }
        });
    }


    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
