package com.example.bf6193.notificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MyFirebaseMessagingService extends FirebaseMessagingService {
    RequestQueue queue;
    Notification myNotication;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    String Nid = "",messageBody="";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("FCM", "onMessageReceived:"+remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage.getNotification().getBody());
    }

    private void sendNotification(String message) {
        String[] messageBodyList = message.split(",");
        Nid = messageBodyList[0];
        messageBody = messageBodyList[1];


        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("Nid", Nid);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= 26)
        {
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(id, description, importance);
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(this, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(messageBody)
                    .setContentText("Hello world")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            manager.notify(1, notification);
        }
        else
        {
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle(messageBody)
                    .setContentText("Hello world")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            manager.notify(1,notification);
        }

    }
}