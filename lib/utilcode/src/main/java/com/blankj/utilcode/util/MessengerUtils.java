package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/10
 *     desc  : utils about messenger
 * </pre>
 */
public class MessengerUtils {

    private static ConcurrentHashMap<String, MessageCallback> subscribers = new ConcurrentHashMap<>();

    private static Map<String, Client> sClientMap = new HashMap<>();
    private static Client              sLocalClient;

    private static final int    WHAT_SUBSCRIBE   = 0x00;
    private static final int    WHAT_UNSUBSCRIBE = 0x01;
    private static final int    WHAT_SEND        = 0x02;
    private static final String KEY_STRING       = "MESSENGER_UTILS";

    public static void register() {
        if (isMainProcess()) {
            if (isServiceRunning(ServerService.class.getName())) {
                Log.i("MessengerUtils", "Server service is running.");
                return;
            }
            Intent intent = new Intent(Utils.getApp(), ServerService.class);
            Utils.getApp().startService(intent);
            return;
        }
        if (sLocalClient == null) {
            Client client = new Client(null);
            if (client.bind()) {
                sLocalClient = client;
            } else {
                Log.e("MessengerUtils", "Bind service failed.");
            }
        } else {
            Log.i("MessengerUtils", "The client have been bind.");
        }
    }

    public static void unregister() {
        if (isMainProcess()) {
            if (!isServiceRunning(ServerService.class.getName())) {
                Log.i("MessengerUtils", "Server service isn't running.");
                return;
            }
            Intent intent = new Intent(Utils.getApp(), ServerService.class);
            Utils.getApp().stopService(intent);
        }
        if (sLocalClient != null) {
            sLocalClient.unbind();
        }
    }

    public static void register(final String pkgName) {
        if (sClientMap.containsKey(pkgName)) {
            Log.i("MessengerUtils", "register: client registered: " + pkgName);
            return;
        }
        Client client = new Client(pkgName);
        if (client.bind()) {
            sClientMap.put(pkgName, client);
        } else {
            Log.e("MessengerUtils", "register: client bind failed: " + pkgName);
        }
    }

    public static void unregister(final String pkgName) {
        if (sClientMap.containsKey(pkgName)) {
            Client client = sClientMap.get(pkgName);
            client.unbind();
        } else {
            Log.i("MessengerUtils", "unregister: client didn't register: " + pkgName);
        }
    }

    public static void subscribe(@NonNull final String key, @NonNull final MessageCallback callback) {
        subscribers.put(key, callback);
    }

    public static void unsubscribe(@NonNull final String key) {
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
        for (Client client : sClientMap.values()) {
            client.sendMsg2Server(data);
        }
    }

    private static boolean isMainProcess() {
        return Utils.getApp().getPackageName().equals(Utils.getCurrentProcessName());
    }

    private static boolean isAppInstalled(@NonNull final String pkgName) {
        PackageManager packageManager = Utils.getApp().getPackageManager();
        try {
            return packageManager.getApplicationInfo(pkgName, 0) != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isServiceRunning(final String className) {
        ActivityManager am =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        //noinspection ConstantConditions
        List<ActivityManager.RunningServiceInfo> info = am.getRunningServices(0x7FFFFFFF);
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }

    private static boolean isAppRunning(@NonNull final String pkgName) {
        int uid;
        PackageManager packageManager = Utils.getApp().getPackageManager();
        try {
            ApplicationInfo ai = packageManager.getApplicationInfo(pkgName, 0);
            if (ai == null) return false;
            uid = ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(Integer.MAX_VALUE);
            if (taskInfo != null && taskInfo.size() > 0) {
                for (ActivityManager.RunningTaskInfo aInfo : taskInfo) {
                    if (pkgName.equals(aInfo.baseActivity.getPackageName())) {
                        return true;
                    }
                }
            }
            List<ActivityManager.RunningServiceInfo> serviceInfo = am.getRunningServices(Integer.MAX_VALUE);
            if (serviceInfo != null && serviceInfo.size() > 0) {
                for (ActivityManager.RunningServiceInfo aInfo : serviceInfo) {
                    if (uid == aInfo.uid) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static class Client {

        String             mPkgName;
        Messenger          mServer;
        LinkedList<Bundle> mCached = new LinkedList<>();
        @SuppressLint("HandlerLeak")
        Handler mReceiveServeMsgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                if (data != null) {
                    String key = data.getString(KEY_STRING);
                    if (key != null) {
                        MessageCallback callback = subscribers.get(key);
                        if (callback != null) {
                            callback.messageCall(data);
                        }
                    }
                }
            }
        };
        Messenger         mClient = new Messenger(mReceiveServeMsgHandler);
        ServiceConnection mConn   = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("MessengerUtils", "client service connected " + name);
                mServer = new Messenger(service);
                int key = Utils.getCurrentProcessName().hashCode();
                Message msg = Message.obtain(mReceiveServeMsgHandler, WHAT_SUBSCRIBE, key, 0);
                msg.replyTo = mClient;
                try {
                    mServer.send(msg);
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
            if (TextUtils.isEmpty(mPkgName)) {
                Intent intent = new Intent(Utils.getApp(), ServerService.class);
                return Utils.getApp().bindService(intent, mConn, Context.BIND_AUTO_CREATE);
            }
            if (isAppInstalled(mPkgName)) {
                if (isAppRunning(mPkgName)) {
                    Intent intent = new Intent(mPkgName + ".messenger");
                    intent.setPackage(mPkgName);
                    return Utils.getApp().bindService(intent, mConn, Context.BIND_AUTO_CREATE);
                } else {
                    Log.e("MessengerUtils", "bind: the app is not running -> " + mPkgName);
                    return false;
                }
            } else {
                Log.e("MessengerUtils", "bind: the app is not installed -> " + mPkgName);
                return false;
            }
        }

        void unbind() {
            Message msg = Message.obtain(mReceiveServeMsgHandler, WHAT_UNSUBSCRIBE);
            msg.replyTo = mClient;
            try {
                mServer.send(msg);
            } catch (RemoteException e) {
                Log.e("MessengerUtils", "unbind: ", e);
            }
            try {
                Utils.getApp().unbindService(mConn);
            } catch (Exception ignore) {/*ignore*/}
        }

        void sendMsg2Server(Bundle bundle) {
            if (mServer == null) {
                mCached.addFirst(bundle);
                Log.i("MessengerUtils", "save the bundle " + bundle);
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
            Message msg = Message.obtain(mReceiveServeMsgHandler, WHAT_SEND);
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

        private final ConcurrentHashMap<Integer, Messenger> mClientMap = new ConcurrentHashMap<>();

        @SuppressLint("HandlerLeak")
        private final Handler mReceiveClientMsgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case WHAT_SUBSCRIBE:
                        mClientMap.put(msg.arg1, msg.replyTo);
                        break;
                    case WHAT_SEND:
                        sendMsg2Client(msg);
                        consumeServerProcessCallback(msg);
                        break;
                    case WHAT_UNSUBSCRIBE:
                        mClientMap.remove(msg.arg1);
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        };

        private final Messenger messenger = new Messenger(mReceiveClientMsgHandler);

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return messenger.getBinder();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    Message msg = Message.obtain(mReceiveClientMsgHandler, WHAT_SEND);
                    msg.replyTo = messenger;
                    msg.setData(extras);
                    sendMsg2Client(msg);
                    consumeServerProcessCallback(msg);
                }
            }
            return START_NOT_STICKY;
        }

        private void sendMsg2Client(final Message msg) {
            for (Messenger client : mClientMap.values()) {
                try {
                    if (client != null) {
                        client.send(msg);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        private void consumeServerProcessCallback(final Message msg) {
            Bundle data = msg.getData();
            if (data != null) {
                String key = data.getString(KEY_STRING);
                if (key != null) {
                    MessageCallback callback = subscribers.get(key);
                    if (callback != null) {
                        callback.messageCall(data);
                    }
                }
            }
        }
    }

    public interface MessageCallback {
        void messageCall(Bundle data);
    }
}
