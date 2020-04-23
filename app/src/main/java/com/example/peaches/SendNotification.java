package com.example.peaches;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.TextView;
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



        //int notificationId = intent.getIntExtra("notificationId", 0);

        String message = MessageManager.getLine(context.getApplicationContext());
        //TextView messageText = (TextView)findViewById(R.id.message_text);
        //messageText.setText(message);

        /*Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);



        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_peach)
                .setContentTitle("Peaches")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        NotificationManager myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            //NotificationManager manager = context.getSystemService(NotificationManager.class);
            //assert manager != null;
            myNotificationManager.createNotificationChannel(channel);

        }

        //assert myNotificationManager != null;
        myNotificationManager.notify(notificationId, builder.build());*/

        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent ii = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_peach);
        mBuilder.setContentTitle("Peaches");
        mBuilder.setContentText(message);
        mBuilder.setPriority(Notification.PRIORITY_MAX);

        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());

        Toast.makeText(context, "does this even work?",Toast.LENGTH_LONG).show();
    }



}