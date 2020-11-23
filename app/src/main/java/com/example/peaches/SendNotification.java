package com.example.peaches;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SendNotification extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        final String fileName = "messages/messages.txt";

        /*String message = MessageManager.getLine(context.getApplicationContext());
        //TextView messageText = (TextView)findViewById(R.id.message_text);
        //messageText.setText(message);

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

        mNotificationManager.notify(0, mBuilder.build());*/


        Toast.makeText(context, "New Peach!",Toast.LENGTH_LONG).show();

        MainActivity.getInstance().displayNotification();
    }



}
