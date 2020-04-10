package com.example.peaches;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MessageManager {

    private final static String fileName = "messages.txt";
    private final static String TAG = MessageManager.class.getName();

    public static String ReadFile(Context context){
        //final String path = context.getFilesDir().getAbsolutePath();
        String line = "";

        try {
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));

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

