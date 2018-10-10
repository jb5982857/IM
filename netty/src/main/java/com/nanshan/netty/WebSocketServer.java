package com.nanshan.netty;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.im2.common.protobuf.MessageTemplate;
import com.nanshan.support.utils.LogUtils;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by jiangbo on 2018/9/27.
 * 这是跑在另一个进程中，专门来用于长链接交换数据
 * 任何对用长链接的操作都需要在这个类中进行
 */

public class WebSocketServer extends Service {
    //ping的时间，单位ms
    private static final int PING_TIME = 5000;

    //handler事件的what
    public static final int WHAT_DEFAULT = -1;

    public static final int WHAT_PING = 0;
    public static final int WHAT_STOP_PING = 4;
    public static final int WHAT_OPEN = 1;
    public static final int WHAT_CLOSE = 2;
    public static final int WHAT_SEND = 3;

    private Handler mHandler;
    private WebSocketClient mClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("webSocket");
        thread.start();
        mHandler = new WebSocketHandler(thread.getLooper());
        mClient = new WebSocketClient(this, Constants.H.WEB_SOCKET_URL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int type = intent.getIntExtra(Constants.Key.TYPE, WHAT_DEFAULT);
        Serializable message = intent.getSerializableExtra(Constants.Key.MESSAGE);
        LogUtils.i("onStartCommand type " + type + " message " + message);
        if (type == WHAT_DEFAULT) {
            LogUtils.w("start service but do not send type");
            return START_STICKY;
        }

        mHandler.obtainMessage(type, message).sendToTarget();
        return START_STICKY;
    }

    //用于处理外部发消息的handler
    private class WebSocketHandler extends Handler {
        WebSocketHandler(Looper looper) {
            super(looper, new Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case WHAT_PING:
                            mClient.ping();
                            mHandler.sendEmptyMessageDelayed(WHAT_PING, PING_TIME);
                            break;
                        case WHAT_OPEN:
                            try {
                                mClient.open();
                            } catch (Exception e) {
                                e.printStackTrace();
                                LogUtils.e("connect interrupt " + e.getMessage());
                            }
                            break;
                        case WHAT_CLOSE:
                            try {
                                mClient.close();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                LogUtils.e("close interrupt " + e.getMessage());
                            }
                            break;
                        case WHAT_SEND:
                            try {
                                mClient.eval((MessageTemplate.Client) msg.obj);
                            } catch (IOException e) {
                                e.printStackTrace();
                                LogUtils.e("send interrupt " + e.getMessage());
                            }
                            break;
                        case WHAT_STOP_PING:
                            mHandler.removeMessages(WHAT_PING);
                            break;
                        default:
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.obtainMessage(WHAT_CLOSE).sendToTarget();
        mHandler.removeMessages(WHAT_PING);
        mHandler.removeMessages(WHAT_OPEN);
        mHandler.removeMessages(WHAT_SEND);
    }
}
