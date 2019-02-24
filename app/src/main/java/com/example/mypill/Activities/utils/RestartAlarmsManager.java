package com.example.mypill.Activities.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
    This class is responsible to receive an intent, after the device was restart
    so the alarm to continuing firing at the specific time
*/

public class RestartAlarmsManager extends BroadcastReceiver {
    private AlarmsManager alarmsManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Log.i("RestartAlarmManager", "Restart intent received. Setting the alarm");

            alarmsManager = new AlarmsManager();
            alarmsManager.setAlarm();
        } else {
            Log.e("RestartAlarmManager", "Received unexpected intent " + intent.toString());
        }
    }
}