package com.fivefivelike.mybaselibrary.http;

import com.blankj.utilcode.util.DeviceUtils;
import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketInfo;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

/**
 * Created by 郭青枫 on 2018/1/8 0008.
 */

public class WebSocketRequest {
    private TickerWebsocket client;
    private Disposable mDisposable;
    private WebSocket mWebSocket;
    private String mUrl;
    private String REQUEST_TAG = "request";
    private ConcurrentHashMap<String, WebSocketCallBack> webSocketCallBacks;

    private String oldSend = "";
    private String uid;
    boolean isOpen = false;

    String registerUrl;
    String unregisterUrl;
    Disposable disposable;

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    public void setUnregisterUrl(String unregisterUrl) {
        this.unregisterUrl = unregisterUrl;
    }

    public interface WebSocketCallBack {
        void onDataSuccess(String data, String info, int status);

        void onDataError(String data, String info, int status);
    }

    private WebSocketRequest() {
        uid = DeviceUtils.getAndroidID();
    }

    private static class Helper {
        private static WebSocketRequest webSocketRequest = new WebSocketRequest();
    }

    public static WebSocketRequest getInstance() {
        return Helper.webSocketRequest;
    }

    public void sendData(List<String> keys) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            stringBuffer.append(keys.get(i)).append(",");
        }
        unregister(oldSend);
        oldSend = stringBuffer.toString();
    }

    private void register(String json) {
        LinkedHashMap baseMap = new LinkedHashMap<>();
        baseMap.put("uid", uid);
        baseMap.put("keys", json);
        disposable = new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(registerUrl)
                .setShowDialog(false)
                .setRequestName("注册web")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(new RequestCallback() {
                    @Override
                    public void success(int requestCode, String data) {

                    }

                    @Override
                    public void error(int requestCode, Throwable exThrowable) {
                    }
                })
                .build()
                .RxSendRequest();
    }


    public void unregister(String json) {
        LinkedHashMap baseMap = new LinkedHashMap<>();
        baseMap.put("uid", uid);
        baseMap.put("keys", "");
        disposable = new HttpRequest.Builder()
                .setRequestCode(0x123)
                .setRequestUrl(unregisterUrl)
                .setShowDialog(false)
                .setRequestName("取消注册web")
                .setRequestMode(HttpRequest.RequestMode.POST)
                .setParameterMode(HttpRequest.ParameterMode.Json)
                .setRequestObj(baseMap)
                .setRequestCallback(new RequestCallback() {
                    @Override
                    public void success(int requestCode, String data) {
                        //取消订阅后 重新订阅新的
                        register(oldSend);
                    }

                    @Override
                    public void error(int requestCode, Throwable exThrowable) {

                    }
                })
                .build()
                .RxSendRequest();
    }

    public void addCallBack(String clss, WebSocketCallBack webSocketCallBack) {
        if (webSocketCallBacks != null) {
            webSocketCallBacks.put(clss, webSocketCallBack);
        }
    }

    public void remoceCallBack(String clss) {
        if (webSocketCallBacks != null) {
            if (webSocketCallBacks != null) {
                webSocketCallBacks.remove(clss);
            }
        }
    }

    public void initRxWebsocket(String url, Class clss, WebSocketCallBack webSocketCallBack) {
        //if you want to use your okhttpClient
        OkHttpClient yourClient = new OkHttpClient();
        RxWebSocketUtil.getInstance().setClient(yourClient);
        // show log,default false
        RxWebSocketUtil.getInstance().setShowLog(true);

        webSocketCallBacks = new ConcurrentHashMap<>();
        webSocketCallBacks.put(clss.getName(), webSocketCallBack);
        mUrl = url;
        mDisposable = RxWebSocketUtil.getInstance().getWebSocketInfo(url)
                //bind on life
                .subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                .subscribe(new Consumer<WebSocketInfo>() {
                    @Override
                    public void accept(WebSocketInfo webSocketInfo) throws Exception {
                        mWebSocket = webSocketInfo.getWebSocket();
                        if (webSocketInfo.isOnOpen()) {
                        } else {
                            String string = webSocketInfo.getString();
                            if (string != null) {
                                serviceSuccess(string);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {//onError()
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //调用失败
                        throwable.printStackTrace();
                        serviceError(throwable);
                    }
                });
    }

    public void intiWebSocket(String url, String name, WebSocketCallBack webSocketCallBack) {
        webSocketCallBacks = new ConcurrentHashMap<>();
        webSocketCallBacks.put(name, webSocketCallBack);
        mUrl = url;
        startSocket();
    }

    private void startSocket() {
        isOpen = false;
        client = new TickerWebsocket(mUrl) {
            @Override
            public void onMessage(String message) {
                KLog.i(REQUEST_TAG, "success  " + message);
                isOpen = true;
                serviceSuccess(message);
            }

            @Override
            protected void onSubscribe() {

            }

            @Override
            protected void onSchedule(ScheduledExecutorService scheduler) {

            }

            @Override
            protected void onReconnect() {
                startSocket();
            }
        };
        client.start();
    }

    private void serviceError(Throwable ex) {
        //websocket链接失败
        KLog.i(REQUEST_TAG, "error  " + ex.getMessage());
        error(ex.getMessage());
    }

    private void serviceSuccess(String msg) {
        //服务器获取成功
        KLog.i(REQUEST_TAG, "success  " + msg);
        success(msg);
    }

    private void success(String msg) {
        //服务器数据 成功
        Iterator iter = webSocketCallBacks.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            KLog.i(REQUEST_TAG, "success 接受名称: " + key + "数据: " + msg);
            WebSocketRequest.WebSocketCallBack webSocketRequest = (WebSocketRequest.WebSocketCallBack) webSocketCallBacks.get(key);
            webSocketRequest.onDataSuccess(msg, msg, 0);
        }
    }

    private void error(String msg) {
        //服务器数据 失败
        //KLog.json(RESPONSE_TAG, msg);
        Iterator iter = webSocketCallBacks.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            KLog.i(REQUEST_TAG, "error 接受名称: " + key + "数据: " + msg);
            WebSocketRequest.WebSocketCallBack webSocketRequest = (WebSocketRequest.WebSocketCallBack) webSocketCallBacks.get(key);
            webSocketRequest.onDataError(msg, msg, 0);
        }
    }


    public void onDestory() {
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        } else {
            try {
                if (null != client) {
                    client.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                client = null;
            }
        }
    }

}
