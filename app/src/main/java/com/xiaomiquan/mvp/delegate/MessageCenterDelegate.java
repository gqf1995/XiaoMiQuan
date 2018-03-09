package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.xiaomiquan.R;
import com.xiaomiquan.entity.bean.MessageInfo;
import com.xiaomiquan.greenDB.MessageInfoDao;
import com.xiaomiquan.greenDaoUtils.DaoManager;
import com.xiaomiquan.utils.TimeUtils;

import java.util.List;

public class MessageCenterDelegate extends BaseDelegate {
    public ViewHolder viewHolder;

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        viewHolder.lin_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.lin_sysytem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        List<MessageInfo> list = DaoManager.getInstance().getDaoSession().getMessageInfoDao()
                .queryBuilder()
                .where(MessageInfoDao.Properties.IsLook.eq(false), MessageInfoDao.Properties.Type.eq("1"))
                .list();
        viewHolder.tv_my_num.setText(list.size() + "");
        int time = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTime() > time) {
                viewHolder.tv_my_content.setText(list.get(i).getMessage());
                viewHolder.tv_my_time.setText(TimeUtils.getDateToLeftTime(list.get(i).getTime()));
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_system_content;
        public TextView tv_system_time;
        public TextView tv_system_num;
        public LinearLayout lin_sysytem;
        public TextView tv_my_content;
        public TextView tv_my_time;
        public TextView tv_my_num;
        public LinearLayout lin_my;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_system_content = (TextView) rootView.findViewById(R.id.tv_system_content);
            this.tv_system_time = (TextView) rootView.findViewById(R.id.tv_system_time);
            this.tv_system_num = (TextView) rootView.findViewById(R.id.tv_system_num);
            this.lin_sysytem = (LinearLayout) rootView.findViewById(R.id.lin_sysytem);
            this.tv_my_content = (TextView) rootView.findViewById(R.id.tv_my_content);
            this.tv_my_time = (TextView) rootView.findViewById(R.id.tv_my_time);
            this.tv_my_num = (TextView) rootView.findViewById(R.id.tv_my_num);
            this.lin_my = (LinearLayout) rootView.findViewById(R.id.lin_my);
        }

    }
}