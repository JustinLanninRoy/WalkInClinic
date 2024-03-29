package com.example.walkinclinic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private TextView title;
    private static final String TAG = "ListActivity";
    DatabaseHelper databaseHelper;
    private ListView mListView;
    private String selectedType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = findViewById(R.id.textView10);
        setContentView(R.layout.list_layout);
        mListView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);
        Intent received = getIntent();
        selectedType = received.getStringExtra("type");
        if (selectedType.equals("patient")){
            populateListView();
        } else if (selectedType.equals("employee")){
            populateListViewA();
        }
    }

    private void populateListView(){
        Log.d(TAG, "populateListView: Displaying data in the ListView");
        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(0) + ": " + data.getString(1) + " " + data.getString(2));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                String[] split = name.split(": ");
                Log.d(TAG, "onItemClick: You Clicked on " + name);
                Intent editScreenIntent = new Intent(ListActivity.this, EditDataActivity.class);
                editScreenIntent.putExtra("id", Integer.parseInt(split[0]));
                editScreenIntent.putExtra("name", name);
                editScreenIntent.putExtra("type", "patient");
                databaseHelper.close();
                startActivity(editScreenIntent);
                ListActivity.this.finish();
            }
        });
    }

    private void populateListViewA(){
        Log.d(TAG, "populateListView: Displaying data in the ListView");
        Cursor data = databaseHelper.getDataA();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(0) + ": " + data.getString(1) + " " + data.getString(2));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                String[] split = name.split(": ");
                Log.d(TAG, "onItemClick: You Clicked on " + name);
                Intent editScreenIntent = new Intent(ListActivity.this, EditDataActivity.class);
                editScreenIntent.putExtra("id", Integer.parseInt(split[0]));
                editScreenIntent.putExtra("type", "employee");
                databaseHelper.close();
                startActivity(editScreenIntent);
                ListActivity.this.finish();
            }
        });
    }
}
