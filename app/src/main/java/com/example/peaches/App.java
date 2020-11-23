package com.example.peaches;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.io.IOException;

public class App extends Application {
    public static final String CHANNEL_MAIN = "channel_main";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channelMain = new NotificationChannel(CHANNEL_MAIN,
                    "Main Channel",
                    NotificationManager.IMPORTANCE_HIGH);

            channelMain.setDescription("Sends out messages of your choosing over a specified time interval.");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channelMain);
        }
    }


}
