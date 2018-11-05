package com.connecteddeveloper.firebasedemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    FirebaseApp mApp;
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "onCreate: FirebaseDemo project started");

        initFirebase();

        // readDatabaseData();
        // writeDatabaseData();
        //writeObject();
        // readObjects();


        //registerNewUser();
        // logout();
        //loginUser();
        logout();
    }

    private void loginUser() {
        OnCompleteListener<AuthResult> success = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    Log.e(TAG, "User logged on");
                else
                    Log.e(TAG, "User log on response, but failed");
            }
        };

        OnFailureListener fail = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "User log on failed");
            }
        };

        String email = "andre.lashley@gmail.com";
        String password = "THGHOBS316!";

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(success)
                .addOnFailureListener(fail);
    }

    private void logout() {
        mAuth.signOut();
    }

    private void registerNewUser() {
        OnCompleteListener<AuthResult> success = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    Log.e(TAG, "User registration successful");
                else
                    Log.e(TAG, "User registration failed");
            }
        };

        OnFailureListener fail = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Registration call failed");
            }
        };

        String email = "andre.lashley@gmail.com";
        String password = "THGHOBS316!";

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(success)
                .addOnFailureListener(fail);
    }

    private void readObjects() {
        DatabaseReference ref = mDatabase.getReference("chatMessages");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: Data received " + dataSnapshot.getChildrenCount());

                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    ChatMessage msg;
                    msg = child.getValue(ChatMessage.class);

                    Log.e(TAG, "Child : " + msg.chatMessage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(listener);
    }

    private void writeObject() {
        ChatMessage msg = new ChatMessage();
        msg.sender = "Lydia";
        msg.sentTime = "5:30 2018-11-04";
        msg.chatMessage = "Hello World";

        DatabaseReference ref = mDatabase.getReference("chatMessages")
                .child(msg.sender + " " + msg.sentTime);
        ref.setValue(msg);

    }

    private void writeDatabaseData() {
        DatabaseReference ref = mDatabase.getReference("chatMessages")
                .child("Andre 12:43:25 11-04-2018");

        ref.child("sentTime").setValue("12:43:25 11-04-2018");
        ref.child("sender").setValue("Andre");
        ref.child("chatMessage").setValue("Today is Sunday and it's sunny");
    }

    private void initFirebase() {
        mApp = FirebaseApp.getInstance();
        mDatabase = FirebaseDatabase.getInstance(mApp);
        mAuth = FirebaseAuth.getInstance(mApp);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();

                if(user != null) {
                    Log.e(TAG, "User is logged in : " + user.getEmail().toString());
                } else {
                    Log.e(TAG, "No user logged in");
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
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
