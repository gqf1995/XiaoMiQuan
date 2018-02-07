package com.xiaomiquan.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


/**
 * Created by 郭青枫 on 2018/1/26 0026.
 */

public class PingUtil {

    private static class helper {
        private static PingUtil pingUtil = new PingUtil();
    }

    public static PingUtil getInstance() {
        return PingUtil.helper.pingUtil;
    }

    private PingUtil() {

    }

    boolean isPing = true;

    public void pingStart() {
        if (isPing) {
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    doPing();
//                    isPing = false;
//                }
//            });
//            thread.start();
        }
    }



    List<Float> ping1Values;
    List<Float> ping2Values;

    public boolean ping(String host, List<Float> list, int pingCount, StringBuffer stringBuffer) {
        String line = null;
        Process process = null;
        BufferedReader successReader = null;
        //        String command = "ping -c " + pingCount + " -w 5 " + host;
        String command = "ping -c " + pingCount + " " + host;
        boolean isSuccess = false;
        try {
            process = Runtime.getRuntime().exec(command);
            if (process == null) {
                Log.e("ping", "ping fail:process is null.");
                append(stringBuffer, "ping fail:process is null.");
                return false;
            }
            successReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = successReader.readLine()) != null) {
                Log.i("ping", line);
                append(stringBuffer, line);
                if (line.contains("time=")) {
                    String[] split = line.split("time=");
                    if (split.length > 1) {
                        String s = split[1].replaceFirst(" ms", "");
                        Log.i("ping", "time-----" + s);
                        list.add(Float.parseFloat(s));
                    }
                }
            }
            int status = process.waitFor();
            if (status == 0) {
                Log.i("ping", "exec cmd success:" + command);
                append(stringBuffer, "exec cmd success:" + command);
                isSuccess = true;
            } else {
                Log.e("ping", "exec cmd fail.");
                append(stringBuffer, "exec cmd fail.");
                isSuccess = false;
            }
            Log.i("ping", "exec finished.");
            append(stringBuffer, "exec finished.");
        } catch (IOException e) {
            Log.e("ping", e.getMessage());
            isSuccess = false;
        } catch (InterruptedException e) {
            Log.e("ping", e.getMessage());
            isSuccess = false;
        } finally {
            Log.i("ping", "ping exit.");
            if (process != null) {
                process.destroy();
            }
            if (successReader != null) {
                try {
                    successReader.close();
                } catch (IOException e) {
                    Log.e("ping", e.getMessage());
                }
            }
        }
        return isSuccess;
    }

    private void append(StringBuffer stringBuffer, String text) {
        if (stringBuffer != null) {
            stringBuffer.append(text + "\n");
        }
    }

    public void setEnablaWifi(boolean isEnable) {

    }

    public void setWifi(boolean isEnable) {
        if (isEnable) {
            pingStart();
        }
    }

    public void setMobile(boolean isEnable) {
        if (isEnable) {
            pingStart();
        }
    }

    public void setConnected(boolean isEnable) {
        if (isEnable) {
            pingStart();
        }
    }

    public static class NetworkConnectChangedReceiver extends BroadcastReceiver {
        private static final String TAG = "xujun";
        public static final String TAG1 = "xxx";

        @Override
        public void onReceive(Context context, Intent intent) {
            // 这个监听wifi的打开与关闭，与wifi的连接无关
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                Log.e(TAG1, "wifiState" + wifiState);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        PingUtil.getInstance().setEnablaWifi(false);
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:

                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        PingUtil.getInstance().setEnablaWifi(true);
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        break;
                    default:
                        break;


                }
            }
            // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager
            // .WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
            // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，
            // 当然刚打开wifi肯定还没有连接到有效的无线
            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                Parcelable parcelableExtra = intent
                        .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (null != parcelableExtra) {
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                    NetworkInfo.State state = networkInfo.getState();
                    boolean isConnected = state == NetworkInfo.State.CONNECTED;// 当然，这边可以更精确的确定状态
                    Log.e(TAG1, "isConnected" + isConnected);
                    if (isConnected) {
                        PingUtil.getInstance().setWifi(true);
                    } else {
                        PingUtil.getInstance().setWifi(false);
                    }
                }
            }
            // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
            // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
            // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                ConnectivityManager manager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                Log.i(TAG1, "CONNECTIVITY_ACTION");

                NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
                if (activeNetwork != null) { // connected to the internet
                    if (activeNetwork.isConnected()) {
                        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                            // connected to wifi
                            PingUtil.getInstance().setWifi(true);
                            Log.e(TAG, "当前WiFi连接可用 ");
                        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                            // connected to the mobile provider's data plan
                            PingUtil.getInstance().setMobile(true);
                            Log.e(TAG, "当前移动网络连接可用 ");
                        }
                    } else {
                        Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                    }


                    Log.e(TAG1, "info.getTypeName()" + activeNetwork.getTypeName());
                    Log.e(TAG1, "getSubtypeName()" + activeNetwork.getSubtypeName());
                    Log.e(TAG1, "getState()" + activeNetwork.getState());
                    Log.e(TAG1, "getDetailedState()"
                            + activeNetwork.getDetailedState().name());
                    Log.e(TAG1, "getDetailedState()" + activeNetwork.getExtraInfo());
                    Log.e(TAG1, "getType()" + activeNetwork.getType());
                } else {   // not connected to the internet
                    Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                    PingUtil.getInstance().setWifi(false);
                    PingUtil.getInstance().setMobile(false);
                    PingUtil.getInstance().setConnected(false);

                }


            }
        }

    }

}
