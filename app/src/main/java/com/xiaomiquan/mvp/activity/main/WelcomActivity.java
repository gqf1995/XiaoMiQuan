package com.xiaomiquan.mvp.activity.main;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fivefivelike.mybaselibrary.base.BaseActivity;
import com.xiaomiquan.mvp.delegate.WelcomDelegate;
import com.xiaomiquan.server.HttpUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.xiaomiquan.base.AppConst.httpBaseUrl6;

public class WelcomActivity extends BaseActivity<WelcomDelegate> {

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);//清空消息方便gc回收
        super.onDestroy();
    }

    @Override
    protected Class<WelcomDelegate> getDelegateClass() {
        return WelcomDelegate.class;
    }

    String ipPing2 = "54.250.214.59";
    String ipPing1 = "47.90.120.59";
    int time = 100000;
    int pingNum = 2;

    private Handler handler = new Handler() {//进行延时跳转
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    doAct();
                    break;
            }
        }
    };
    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        //viewDelegate.viewHolder.iv_pic.setImageResource(R.drawable.welcom);
        handler.sendEmptyMessageDelayed(1, 500);
        HttpUrl.setBaseUrl(httpBaseUrl6);
    }

    private void doPing() {
        //        ping1Values = new ArrayList<>();
        //        ping2Values = new ArrayList<>();
        //        boolean ping = ping(ipPing1, ping1Values, pingNum, new StringBuffer());
        //        if (ping) {
        //            //第一个 ip ping成功
        //            float end1 = 0;
        //            float end2 = 0;
        //            for (int i = 0; i < ping1Values.size(); i++) {
        //                end1 = end1 + ping1Values.get(i);
        //            }
        //            end1 = end1 / ping1Values.size();
        //            if (end1 > time) {
        //                //第一个 ip 网络延迟 大于1000毫秒
        //                boolean ping2 = ping(ipPing2, ping2Values, pingNum, new StringBuffer());
        //                if (ping2) {
        //                    //第二个网络 ping成功
        //                    for (int i = 0; i < ping2Values.size(); i++) {
        //                        end2 = end2 + ping2Values.get(i);
        //                    }
        //                    end2 = end2 / ping2Values.size();
        //                    if (end1 > end2) {
        //                        //用第二个网络
        //                        Log.i("ping", "ipPing2" + ipPing2);
        //                        HttpUrl.setBaseUrl(httpBaseUrl4);
        //                    } else {
        //                        Log.i("ping", "ipPing1" + ipPing1);
        //                        ToastUtil.show(httpBaseUrl);
        //                    }
        //                } else {
        //                    Log.i("ping", "ipPing1" + ipPing1);
        //                    ToastUtil.show(httpBaseUrl);
        //                }
        //            } else {
        //                Log.i("ping", "ipPing1" + ipPing1);
        //                ToastUtil.show(httpBaseUrl);
        //            }
        //        } else {
        //            //直接用第二个网络
        //            Log.i("ping", "ipPing2" + ipPing2);
        //            HttpUrl.setBaseUrl(httpBaseUrl4);
        //        }
       //PingUtil.getInstance().pingStart();
    }

    private void doAct() {
        doPing();
        gotoActivity(MainActivity.class).setIsFinish(true).startAct();
//        startActivity(new Intent(WelcomActivity.this, MainActivity.class));
//        finish();
    }

    private void append(StringBuffer stringBuffer, String text) {
        if (stringBuffer != null) {
            stringBuffer.append(text + "\n");
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

}
