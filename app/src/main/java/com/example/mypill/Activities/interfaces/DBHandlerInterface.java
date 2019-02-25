package com.example.mypill.Activities.interfaces;

/*
    This interface is used to ensure every DB handler follows
    the same structure

    For the current requirements of the application, this is simple
    but in the future if deleteEntries, getAllEntries implemented their
    behaviour must be defined here
*/

import com.example.mypill.Activities.data.Entry;

import java.util.ArrayList;

public interface DBHandlerInterface {

    public boolean addEntry(Entry entry);
    public ArrayList<Entry> getEntries(int limiter);
}
