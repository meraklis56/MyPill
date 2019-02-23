package com.example.mypill.Activities.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;
import android.icu.util.Calendar;
import android.util.Log;

public class AlarmsManager {

    private Context context;
    private AlarmManager alarmManager;
    private Intent intent;
    private PendingIntent pendingIntent;

    public AlarmsManager() {
        context = GlobalApplication.getAppContext();
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context.getApplicationContext(), MyBroadcastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast (context.getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public boolean setAlarm() {
        try {
            // Set the alarm to start at approximately 5:00 a.m.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 5);

            // For testing reasons this is repeated approximately every 10 seconds
            // Due to Android's doze settings, it could be more
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                    1000 * 10, pendingIntent);
            Log.i("AlarmsManager", "Alarm was set at 5 am");
            return true;
        } catch (Exception e) {
            Log.e("AlarmsManager", e.getMessage());
            return false;
        }
        // TODO make the alarm be set automatically on each reboot
    }

    public boolean cancelAlarm() {
        try {
            alarmManager.cancel(pendingIntent);
            System.out.println("ALARM CANCELLED");
            return true;
        } catch (Exception e) {
            Log.e("AlarmsManager", "AlarmManager update was not canceled. " + e.toString());
            return false;
        }
    }
}
