package com.xiaomiquan.mvp.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.fivefivelike.mybaselibrary.entity.ToolbarBuilder;
import com.fivefivelike.mybaselibrary.utils.CommonUtils;
import com.xiaomiquan.R;
import com.xiaomiquan.adapter.ChangeSetAdapter;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.mvp.delegate.ChangeDefaultSetDelegate;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * 统一 选择 设置页面
 */
public class ChangeDefaultSetActivity extends BaseActivity<ChangeDefaultSetDelegate> {


    public static final String TYPE_LANGUAGE = "type_language";//切换语言
    public static final String TYPE_UNIT = "type_unit";//切换单位

    String title = "";

    List<String> data;
    int defaultIndex;

    ChangeSetAdapter changeSetAdapter;
    String defaultSet;


    //    平台价格-cny=cny
    //    平台价格-usd=usd
    //    cny-平台价格=cny
    //    usd-平台价格=usd
    //    usd-cny=usd
    //    cny-usd=cny
    //            仅usd=usd
    //    仅cny=cny
    //            仅平台价格=usd

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        getIntentData();
        if (TYPE_LANGUAGE.equals(type)) {
            title = CommonUtils.getString(R.string.str_change_language);
            data = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_language));
            List<String> strings = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_language_abbreviations));
            defaultIndex = strings.indexOf(UserSet.getinstance().getLanguage());
        } else if (TYPE_UNIT.equals(type)) {
            title = CommonUtils.getString(R.string.str_default_unit);
            data = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_unit));
            List<String> strings1 = Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_unit));
            if (strings1.contains(UserSet.getinstance().getUnit())) {
                defaultIndex = strings1.indexOf(UserSet.getinstance().getUnit());
            } else {
                defaultIndex = 0;
            }
        }
        initToolbar(new ToolbarBuilder().setTitle(title));
        initList();
    }

    private void initList() {
        defaultSet = data.get(defaultIndex);
        changeSetAdapter = new ChangeSetAdapter(this, data, defaultSet);
        viewDelegate.viewHolder.recycler_view.setLayoutManager(new LinearLayoutManager(this));
        viewDelegate.viewHolder.recycler_view.setAdapter(changeSetAdapter);
        changeSetAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                changeSetAdapter.setSelectPosition(position);
                if (TYPE_LANGUAGE.equals(type)) {
                    UserSet.getinstance().setLanguage(Arrays.asList(CommonUtils.getStringArray(R.array.sa_select_language_abbreviations)).get(position));
                } else if (TYPE_UNIT.equals(type)) {
                    UserSet.getinstance().setUnit(data.get(position));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    public static void startAct(Activity activity,
                                String type
    ) {
        Intent intent = new Intent(activity, ChangeDefaultSetActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    private String type;

    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
    }

    @Override
    protected Class<ChangeDefaultSetDelegate> getDelegateClass() {
        return ChangeDefaultSetDelegate.class;
    }
}
