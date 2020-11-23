package com.example.peaches;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseFile extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayNames;
    ArrayList<String> selectedItems = new ArrayList<>();
    //FileListViewModel fileListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        runFirstTime();

        setContentView(R.layout.activity_choose_file);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_choose_file);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        String[] testData = new String[]{"hello","there","general","kenobi"};
        final FileListAdapter adapter = new FileListAdapter(testData);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemCheckListener(new FileListAdapter.OnItemCheckListener() {

            @Override
            public void onItemChanged(TextView textView, CheckBox checkBox) {
                Log.d("debug", "textview check change: " + textView.getText().toString() + " " + (checkBox.isChecked()?"true":"false"));
            }
        });

        //fileListViewModel = ViewModelProviders.of(this).get(FileListViewModel.class);
        /*fileListViewModel.getAllFiles().observe(this, new Observer<List<FileList>>() {
            @Override
            public void onChanged(List<FileList> fileLists) {
                // TODO add on changed behavior
            }
        });*/

/*
        listView = (ListView)findViewById(R.id.list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        arrayNames = new ArrayList<String>();
        try {
            String[] files = getAssets().list("messages");
            arrayNames = new ArrayList<String>(Arrays.asList(files));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.listviewlayout, R.id.checked_list, arrayNames);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ChooseFile.this,"item " + position + " " + arrayNames.get(position), Toast.LENGTH_SHORT).show();
                String selectedItem = arrayNames.get(position);
                if (selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem);
                else
                    selectedItems.add(selectedItem);
            }
        });

 */


        /*
        // gets all files from assets
        String[] f = new String[0];
        try {
            f = getAssets().list("");
            for(String f1 : f){
                Log.v("names",f1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("test", "failed to get files");
        }
         */
    }

    public void showSelectedItems(View view){
        String items = "";
        for (String item : selectedItems){
            items += "-" + item + "\n";
        }
        Toast.makeText(ChooseFile.this, "You have selected \n" + items, Toast.LENGTH_LONG).show();
        if (!items.equals(""))
            MessageManager.setDefaultArrayLists(selectedItems, getApplicationContext());
    }

    public void runFirstTime(){
        SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);

        if (settings.getBoolean("my_first_time", true)) {
            Log.d("debug", "first time");

            prepopulateFiles();

            settings.edit().putBoolean("my_first_time", false).apply();
        }
    }

    public void prepopulateFiles(){
        String[] fileText1 = new String[] {"hello there"
                , "this is a message"
                , "there should be a couple to test the messaging system"
                , "only a couple more till this is done"
                , "hopefully third time's the charm"
                , "message a is this", "goodbye"};
        String[] fileText2 = new String[] {"this is the second file (2)"
                , "the two is there to indicate its the second file (2)"
                , "there will not be very many messages (2)"
                , "this should be fine (2)"};
        File file1 = new File(getApplicationContext().getFilesDir(), "file1.txt");
        File file2 = new File(getApplicationContext().getFilesDir(), "file2.txt");
        try {
            PrintWriter writer1 = new PrintWriter(file1, "UTF-8");
            for (int i = 0; i < fileText1.length; i++) {
                writer1.write(fileText1[i]);
            }
            writer1.close();
            PrintWriter writer2 = new PrintWriter(file2, "UTF-8");
            for (int i = 0; i < fileText2.length; i++) {
                writer2.write(fileText2[i]);
            }
            writer2.close();
        }
        catch (Exception e) {
            Log.d("debug", "error pre-populating files");
        }
    }
}
