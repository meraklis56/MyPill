package com.example.mypill.Activities.data;

public class Entry {

    private int pillID;
    private String action, time;

    Entry (int pillID, String action, String time) {
        this.pillID = pillID;
        this.action = action;
        this.time = time;
    }

    public int getPillID() {
        return pillID;
    }

    public void setPillID(int pillID) {
        this.pillID = pillID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String toString() {
        return pillID + " - " + action + " - " + time;
    }
}
