package com.example.peaches;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;

public class MessageManager {

    private final static String fileName = "messages.txt";
    private final static String TAG = MessageManager.class.getName();
    private final static String arrayName = "uniqueLines";


    public static String ReadFile(Context context){
        //final String path = context.getFilesDir().getAbsolutePath();

        //check if first time running
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            // makes not first run anymore
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();

            // if first run, create array for unique lines
            int[] uniqueLines = createArray(context);
            saveArray(uniqueLines, arrayName, context);

        }


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



            // get array from preferences memory
            int[] uniqueLines = loadArray(arrayName, context);

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

    // save unique lines to memory
    private static boolean saveArray(int[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencenums", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0; i<array.length; i++)
            editor.putInt(arrayName + "_" + i, array[i]);
        return editor.commit();
    }

    // get unique lines from memory
    private static int[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencenums", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        int array[] = new int [size];
        for(int i=0; i<size; i++)
            array[i] = prefs.getInt(arrayName + "_" + i, 0);
        return array;
    }

    // create array that stores still unique lines
    private static int[] createArray(Context context){
        try {
            // open file
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));

            // find length of file
            int totalLines = 0;
            while (reader.readLine() != null)
                totalLines++;
            reader.close();

            // make length of file array and fill in all nums of lines
            int[] uniqueLines = new int [totalLines];
            for (int i = 0; i < totalLines; i++){
                uniqueLines[i] = i;
            }

            return uniqueLines;

        }
        catch(FileNotFoundException e){
            Log.d(TAG, e.getMessage());
        }
        catch(IOException e) {
            Log.d(TAG, e.getMessage());
        }

        // if fails to make array
        return new int [0];
    }
}

