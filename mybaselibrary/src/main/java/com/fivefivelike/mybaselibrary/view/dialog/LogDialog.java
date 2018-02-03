package com.fivefivelike.mybaselibrary.view.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.fivefivelike.mybaselibrary.R;
import com.fivefivelike.mybaselibrary.utils.logger.klog.KlogData;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭青枫 on 2018/2/3 0003.
 */

public class LogDialog extends DialogFragment {
    LogsAdapter adapter;
    List<String> logs;
    public RecyclerView recycler_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_log, container, false);
        this.recycler_view = (RecyclerView) v.findViewById(R.id.recycler_view);
        logs = new ArrayList<>();
        logs.add("Log:");
        adapter = new LogsAdapter(getActivity(), logs);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.setAdapter(adapter);
        return v;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onExchangeName(KlogData event) {
        if (adapter != null) {
            adapter.getDatas().add(event.getMsg());
            adapter.notifyItemInserted(adapter.getDatas().size());
        }
    }
}
