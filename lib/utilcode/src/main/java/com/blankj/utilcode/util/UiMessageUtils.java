package com.blankj.utilcode.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/10/20
 *     desc  : utils about ui message can replace LocalBroadcastManager
 * </pre>
 */
public final class UiMessageUtils implements Handler.Callback {

    private static final String  TAG   = "UiMessageUtils";
    private static final boolean DEBUG = isAppDebug();

    private final Handler   mHandler = new Handler(Looper.getMainLooper(), this);
    private final UiMessage mMessage = new UiMessage(null);

    private final SparseArray<List<UiMessageCallback>> mListenersSpecific  = new SparseArray<>();
    private final List<UiMessageCallback>              mListenersUniversal = new ArrayList<>();
    private final List<UiMessageCallback>              mDefensiveCopyList  = new ArrayList<>();

    public static UiMessageUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    private UiMessageUtils() {
    }

    /**
     * Sends an empty Message containing only the message ID.
     *
     * @param id The message ID.
     */
    public final void send(final int id) {
        mHandler.sendEmptyMessage(id);
    }

    /**
     * Sends a message containing the ID and an arbitrary object.
     *
     * @param id  The message ID.
     * @param obj The object.
     */
    public final void send(final int id, @NonNull final Object obj) {
        mHandler.sendMessage(mHandler.obtainMessage(id, obj));
    }

    /**
     * Add listener for specific type of message by its ID.
     * Don't forget to call {@link #removeListener(UiMessageCallback)} or
     * {@link #removeListeners(int)}
     *
     * @param id       The ID of message that will be only notified to listener.
     * @param listener The listener.
     */
    public void addListener(int id, @NonNull final UiMessageCallback listener) {
        synchronized (mListenersSpecific) {
            List<UiMessageCallback> idListeners = mListenersSpecific.get(id);
            if (idListeners == null) {
                idListeners = new ArrayList<>();
                mListenersSpecific.put(id, idListeners);
            }
            if (!idListeners.contains(listener)) {
                idListeners.add(listener);
            }
        }
    }

    /**
     * Add listener for all messages.
     *
     * @param listener The listener.
     */
    public void addListener(@NonNull final UiMessageCallback listener) {
        synchronized (mListenersUniversal) {
            if (!mListenersUniversal.contains(listener)) {
                mListenersUniversal.add(listener);
            } else {
                if (DEBUG) {
                    Log.w(TAG, "Listener is already added. " + listener.toString());
                }
            }
        }
    }

    /**
     * Remove listener for all messages.
     *
     * @param listener The listener to remove.
     */
    public void removeListener(@NonNull final UiMessageCallback listener) {
        synchronized (mListenersUniversal) {
            if (DEBUG && !mListenersUniversal.contains(listener)) {
                Log.w(TAG, "Trying to remove a listener that is not registered. " + listener.toString());
            }
            mListenersUniversal.remove(listener);
        }
    }

    /**
     * Remove all listeners for desired message ID.
     *
     * @param id The id of the message to stop listening to.
     */
    public void removeListeners(final int id) {
        if (DEBUG) {
            final List<UiMessageCallback> callbacks = mListenersSpecific.get(id);
            if (callbacks == null || callbacks.size() == 0) {
                Log.w(TAG, "Trying to remove specific listeners that are not registered. ID " + id);
            }
        }
        synchronized (mListenersSpecific) {
            mListenersSpecific.delete(id);
        }
    }

    /**
     * Remove the specific listener for desired message ID.
     *
     * @param id       The id of the message to stop listening to.
     * @param listener The listener which should be removed.
     */
    public void removeListener(final int id, @NonNull final UiMessageCallback listener) {
        synchronized (mListenersSpecific) {
            final List<UiMessageCallback> callbacks = this.mListenersSpecific.get(id);
            if (callbacks != null && !callbacks.isEmpty()) {
                if (DEBUG) {
                    if (!callbacks.contains(listener)) {
                        Log.w(TAG, "Trying to remove specific listener that is not registered. ID " + id + ", " + listener);
                        return;
                    }
                }
                callbacks.remove(listener);
            } else {
                if (DEBUG) {
                    Log.w(TAG, "Trying to remove specific listener that is not registered. ID " + id + ", " + listener);
                }
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        mMessage.setMessage(msg);
        if (DEBUG) {
            logMessageHandling(mMessage);
        }

        // process listeners for specified type of message what
        synchronized (mListenersSpecific) {
            final List<UiMessageCallback> idListeners = mListenersSpecific.get(msg.what);
            if (idListeners != null) {
                if (idListeners.size() == 0) {
                    mListenersSpecific.remove(msg.what);
                } else {
                    mDefensiveCopyList.addAll(idListeners);
                    for (final UiMessageCallback callback : mDefensiveCopyList) {
                        callback.handleMessage(mMessage);
                    }
                    mDefensiveCopyList.clear();
                }
            }
        }

        // process universal listeners
        synchronized (mListenersUniversal) {
            if (mListenersUniversal.size() > 0) {
                mDefensiveCopyList.addAll(mListenersUniversal);
                for (final UiMessageCallback callback : mDefensiveCopyList) {
                    callback.handleMessage(mMessage);
                }
                mDefensiveCopyList.clear();
            }
        }

        mMessage.setMessage(null);

        return true;
    }

    private void logMessageHandling(@NonNull final UiMessage msg) {
        final List<UiMessageCallback> idListeners = mListenersSpecific.get(msg.getId());

        if ((idListeners == null || idListeners.size() == 0) && mListenersUniversal.size() == 0) {
            Log.w(TAG, "Delivering FAILED for message ID " + msg.getId() + ". No listeners. " + msg.toString());
        } else {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Delivering message ID ");
            stringBuilder.append(msg.getId());
            stringBuilder.append(", Specific listeners: ");
            if (idListeners == null || idListeners.size() == 0) {
                stringBuilder.append(0);
            } else {
                stringBuilder.append(idListeners.size());
                stringBuilder.append(" [");
                for (int i = 0; i < idListeners.size(); i++) {
                    stringBuilder.append(idListeners.get(i).getClass().getSimpleName());
                    if (i < idListeners.size() - 1) {
                        stringBuilder.append(",");
                    }
                }
                stringBuilder.append("]");
            }

            stringBuilder.append(", Universal listeners: ");
            synchronized (mListenersUniversal) {
                if (mListenersUniversal.size() == 0) {
                    stringBuilder.append(0);
                } else {
                    stringBuilder.append(mListenersUniversal.size());
                    stringBuilder.append(" [");
                    for (int i = 0; i < mListenersUniversal.size(); i++) {
                        stringBuilder.append(mListenersUniversal.get(i).getClass().getSimpleName());
                        if (i < mListenersUniversal.size() - 1) {
                            stringBuilder.append(",");
                        }
                    }
                    stringBuilder.append("], Message: ");
                }
            }
            stringBuilder.append(msg.toString());

            Log.v(TAG, stringBuilder.toString());
        }
    }

    private static boolean isAppDebug() {
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(Utils.getApp().getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static final class UiMessage {

        private Message mMessage;

        private UiMessage(final Message message) {
            this.mMessage = message;
        }

        private void setMessage(final Message message) {
            mMessage = message;
        }

        public int getId() {
            isUiThread();
            return mMessage.what;
        }

        public Object getObject() {
            isUiThread();
            return mMessage.obj;
        }

        private void isUiThread() {
            if (null == mMessage) {
                throw new IllegalStateException("You can't use LocalMessage instance from a non-UI thread. " +
                        "Extract the data from LocalMessage and don't hold a reference to it outside of handleMessage()");
            }
        }

        @Override
        public String toString() {
            isUiThread();
            final StringBuilder b = new StringBuilder();
            b.append("{ id=");
            b.append(getId());

            if (getObject() != null) {
                b.append(" obj=");
                b.append(getObject());
            }

            b.append(" }");
            return b.toString();
        }
    }

    public interface UiMessageCallback {
        void handleMessage(@NonNull UiMessage localMessage);
    }

    private static final class LazyHolder {
        private static final UiMessageUtils INSTANCE = new UiMessageUtils();
    }
}
