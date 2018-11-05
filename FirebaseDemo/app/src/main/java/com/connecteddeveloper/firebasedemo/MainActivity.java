package com.connecteddeveloper.firebasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "onCreate: FirebaseDemo project started");

        initFirebase();

        readDatabaseData();
        // writeDatabaseData();
        // readObjects();
        // writeObjects();
        // authentication();
    }

    private void initFirebase() {
    }

    private void readDatabaseData() {
        
    }
}
