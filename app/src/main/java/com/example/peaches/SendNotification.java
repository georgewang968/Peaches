package com.example.peaches;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.example.peaches.MainActivity.CHANNEL_DESC;
import static com.example.peaches.MainActivity.CHANNEL_ID;
import static com.example.peaches.MainActivity.CHANNEL_NAME;

public class SendNotification extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        final String fileName = "messages.txt";

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }*/

        int notificationId = intent.getIntExtra("notificationId", 0);
        String message = "yeet";

        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        NotificationManager myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_peach)
                .setContentTitle("Peaches")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        myNotificationManager.notify(notificationId, builder.build());

        Toast.makeText(context, "does this even work?",Toast.LENGTH_LONG).show();
    }



}
