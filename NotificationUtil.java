package com.pristyn.care.pristyndocapp.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.pristyn.care.pristyndocapp.R;

public class NotificationUtil {
    private static final int DEFAULT_NOTIFICATION_ID = 0;
    private final NotificationManager notificationManager;
    private final Context context;
    @Nullable
    private PendingIntent pendingIntent;
    private String channelId;
    private CharSequence title;
    private CharSequence body;
    private int priority = NotificationCompat.PRIORITY_DEFAULT;
    private CharSequence channelName;
    private boolean enableVibration = false;
    private int notificationId = DEFAULT_NOTIFICATION_ID;
    private boolean autoCancel = true;
    private boolean ongoing = false;
    private String category = NotificationCompat.CATEGORY_REMINDER;
    private Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    public NotificationUtil(Context context) {
        this.context = context.getApplicationContext();
        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void cancelNotification(Context context, int notificationId) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(notificationId);
        }
    }

    public Notification composeNotification() {

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(autoCancel)
                        .setSound(soundUri)
                        .setContentIntent(pendingIntent)
                        .setCategory(category)
                        .setOngoing(ongoing)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                        .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                        .setSmallIcon(R.drawable.ic_notification_small_icon)
                        .setPriority(priority);

//         Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(channelId, channelName,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);

            if (NotificationCompat.CATEGORY_ALARM.equals(category)) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();

                channel.setSound(soundUri, audioAttributes);
            }

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = notificationBuilder.build();

        if (NotificationCompat.CATEGORY_ALARM.equals(category)) {
            notification.flags |= Notification.FLAG_INSISTENT;
        }

        return notification;
    }

    public void notify(Notification notification) {
        if (notificationManager != null) {
            notificationManager.notify(notificationId, notification);
        }
    }

    public void setPendingIntent(@Nullable PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public void setBody(CharSequence body) {
        this.body = body;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setChannelName(CharSequence channelName) {
        this.channelName = channelName;
    }

    public void enableVibration(boolean enableVibration) {
        this.enableVibration = enableVibration;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public void setAutoCancel(boolean autoCancel) {
        this.autoCancel = autoCancel;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSoundUri(Uri soundUri) {
        this.soundUri = soundUri;
    }
}
