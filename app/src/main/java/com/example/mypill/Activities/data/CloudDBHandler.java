package com.example.mypill.Activities.data;

import android.content.Context;
import android.util.Log;

import com.example.mypill.Activities.interfaces.DBHandlerInterface;
import com.example.mypill.Activities.utils.JSONUtils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.mypill.Activities.utils.GlobalApplication;

import org.json.JSONObject;

/*
    This class is responsible to save entries to Firebase

    Firebase handles automatically the synchronization of the data
    when the user if offline, so there is no need to implement it

    TODO set authentication. MAJOR security risk at the moment
*/

public class CloudDBHandler implements DBHandlerInterface {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Context context = GlobalApplication.getAppContext();

    public CloudDBHandler() {
        FirebaseApp.initializeApp(context);
        database = FirebaseDatabase.getInstance();
    }

    public boolean addEntry(int pillID, String action, String time) {
        // push() is used so every new addition of data, to be stored under
        // a unique ID and not replace the previous values
        myRef = database.getReference("/Actions/" + time.split(" ")[1]).push();

        JSONObject entryJSON = new JSONObject();
        try {
            entryJSON.put("pillID", pillID);
            entryJSON.put("action", action);
            entryJSON.put("time", time.split(" ")[0]);

            myRef.setValue(JSONUtils.jsonToMap(entryJSON));
            Log.i("FirebaseSDK", "entry has been saved");
            return true;
        } catch (org.json.JSONException e) {
            Log.e("FirebaseSDK", e.getMessage());
            return false;
        }
    }

}
