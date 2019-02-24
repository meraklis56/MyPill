package com.example.mypill.Activities.data;

/*
    This class is responsible to manage the data

    Currently, there are two databases
    1. Local Database (SQLite)
    2. Remote Database (Firebase)

    When the app is logged in for the first time it is responsible to:
    1. Initialize the local DB (create tables, etc)
    2. Set up Firebase connection

    Every time a new entry must be stored:
    1. Save it to local DB
    2. Notify Firebase
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;

import com.example.mypill.Activities.utils.GlobalApplication;

import java.util.Date;

public class DataHandler {

    private LocalDBHandler localDB;
    private Context context = GlobalApplication.getAppContext();

    public DataHandler() {
        localDB = new LocalDBHandler(context);
    }

    public void initializeDBs() {
    }

    public void saveEntry(int pillID, String action) {

        saveEntryLocally(pillID, action);

        saveEntryRemotely(pillID, action);
    }

    private void saveEntryLocally(int pillID, String action) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

        System.out.println(pillID + "," + action + sdf.format(new Date()));

        // Gets the data repository in write mode
        SQLiteDatabase db = localDB.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(LocalDBHandler.FeedEntry.COLUMN_NAME_PILL_ID, pillID);
        values.put(LocalDBHandler.FeedEntry.COLUMN_NAME_ACTION, action);
        values.put(LocalDBHandler.FeedEntry.COLUMN_NAME_TIME, sdf.format(new Date()));

        long newRowID = db.insert(LocalDBHandler.FeedEntry.TABLE_NAME, null, values);
        Log.i("LocalDBHandler", "new RowID: " + newRowID);
    }

    private void saveEntryRemotely(int pillId, String action) {
    }
}
