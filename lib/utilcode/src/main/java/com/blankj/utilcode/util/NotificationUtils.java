package com.blankj.utilcode.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/02/20
 *     desc  : utils about notification
 * </pre>
 */
public class NotificationUtils {

    private NotificationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void create(Context context, int id, Intent intent, int smallIcon, String contentTitle, String contentText) {
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Intent para disparar o broadcast
        PendingIntent p = PendingIntent.getActivity(Utils.getApp(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Cria a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(p)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(smallIcon)
                .setAutoCancel(true);

        // Dispara a notification
        Notification n = builder.build();
        manager.notify(id, n);
    }

    public static void createStackNotification(Context context, int id, String groupId, Intent intent, int smallIcon, String contentTitle, String contentText) {
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Intent para disparar o broadcast
        PendingIntent p = intent != null ? PendingIntent.getActivity(Utils.getApp(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT) : null;

        // Cria a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(p)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(smallIcon)
                .setGroup(groupId)
                .setAutoCancel(true);

        // Dispara a notification
        Notification n = builder.build();
        manager.notify(id, n);
    }

    // Notificação simples sem abrir intent (usada para alertas, ex: no wear)
    public static void create(int smallIcon, String contentTitle, String contentText) {
        NotificationManager manager =
                (NotificationManager) Utils.getApp().getSystemService(Context.NOTIFICATION_SERVICE);

        // Cria a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(Utils.getApp())
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(smallIcon)
                .setAutoCancel(true);

        // Dispara a notification
        Notification n = builder.build();
        manager.notify(0, n);
    }

    public static void cancel(@Nullable String tag, final int id) {
        NotificationManagerCompat.from(Utils.getApp()).cancel(tag, id);
    }

    public static void cancel(final int id) {
        NotificationManagerCompat.from(Utils.getApp()).cancel(id);
    }

    public static void cancelAll() {
        NotificationManagerCompat.from(Utils.getApp()).cancelAll();
    }
}
