package com.example.peaches;
//test message
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

public class MainActivity extends AppCompatActivity {


    private static MainActivity instance;

    private ImageButton image_button_settings;

    public static final String CHANNEL_ID = "peaches_1";
    public static final String CHANNEL_NAME = "Peaches";
    public static final String CHANNEL_DESC = "Peaches Notification Channel";

    /*public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;*/

    public static final String fileName = "messages.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        image_button_settings = (ImageButton) findViewById(R.id.image_button_settings);
        image_button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        createNotificationChannel();

        findViewById(R.id.buttonNotify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotification();
            }
        });

    }

    // create notification
    public void displayNotification(){

        // sets message to textview and displays notification with message
        String message = MessageManager.getLine(getApplicationContext());
        TextView messageText = (TextView)findViewById(R.id.message_text);
        messageText.setText(message);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_peach)
                    .setContentTitle("Peaches")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(this);
        mNotificationMgr.notify(1, mBuilder.build());

    }

    public void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

    }

    public void openSettings() {
        Intent intent = new Intent(this, SettingsThing.class);
        startActivity(intent);
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void makeToast() {
        Toast.makeText(this, "YEEEEEEEEEEEET", Toast.LENGTH_SHORT).show();
    }


}
