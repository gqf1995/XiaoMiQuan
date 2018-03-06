package com.xiaomiquan.mvp.activity.user;

import android.text.TextUtils;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseDataBindActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.mvp.databinder.CustomRateBinder;
import com.xiaomiquan.mvp.delegate.CustomRateDelegate;
import com.xiaomiquan.utils.BigUIUtil;
import com.xiaomiquan.utils.UserSet;

import java.math.BigDecimal;
import java.util.Map;

public class CustomRateActivity extends BaseDataBindActivity<CustomRateDelegate, CustomRateBinder> {
    Map<String, BigDecimal> customRate;

    @Override
    protected Class<CustomRateDelegate> getDelegateClass() {
        return CustomRateDelegate.class;
    }

    @Override
    public CustomRateBinder getDataBinder(CustomRateDelegate viewDelegate) {
        return new CustomRateBinder(viewDelegate);
    }


    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        initToolbar(new ToolbarBuilder().setTitle(CommonUtils.getString(R.string.str_set_custom_rate)).setSubTitle(CommonUtils.getString(R.string.str_save)));
        viewDelegate.viewHolder.checkbox_custom_rate.setChecked(UserSet.getinstance().isUseCustomRate());
        viewDelegate.viewHolder.checkbox_custom_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选中直接保存汇率
                if (viewDelegate.viewHolder.checkbox_custom_rate.isChecked()) {
                    saveRate();
                }
                UserSet.getinstance().setIsUseCustomRate(viewDelegate.viewHolder.checkbox_custom_rate.isChecked());
            }
        });
        customRate = BigUIUtil.getinstance().getCustomRate();
        for (Map.Entry<String, BigDecimal> entry : customRate.entrySet()) {
            String k = entry.getKey();
            BigDecimal v = entry.getValue();
            String[] split = k.split(",");
            if (split[0].contains("CNY")) {
                viewDelegate.viewHolder.et_rate_cny.setText(v.toPlainString());
            } else if (split[0].contains("JPY")) {
                viewDelegate.viewHolder.et_rate_jpy.setText(v.toPlainString());
            } else if (split[0].contains("KRW")) {
                viewDelegate.viewHolder.et_rate_krw.setText(v.toPlainString());
            } else if (split[0].contains("EUR")) {
                viewDelegate.viewHolder.et_rate_eur.setText(v.toPlainString());
            }
        }
    }

    @Override
    protected void clickRightTv() {
        super.clickRightTv();
        //保存设置汇率
        saveRate();
    }

    private void saveRate() {
        if (!TextUtils.isEmpty(viewDelegate.viewHolder.et_rate_cny.getText().toString())) {
            customRate.put("CNY.USD", new BigDecimal(viewDelegate.viewHolder.et_rate_cny.getText().toString()));
        }
        if (!TextUtils.isEmpty(viewDelegate.viewHolder.et_rate_jpy.getText().toString())) {
            customRate.put("JPY.USD", new BigDecimal(viewDelegate.viewHolder.et_rate_jpy.getText().toString()));
        }
        if (!TextUtils.isEmpty(viewDelegate.viewHolder.et_rate_krw.getText().toString())) {
            customRate.put("KRW.USD", new BigDecimal(viewDelegate.viewHolder.et_rate_krw.getText().toString()));
        }
        if (!TextUtils.isEmpty(viewDelegate.viewHolder.et_rate_eur.getText().toString())) {
            customRate.put("EUR.USD", new BigDecimal(viewDelegate.viewHolder.et_rate_eur.getText().toString()));
        }
        BigUIUtil.getinstance().putCustomRate(customRate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {
        super.onServiceError(data, info, status, requestCode);
        switch (requestCode) {
        }
    }

}
