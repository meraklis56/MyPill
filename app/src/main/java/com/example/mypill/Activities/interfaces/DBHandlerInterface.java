package com.example.mypill.Activities.interfaces;

/*
    This interface is used to ensure every DB handler follows
    the same structure

    For the current requirements of the application, this is simple
    but in the future if deleteEntries, getAllEntries implemented their
    behaviour must be defined here
*/

public interface DBHandlerInterface {

    public boolean addEntry(int pillID, String action, String time);
}
