package com.xiaomiquan.mvp.delegate;

import android.view.View;
import android.widget.TextView;

import com.fivefivelike.mybaselibrary.base.BaseDelegate;
import com.fivefivelike.mybaselibrary.view.materialedittext.MaterialEditText;
import com.xiaomiquan.R;

public class UserTopicDelegate extends BaseDelegate {
    public ViewHolder viewHolder;
    private String topic_content = "";

    @Override
    public void initView() {
        viewHolder = new ViewHolder(getRootView());
        saveInput();;
        viewHolder.topic_commit.setOnClickListener(onClickListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_topic;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.topic_commit:

                    break;
            }
        }
    };

    private void saveInput() {
        topic_content = viewHolder.topic_input1.getText().toString();
    }


    public static class ViewHolder {
        public View rootView;
        public View v_status;

        public TextView topic_commit;
        public MaterialEditText topic_input1;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.v_status = (View) rootView.findViewById(R.id.v_status);
            this.topic_input1=rootView.findViewById(R.id.topic_input1);
            this.topic_commit = (TextView) rootView.findViewById(R.id.topic_commit);

        }

    }
}