package com.example.mypill.Activities.data;

/*
    This class is responsible to manage the data

    Currently, there are two databases
    1. Local Database (SQLite)
    2. Cloud Database (Firebase)

    There is no need to initialize anything, as SQLite and
    Firebase do it automatically

    Every time a new entry must be stored:
    1. Save it to local DB
    2. Save it to Firebase
*/

import android.content.Context;
import android.icu.text.SimpleDateFormat;

import com.example.mypill.Activities.utils.GlobalApplication;

import java.util.ArrayList;
import java.util.Date;

public class DataHandler {

    private LocalDBHandler localDB;
    private CloudDBHandler cloudDB;
    private Context context = GlobalApplication.getAppContext();

    public DataHandler() {
        localDB = new LocalDBHandler(context);
        cloudDB = new CloudDBHandler();
    }

    public void saveEntry(int pillID, String action) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String currentTime = sdf.format(new Date());

        saveEntryLocally(new Entry(pillID, action, currentTime));
        saveEntryRemotely(new Entry(pillID, action, currentTime));

        // For 100% accurate synchronization between the databases
        // If one fails, then the other one must be cancelled/undone
    }

    private void saveEntryLocally(Entry entry) {
        localDB.addEntry(entry);
    }

    private void saveEntryRemotely(Entry entry) {
        cloudDB.addEntry(entry);
    }

    public ArrayList<Entry> getEntries(int limitter) {
        return localDB.getEntries(limitter);
    }
}
