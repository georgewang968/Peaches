package com.example.peaches;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;

public class MessageManager {

    private final static String fileName = "messages.txt";
    private final static String TAG = MessageManager.class.getName();


    public static String ReadFile(MainActivity context){
        //final String path = context.getFilesDir().getAbsolutePath();
        String line = "";

        try {
            // open file
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));

            // get random line
            // first find length of file
            int totalLines = 0;
            while (reader.readLine() != null)
                totalLines++;
            reader.close();

            // now pull random line
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            int pos = (int)(Math.random() * totalLines);
            for (int i = 0; i < pos; i++)
                reader.readLine();

            line = reader.readLine();

            reader.close();
        }
        catch(FileNotFoundException e){
            Log.d(TAG, e.getMessage());
        }
        catch(IOException e){
            Log.d(TAG, e.getMessage());
        }
        return line;
    }
}

