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
import java.util.ArrayList;

public class MessageManager {

    private final static String fileName = "messages/messages.txt";
    private final static String TAG = MessageManager.class.getName();
    private final static String arrayName = "uniqueLines";
    private final static String arraySizeName = "arraySize";
    private final static char newlineSymbol = '*';
    private final static String defaultListName = "defaultList";


    // sets messages to be used, main function that is called from other places
    public static void setDefaultArrayLists(ArrayList<String> arrayNames, Context context){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        boolean isFirstRun = pref.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            // makes not first run anymore
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }

        saveArrayList(arrayNames, defaultListName, context);
        setArrayListsToMemory(arrayNames, context);
    }

    // set appropriate text files to be used in the random drawing
    // function creates arraylists and stores them in sharedpreferences
    // name of text files will be used as the key to access them and the indicator
    // for which files are used
    public static void setArrayListsToMemory(ArrayList<String> arrayNames, Context context){
        Log.d("test", "setArrayListToMemory ran");
        // check if first run
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFirstRun = pref.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            // makes not first run anymore
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }

        for (int i = 0; i < arrayNames.size(); i++){
            // enter file and save values to array
            try{
                // open file
                BufferedReader reader = null;
                Log.d("test", "array name " + arrayNames.get(i));
                reader =  new BufferedReader(new InputStreamReader(context.getAssets().open("messages/" + arrayNames.get(i))));
                // create arraylist to hold file lines
                ArrayList<String> lines = new ArrayList<String>();
                // read lines to arraylist lines until end of file
                String line;
                while ((line = reader.readLine()) != null){
                    lines.add(line);
                }
                // now save arraylist to sharedpreferences for further use
                saveArrayList(lines, arrayNames.get(i), context);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        saveArrayList(arrayNames, "arrayNames", context);

    }

    // new getLine function
    // load up all the saved ArrayLists
    // then pick random string to remove
    // check ArrayLists for empty lists to delete
    // save ArrayLists back to sharedpreferences
    public static String getLineChanged(Context context){
        Log.d("test", "testing");
        // check if first run
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFirstRun = pref.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            // makes not first run anymore
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();

            ArrayList<String> arrayNames = new ArrayList<String>();
            arrayNames.add("messages/messages.txt");
            arrayNames.add("messages/messages2.txt");
            setDefaultArrayLists(arrayNames, context);
        }

        // load ArrayLists
        ArrayList<String> arrayNames = loadArrayList("arrayNames", context);
        ArrayList<ArrayList<String>> messages = new ArrayList<>();
        for (int i = 0; i < arrayNames.size(); i++){
            ArrayList<String> lines = loadArrayList(arrayNames.get(i), context);
            messages.add(lines);
        }
        // randomized draw
        int randPos = (int)(Math.random() * messages.size());
        // remove line from one set of messages
        Log.d("test", "messages size" + messages.size());
        Log.d("test", "messages file size" + messages.get(0).size());
        String message = messages.get(randPos).remove((int)(Math.random() * messages.get(randPos).size()));
        // check if ArrayList is empty
        for (int i = 0; i < messages.size(); i++){
            if (messages.get(i).size() == 0){
                arrayNames.remove(i);
                messages.remove(i);
                i--;
            }
        }
        // check if all ArrayLists are empty
        if (messages.size() == 0){
            // sets to default
            ArrayList<String> defaultList = loadArrayList(defaultListName, context);
            setArrayListsToMemory(defaultList, context);
            saveArrayList(defaultList, "arrayNames", context);
        }
        else {

            // save ArrayLists back
            int size = messages.size();
            for (int i = 0; i < size; i++) {
                saveArrayList(messages.get(i), arrayNames.get(i), context);
            }
            saveArrayList(arrayNames, "arrayNames", context);
        }


    return message;
    }


    // old version, only supports one file
    public static String getLine(Context context){
        //check if first time running
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        boolean isFirstRun = pref.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            // makes not first run anymore
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();

            // if first run, create array for unique lines
            int[] uniqueLines = createArray(context);
            saveArray(uniqueLines, arrayName, context);

            // length of unique lines
            editor.putInt(arraySizeName, uniqueLines.length);
            editor.apply();

        }


        String line = "";

        try {
            // open file
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));

            // get array from preferences memory
            int[] uniqueLines = loadArray(arrayName, context);
            // get arraySize which is length of uniqueLines
            int arraySize = pref.getInt(arraySizeName, 0);
            if (arraySize == 0) {
                uniqueLines = createArray(context);
                saveArray(uniqueLines, arrayName, context);
                arraySize = uniqueLines.length;
            }
            // get random line
            int pos = (int)(Math.random() * arraySize);
            for (int i = 0; i < uniqueLines[pos]; i++)
                reader.readLine();
            line = reader.readLine();
            // now erase pos by switching with last relevant position in array
            int temp = uniqueLines[pos];
            uniqueLines[pos] = uniqueLines[arraySize - 1];
            uniqueLines[arraySize - 1] = temp;
            saveArray(uniqueLines, arrayName, context);

            // now save arraySize as 1 less than original
            arraySize--;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt(arraySizeName, arraySize);
            editor.apply();

            reader.close();
        }
        catch(FileNotFoundException e){
            Log.d(TAG, e.getMessage());
        }
        catch(IOException e){
            Log.d(TAG, e.getMessage());
        }
        return addNewlines(line);
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



    // new save unique lines to memory for ArrayList
    public static boolean saveArrayList(ArrayList<String> array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencenums", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.size());
        for (int i = 0; i < array.size(); i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        return editor.commit();
    }

    private static ArrayList<String> loadArrayList(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencenums", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        ArrayList<String> array = new ArrayList<String>();
        for (int i = 0; i < size; i++)
            array.add(prefs.getString(arrayName + "_" + i, ""));
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

    private static String addNewlines(String message){
        return message.replace(newlineSymbol, '\n');
    }
}

