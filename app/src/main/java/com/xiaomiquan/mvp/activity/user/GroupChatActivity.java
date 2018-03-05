package com.xiaomiquan.mvp.activity.user;

import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.xiaomiquan.mvp.delegate.CustomerServiceActDelegate;

/**
 * Created by 郭青枫 on 2018/3/5 0005.
 */

public class GroupChatActivity extends BaseActivity<CustomerServiceActDelegate> {

//    {
//        "code": 0,
//            "data": {
//        "chatroomId": "abc",
//                "chatroomName": "大V直播间",
//                "code": 200,
//                "total": 0,
//                "users": []
//    },
//        "dialog": {
//        "cancelAndClose": false,
//                "cancelBtn": "",
//                "cancelColor": "",
//                "code": "3300",
//                "confirmBtn": "",
//                "confirmColor": "",
//                "content": "",
//                "contentColor": "",
//                "time": "",
//                "title": "创建聊天室成功",
//                "titleColor": "",
//                "type": "2",
//                "url": ""
//    }
//    }

    @Override
    protected Class<CustomerServiceActDelegate> getDelegateClass() {
        return CustomerServiceActDelegate.class;
    }






}
