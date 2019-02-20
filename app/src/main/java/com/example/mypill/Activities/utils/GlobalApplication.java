package com.example.mypill.Activities.utils;

import android.app.Application;
import android.content.Context;

/*
    This singleton class is responsible to give any crucial element of Android to
    a simple java class. For example appContext
*/

public class GlobalApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}