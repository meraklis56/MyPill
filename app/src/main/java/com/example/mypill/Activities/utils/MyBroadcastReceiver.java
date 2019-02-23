package com.example.mypill.Activities.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

    /*
    This class is responsible to receive the intent from AlarmsManager Service
    */

    public MyBroadcastReceiver() { }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("BroadcastReceiver");
        CreateNotificationIntentService.enqueueWork(context, new Intent());
    }
}