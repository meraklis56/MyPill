package com.example.mypill.Activities.mainScreen;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.mypill.Activities.data.DataHandler;
import com.example.mypill.Activities.utils.AlarmsManager;
import com.example.mypill.R;

/*
    This class, when it is executed for the first time, it is responsible
    to setup the alarm and the notifications

    In general, it is responsible to present how many times the pill was
    received and not.
*/

public class MainActivity extends AppCompatActivity {

    private Button setAlarmButton, cancelAlarmButton;
    private AlarmsManager alarmsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmsManager = new AlarmsManager();
        alarmsManager.setMainAlarm();
        // Always set alarm.

        new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        new DataHandler().getEntries(5).forEach(entry -> {
//                            System.out.println(entry.toString());
                        });
                    }
                });

        // TODO implement tab view
        // TODO implement fragments
    }
}
