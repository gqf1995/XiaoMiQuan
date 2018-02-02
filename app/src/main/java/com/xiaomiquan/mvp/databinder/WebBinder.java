package com.xiaomiquan.mvp.databinder;

import com.fivefivelike.mybaselibrary.base.BaseDataBind;
import com.xiaomiquan.utils.UserSet;
import com.xiaomiquan.mvp.delegate.WebDelegate;

import java.util.Map;

public class WebBinder extends BaseDataBind<WebDelegate> {

    public WebBinder(WebDelegate viewDelegate) {
        super(viewDelegate);
    }

    public String getMapWithUid() {
        Map<String, Object> mapWithUid = getBaseMapWithUid();
        StringBuffer sb = new StringBuffer();
        sb.append("?");
        for (Map.Entry<String, Object> entry : mapWithUid.entrySet()) {
            String key = entry.getKey().toString().trim();
            String value = entry.getValue().toString().trim();
            sb.append(key + "=" + value);
            sb.append("&");
        }
        sb.append("isNight" + "=" + UserSet.getinstance().isNight());
        return sb.toString();
    }

}