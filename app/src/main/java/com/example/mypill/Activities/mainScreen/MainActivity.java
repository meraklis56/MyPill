package com.example.mypill.Activities.mainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.mypill.Activities.loginScreen.LoginActivity;
import com.example.mypill.Activities.utils.AlarmsManager;
import com.example.mypill.Activities.utils.AuthenticationHandler;
import com.example.mypill.R;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

/*
    This class, when it is executed for the first time, it is responsible
    to setup the alarm and the notifications

    In general, it is responsible to present how many times the pill was
    received and not.
*/

public class MainActivity extends AppCompatActivity {

    private AlarmsManager alarmsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmsManager = new AlarmsManager();
        alarmsManager.setMainAlarm();
        // Always set alarm

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        SampleAdapter sampleAdapter = new SampleAdapter(getSupportFragmentManager());

        viewPager.setAdapter(sampleAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("TAB SELECTED");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutOption:
                Logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Logout() {
        if (AuthenticationHandler.getInstance().Logout()) {
            Toast.makeText(getApplicationContext(), getString(R.string.logOutCorrectToast), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.logOutFalseToast), Toast.LENGTH_SHORT).show();
        }
    }

}
