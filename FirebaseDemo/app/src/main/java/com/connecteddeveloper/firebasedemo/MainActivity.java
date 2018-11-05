package com.connecteddeveloper.firebasedemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    FirebaseApp mApp;
    FirebaseDatabase mDatabase;

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
        mApp = FirebaseApp.getInstance();
        mDatabase = FirebaseDatabase.getInstance(mApp);
    }

    private void readDatabaseData() {
        DatabaseReference ref = mDatabase.getReference("chatMessages");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: Snapshot received"
                        + dataSnapshot.getChildren() + " key : " + dataSnapshot.getKey().toString()
                + " value : " + dataSnapshot.getValue().toString());

                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.e(TAG, "Child node: " + child.getChildrenCount() + " key: " +
                            child.getKey().toString() + " value : " + child.getValue().toString());

                    for(DataSnapshot grandChild : child.getChildren()) {
                        Log.e(TAG, "grandChild node: " + grandChild.getChildrenCount()
                                + " key: " + grandChild.getKey().toString() + " value : "
                                + grandChild.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(eventListener);
    }
}
