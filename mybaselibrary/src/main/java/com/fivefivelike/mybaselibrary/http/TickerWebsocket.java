package com.fivefivelike.mybaselibrary.http;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.ScheduledExecutorService;

public abstract class TickerWebsocket extends WebSocketClient {

    private ScheduledExecutorService scheduler;

    public TickerWebsocket(String serverUri) {
        super(URI.create(serverUri));
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.i("TickerWebsocket","open websocket...");
       // scheduler = Executors.newSingleThreadScheduledExecutor();
        //onSchedule(scheduler);
        onSubscribe();
    }

    protected abstract void onSubscribe();

    protected abstract void onSchedule(ScheduledExecutorService scheduler);

    protected abstract void onReconnect();

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.i("TickerWebsocket","websocket closed , now reconnect... >>" + reason);
        if (scheduler != null) {
            scheduler.shutdown();
        }
        onReconnect();
    }

    @Override
    public void onError(Exception ex) {
        Log.i("TickerWebsocket","websocket error: " + ex.getMessage());
    }

    public void start(){
        try {
            boolean connected = connectBlocking();
            Log.i("TickerWebsocket",this.getClass().getSimpleName() + " 连接".concat(connected ? "成功" : "失败"));
        } catch (InterruptedException e) {
            Log.i("TickerWebsocket","启动失败" + e.getMessage());
        }
    }

}
