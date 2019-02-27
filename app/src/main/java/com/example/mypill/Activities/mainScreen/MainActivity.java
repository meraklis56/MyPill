package com.example.mypill.Activities.mainScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.mypill.Activities.loginScreen.LoginActivity;
import com.example.mypill.Activities.utils.AlarmsManager;
import com.example.mypill.R;

import androidx.appcompat.app.AppCompatActivity;

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

//        new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        new DataHandler().getEntries(5).forEach(entry -> {
////                            System.out.println(entry.toString());
//                        });
//                    }
//                });

        // TODO implement tab view
        // TODO implement fragments
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logoutOption:
                Logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Logout() {
        SharedPreferences prefs = getSharedPreferences("mySharedPrefs", MODE_PRIVATE);
        prefs.edit().remove("loggedIn").apply();
        Toast.makeText(getBaseContext(), getString(R.string.logOutToast), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
