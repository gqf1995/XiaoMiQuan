package com.fivefivelike.mybaselibrary.http;

import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketInfo;
import com.fivefivelike.mybaselibrary.utils.logger.KLog;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    //private ConcurrentLinkedQueue<WebSocketCallBack> webSocketCallBacks;
    private String mUrl;
    private String REQUEST_TAG = "request";
    private ConcurrentHashMap<String, WebSocketCallBack> webSocketCallBacks;

    public interface WebSocketCallBack {
        void onDataSuccess(String data, String info, int status);

        void onDataError(String data, String info, int status);
    }

    private static class Helper {
        private static WebSocketRequest webSocketRequest = new WebSocketRequest();
    }

    public static WebSocketRequest getInstance() {
        return Helper.webSocketRequest;
    }


    public void sendData(String json) {
        if (mDisposable != null) {
            mWebSocket.send(json);
        } else {
            client.send(json);
        }
    }

    public void addCallBack(Class clss, WebSocketCallBack webSocketCallBack) {
        if (webSocketCallBacks != null) {
            webSocketCallBacks.put(clss.getName(), webSocketCallBack);
        }
    }

    public void remoceCallBack(Class clss) {
        if (webSocketCallBacks != null) {
            if (webSocketCallBacks != null) {
                webSocketCallBacks.remove(clss.getName());
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

    public void intiWebSocket(String url, Class clss, WebSocketCallBack webSocketCallBack) {
        webSocketCallBacks = new ConcurrentHashMap<>();
        webSocketCallBacks.put(clss.getName(), webSocketCallBack);
        mUrl = url;
        startSocket();

        //        client = new WebSocketClient(URI.create(url)) {
        //            @Override
        //            public void onOpen(ServerHandshake handshakedata) {
        //                KLog.i(REQUEST_TAG, "onOpen  ");
        //            }
        //
        //            @Override
        //            public void onMessage(String message) {
        //                KLog.i(REQUEST_TAG, "onMessage  ");
        //                serviceSuccess(message);
        //            }
        //
        //            @Override
        //            public void onClose(int code, String reason, boolean remote) {
        //                KLog.i(REQUEST_TAG, "onClose  ");
        //                start();
        //            }
        //
        //            @Override
        //            public void onError(Exception ex) {
        //                KLog.i(REQUEST_TAG, "onError  ");
        //                serviceError(ex);
        //                start();
        //            }
        //        };
        //        start();
        //client.connect();
    }

    private void startSocket() {
        client=new TickerWebsocket(mUrl) {
            @Override
            public void onMessage(String message) {
                KLog.i(REQUEST_TAG, "success  " + message);
            }

            @Override
            protected void onSubscribe() {

            }

            @Override
            protected void onSchedule(ScheduledExecutorService scheduler) {
                scheduler.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 5, 30, TimeUnit.SECONDS);
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
    }

    private void serviceSuccess(String msg) {
        //服务器获取成功
        KLog.i(REQUEST_TAG, "success  " + msg);
    }

    private void success(String msg) {
        //服务器数据 成功
        Iterator iter = webSocketCallBacks.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            KLog.i(REQUEST_TAG, "success 接受名称: " + key + "数据: " + msg);
            WebSocketRequest webSocketRequest = (WebSocketRequest) webSocketCallBacks.get(key);
            webSocketRequest.success(msg);
        }
    }

    private void error(String msg) {
        //服务器数据 失败
        //KLog.json(RESPONSE_TAG, msg);
        Iterator iter = webSocketCallBacks.keySet().iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            KLog.i(REQUEST_TAG, "error 接受名称: " + key + "数据: " + msg);
            WebSocketRequest webSocketRequest = (WebSocketRequest) webSocketCallBacks.get(key);
            webSocketRequest.error(msg);
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
