package com.xiaomiquan.mvp.delegate.circle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.circledialog.res.drawable.RadiuBg;
import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.List;

public class ReleaseForecastDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    List<String> strcurren = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_forecast_curren));
    List<String> strtype = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_forecast_type));

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        initTagFlow(viewHolder.id_currency_flowlayout, strcurren);
        initTagFlow(viewHolder.id_flowlayout, strtype);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_forecast;
    }

    private void initTagFlow(TagFlowLayout tagFlowLayout, List<String> str) {
        tagFlowLayout.setAdapter(new TagAdapter<String>(str) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getActivity().getLayoutInflater().inflate(R.layout.layout_forecast,
                        parent, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    public static class ViewHolder {
        public View rootView;

        public TextView tv_title;
        public TextView tv_all_type;
        public TagFlowLayout id_currency_flowlayout;
        public TagFlowLayout id_flowlayout;
        public EditText et_time;
        public EditText et_price;

        public ViewHolder(View rootView) {
            this.rootView = rootView;

            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_all_type = (TextView) rootView.findViewById(R.id.tv_all_type);
            this.id_currency_flowlayout = (TagFlowLayout) rootView.findViewById(R.id.id_currency_flowlayout);
            this.id_flowlayout = (TagFlowLayout) rootView.findViewById(R.id.id_flowlayout);
            this.et_time = (EditText) rootView.findViewById(R.id.et_time);
            this.et_price = (EditText) rootView.findViewById(R.id.et_price);
        }

    }
}