package com.example.peaches;
//test message
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static com.example.peaches.App.CHANNEL_MAIN;

public class MainActivity extends AppCompatActivity {


    private static MainActivity ins;

    private ImageButton image_button_settings;

    public static final String CHANNEL_ID = "peaches_1";
    public static final String CHANNEL_NAME = "Peaches";
    public static final String CHANNEL_DESC = "Peaches Notification Channel";

    /*public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;*/

    public static final String fileName = "messages/messages.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ins = this;
        setContentView(R.layout.activity_main);


        image_button_settings = (ImageButton) findViewById(R.id.image_button_settings);
        image_button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        ImageButton image_button_files = (ImageButton) findViewById(R.id.image_button_files);
        image_button_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {openFiles();}
        });

        findViewById(R.id.buttonNotify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //displayNotification();
                alternateNotification();
            }
        });

        //debug lines
        Log.d("test", "onCreate ran");

    }

    public void alternateNotification(){
        Log.d("debug", "file location: " + getApplicationContext().getFilesDir());
        String fileStrings = "";
        String[] files = getApplicationContext().fileList();
        for (int i = 0; i < files.length; i++){
            fileStrings += files[i] + " ";
        }
        Log.d("debug", "file list 1 " + fileStrings);
        File file = new File(getApplicationContext().getFilesDir(), "test.txt");
        try{
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("text".getBytes());
        }
        catch (Exception e) {
            Log.d("debug", "error writing file");
        }
    }


    // create notification
    public void displayNotification(){


        // sets message to textview and displays notification with message
        String message = MessageManager.getLineChanged(getApplicationContext());
        TextView messageText = (TextView)findViewById(R.id.message_text);
        messageText.setText(message);

        // creates intent to activity
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,1,mainIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        // builds and sends notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_MAIN)
                    .setSmallIcon(R.drawable.ic_peach)
                    .setContentTitle("Peaches")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(this);
        mNotificationMgr.notify(1, mBuilder.build());

        // log lines
        Log.d("test", "display message ran");
        Log.d("test", "");
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
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.right_slide_in, R.anim.left_slide_out);
        Intent intent = new Intent(this, SettingsThing.class);
        startActivity(intent, options.toBundle());
        overridePendingTransition(R.anim.right_slide_in, R.anim.left_slide_out);
    }

    public void openFiles() {
        Intent intent = new Intent(this, ChooseFile.class);
        startActivity(intent);
    }

    public static MainActivity getInstance() {
        return ins;
    }

    public void makeToast() {
        Toast.makeText(this, "YEEEEEEEEEEEET", Toast.LENGTH_SHORT).show();
    }


}
