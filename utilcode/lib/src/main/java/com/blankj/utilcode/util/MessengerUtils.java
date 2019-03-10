package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/03/04
 *     desc  :
 * </pre>
 */
public class MessengerUtils {

    private static ConcurrentHashMap<String, MessageCallback> subscribers = new ConcurrentHashMap<>();

    private static List<Client> sClients = new ArrayList<>();
    private static Client       sLocalClient;

    private static final int    SUBSCRIBE          = 1;
    private static final int    UNSUBSCRIBE        = -1;
    private static final int    SEND_MSG_TO_TARGET = 2;
    private static final String KEY_STRING         = "MESSENGER_UTILS";

    public static void init() {
        if (isMainProcess()) {
            LogUtils.e("init " + getCurrentProcessName());
            Intent intent = new Intent(Utils.getApp(), ServerService.class);
            Utils.getApp().startService(intent);
        } else {
            LogUtils.e("init " + getCurrentProcessName());
            registerClient();
        }
    }

    public static void registerClient() {
        Client client = new Client(null);
        if (client.bind()) {
            sLocalClient = client;
        } else {
            LogUtils.e("bind service failed.");
        }
    }

    public static void unregisterClient() {
        if (sLocalClient != null) {
            sLocalClient.unbind();
        }
    }

    public static void subscribe(String key, MessageCallback callback) {
        subscribers.put(key, callback);
    }

    public static void unsubscribe(String key) {
        subscribers.remove(key);
    }

    public static void post(@NonNull String key, @NonNull Bundle data) {
        data.putString(KEY_STRING, key);
        if (sLocalClient != null) {
            sLocalClient.sendMsg2Server(data);
        } else {
            Intent intent = new Intent(Utils.getApp(), ServerService.class);
            intent.putExtras(data);
            Utils.getApp().startService(intent);
        }
//        if (otherAppClient != null) {
//            otherAppClient.sendMsg2Server(data);
//        }
    }


    public static void onMsgReceive(Bundle bundle) {

    }

    private static boolean isMainProcess() {
        return Utils.getApp().getPackageName().equals(getCurrentProcessName());
    }

    private static String getCurrentProcessName() {
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return "";
        int pid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.pid == pid) {
                if (aInfo.processName != null) {
                    return aInfo.processName;
                }
            }
        }
        return "";
    }

    static class Client {

        String             mPkgName;
        Messenger          mServer;
        LinkedList<Bundle> mCached = new LinkedList<>();
        @SuppressLint("HandlerLeak")
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                if (data != null) {
                    String key = data.getString(KEY_STRING);
                    if (key != null) {
                        MessageCallback messageCallback = subscribers.get(key);
                        if (messageCallback != null) {
                            messageCallback.onMsgCallBack(data);
                        }
                    }
                }
            }
        };
        Messenger         mClient = new Messenger(mHandler);
        ServiceConnection mConn   = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtils.d("client service connected", name);
                mServer = new Messenger(service);
                int key = getCurrentProcessName().hashCode();
                Message subscribeMsg = Message.obtain(mHandler, SUBSCRIBE, key, 0);
                subscribeMsg.replyTo = mClient;
                try {
                    mServer.send(subscribeMsg);
                } catch (RemoteException e) {
                    Log.e("MessengerUtils", "onServiceConnected: ", e);
                }
                sendCachedMsg2Server();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.w("MessengerUtils", "client service disconnected:" + name);
                mServer = null;
                if (!bind()) {
                    Log.e("MessengerUtils", "client service rebind failed: " + name);
                }
            }
        };

        Client(String pkgName) {
            this.mPkgName = pkgName;
        }

        boolean bind() {
            Intent intent;
            if (TextUtils.isEmpty(mPkgName)) {
                intent = new Intent(Utils.getApp(), ServerService.class);
            } else {
                intent = new Intent(mPkgName + ".messenger");
                intent.setPackage(mPkgName);
            }
            return Utils.getApp().bindService(intent, mConn, Context.BIND_AUTO_CREATE);
        }

        void unbind() {
            Utils.getApp().unbindService(mConn);
        }

        void sendMsg2Server(Bundle bundle) {
            if (mServer == null) {
                mCached.addFirst(bundle);
                LogUtils.d("save the bundle", bundle);
            } else {
                sendCachedMsg2Server();
                if (!send2Server(bundle)) {
                    mCached.addFirst(bundle);
                }
            }
        }

        private void sendCachedMsg2Server() {
            if (mCached.isEmpty()) return;
            for (int i = mCached.size() - 1; i >= 0; --i) {
                if (send2Server(mCached.get(i))) {
                    mCached.remove(i);
                }
            }
        }

        private boolean send2Server(Bundle bundle) {
            Message msg = Message.obtain(mHandler, SEND_MSG_TO_TARGET);
            msg.setData(bundle);
            msg.replyTo = mClient;
            try {
                mServer.send(msg);
                return true;
            } catch (RemoteException e) {
                Log.e("MessengerUtils", "send2Server: ", e);
                return false;
            }
        }
    }

    public static class ServerService extends Service {

        private final ConcurrentHashMap<Integer, Messenger> messengerMap = new ConcurrentHashMap<>();

        @SuppressLint("HandlerLeak")
        private final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                LogUtils.e(msg.toString());
                int key = msg.arg1;
                switch (msg.what) {
                    case SUBSCRIBE:
                        messengerMap.put(key, msg.replyTo);
                        break;
                    case SEND_MSG_TO_TARGET:
                        sendMsg(msg);
                        break;
                    case UNSUBSCRIBE:
                        messengerMap.remove(key);
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        };

        private final Messenger messenger = new Messenger(handler);

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            LogUtils.e();
            return messenger.getBinder();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            LogUtils.e(intent);
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    Message message = Message.obtain(handler, SEND_MSG_TO_TARGET);
                    message.replyTo = messenger;
                    message.setData(extras);
                    sendMsg(message);
                }
            }
            return START_NOT_STICKY;
        }

        private void sendMsg(Message msg) {
            try {
                Message msgToClient = Message.obtain(msg);
                for (Messenger messenger : messengerMap.values()) {
                    if (messenger != null) {
                        Message m = new Message();
                        m.copyFrom(msgToClient);
                        messenger.send(m);
                    }
                }
                Message m = new Message();
                m.copyFrom(msgToClient);
                Bundle data = msg.getData();
                if (data != null) {
                    String key = data.getString(KEY_STRING);
                    if (key != null) {
                        MessageCallback messageCallback = subscribers.get(key);
                        if (messageCallback != null) {
                            messageCallback.onMsgCallBack(data);
                        }
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public interface MessageCallback {
        void onMsgCallBack(Bundle data);
    }
}
