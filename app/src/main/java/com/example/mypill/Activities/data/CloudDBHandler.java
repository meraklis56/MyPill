package com.example.mypill.Activities.data;

import android.content.Context;
import android.util.Log;

import com.example.mypill.Activities.interfaces.DBHandlerInterface;
import com.example.mypill.Activities.utils.JSONUtils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.mypill.Activities.utils.GlobalApplication;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;

/*
    This class is responsible to save entries to Firebase

    Firebase handles automatically the synchronization of the data
    when the user if offline, so there is no need to implement it

    In the Firebase database, only authenticated users are allowed
    to read/write data

    TODO set authentication. MAJOR security risk at the moment
*/

public class CloudDBHandler implements DBHandlerInterface {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Context context = GlobalApplication.getAppContext();
    private String userID;

    public CloudDBHandler() {
        FirebaseApp.initializeApp(context);
        database = FirebaseDatabase.getInstance();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public boolean addEntry(Entry entry) {
        // push() is used so every new addition of data, to be stored under
        // a unique ID and not replace the previous values
        myRef = database.getReference("users/" + userID + "/actions/").push();

        JSONObject entryJSON = new JSONObject();
        try {
            entryJSON.put("pillID", entry.getPillID());
            entryJSON.put("action", entry.getAction());
            entryJSON.put("time", entry.getTime());

            myRef.setValue(JSONUtils.jsonToMap(entryJSON));
            Log.i("FirebaseSDK", "entry has been saved");
            return true;
        } catch (org.json.JSONException e) {
            Log.e("FirebaseSDK", e.getMessage());
            return false;
        }
    }

    public ArrayList<Entry> getEntries(int limiter) {
        myRef = database.getReference("/Actions");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getChildren().forEach(entry -> {
                        entry.getChildren().forEach(field -> {
                            String input = field.getValue().toString();
                            // TODO parse it
                        });
                    });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //TODO implement
        return null;
    }

}
