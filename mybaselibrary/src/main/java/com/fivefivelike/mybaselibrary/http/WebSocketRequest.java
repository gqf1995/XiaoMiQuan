package com.fivefivelike.mybaselibrary.http;

import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketInfo;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

/**
 * Created by 郭青枫 on 2018/1/8 0008.
 */

public class WebSocketRequest {
    private WebSocketClient client;
    private Disposable mDisposable;
    private WebSocket mWebSocket;
    private List<WebSocketCallBack> webSocketCallBacks;
    private String mUrl;

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


    public void initRxWebsocket(String url, WebSocketCallBack webSocketCallBack) {
        //if you want to use your okhttpClient
        OkHttpClient yourClient = new OkHttpClient();
        RxWebSocketUtil.getInstance().setClient(yourClient);
        // show log,default false
        RxWebSocketUtil.getInstance().setShowLog(true);

        webSocketCallBacks = new ArrayList<>();
        webSocketCallBacks.add(webSocketCallBack);
        mUrl = url;
        mDisposable = RxWebSocketUtil.getInstance().getWebSocketInfo(url)
                //bind on life
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

    public void intiWebSocket(String url, WebSocketCallBack webSocketCallBack) {
        webSocketCallBacks = new ArrayList<>();
        webSocketCallBacks.add(webSocketCallBack);
        mUrl = url;
        client = new WebSocketClient(URI.create(url)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {

            }

            @Override
            public void onMessage(String message) {
                serviceSuccess(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                start();
            }

            @Override
            public void onError(Exception ex) {
                serviceError(ex);
                start();
            }
        };
        client.connect();
    }


    private void serviceError(Throwable ex) {
        //websocket链接失败
    }

    private void serviceSuccess(String msg) {
        //服务器获取成功
    }

    private void success(String msg) {
        //服务器数据 成功
        for (int i = 0; i < webSocketCallBacks.size(); i++) {

        }
    }

    private void error(String msg) {
        //服务器数据 失败
        for (int i = 0; i < webSocketCallBacks.size(); i++) {

        }
    }

    private void start() {
        try {
            boolean connected = client.connectBlocking();
        } catch (InterruptedException e) {

        }
    }

    public void onRemove(WebSocketCallBack webSocketCallBack) {
        webSocketCallBacks.remove(webSocketCallBack);
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
