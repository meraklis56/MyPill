package com.example.mypill.Activities.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mypill.Activities.interfaces.DBHandlerInterface;

/*
    This class is responsible to save and manage data locally
    using SQLite
*/
public class LocalDBHandler extends SQLiteOpenHelper implements DBHandlerInterface {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ActionEntries.db";

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entries";
        public static final String COLUMN_NAME_PILL_ID = "pill_id";
        public static final String COLUMN_NAME_ACTION = "action";
        public static final String COLUMN_NAME_TIME = "time";
    }

    public LocalDBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_PILL_ID + " INTEGER," +
                    FeedEntry.COLUMN_NAME_ACTION + " TEXT," +
                    FeedEntry.COLUMN_NAME_TIME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public void onCreate(SQLiteDatabase db) {
        System.out.println(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean addEntry(int pillID, String action, String time) {
        try {
            // Gets the data repository in write mode
            SQLiteDatabase db = getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(LocalDBHandler.FeedEntry.COLUMN_NAME_PILL_ID, pillID);
            values.put(LocalDBHandler.FeedEntry.COLUMN_NAME_ACTION, action);
            values.put(LocalDBHandler.FeedEntry.COLUMN_NAME_TIME, time);

            long newRowID = db.insert(LocalDBHandler.FeedEntry.TABLE_NAME, null, values);
            if (newRowID > -1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e("SQLite", e.getMessage());
            return false;
        }
    }
}
