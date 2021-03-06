package com.example.mypill.Activities.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.mypill.Activities.interfaces.DBHandlerInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

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

    public boolean addEntry(Entry entry) {
        try {
            // Gets the data repository in write mode
            SQLiteDatabase db = getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(LocalDBHandler.FeedEntry.COLUMN_NAME_PILL_ID, entry.getPillID());
            values.put(LocalDBHandler.FeedEntry.COLUMN_NAME_ACTION, entry.getAction());
            values.put(LocalDBHandler.FeedEntry.COLUMN_NAME_TIME, entry.getTime());

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

    public ArrayList<Entry> getEntries(int limiter) {
        ArrayList<Entry> entries = new ArrayList<Entry>();

        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM entries order by _id desc limit " + limiter;
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_PILL_ID)));
            String action = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_ACTION));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TIME));
            entries.add(new Entry(id, action, time));
        }

        return entries;
    }

    public ArrayList<String[]> getTotalEntries() {
        ArrayList<String[]> totalEntries = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String totalSelectQuery = "SELECT Count(*) AS Total FROM entries WHERE action=\"forgotten\" UNION\n" +
                "SELECT Count(*) AS Total FROM entries WHERE action=\"taken\"";
        Cursor totalCursor = db.rawQuery(totalSelectQuery, null);

        while (totalCursor.moveToNext()) {
            int total = totalCursor.getInt(totalCursor.getColumnIndexOrThrow("Total"));
            System.out.println(totalCursor.getPosition());
            String[] tempArray = new String[2];
            tempArray[1] = String.valueOf(total);

            if (totalCursor.getPosition() == 0) {
                tempArray[0] = "Forgotten";
            } else if (totalCursor.getPosition() == 1) {
                tempArray[0] = "Ingested";
            }
            totalEntries.add(tempArray);
        }

        return totalEntries;
    }

    public void ClearDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);
    }
}
