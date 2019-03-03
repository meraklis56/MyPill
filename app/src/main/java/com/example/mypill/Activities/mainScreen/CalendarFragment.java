package com.example.mypill.Activities.mainScreen;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.mypill.Activities.data.DataHandler;
import com.example.mypill.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.Fragment;

public class CalendarFragment extends Fragment {

    private List<EventDay> events;
    private CalendarView calendarView;

    public CalendarFragment() { }

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        // Inflate the layout for this fragment

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        events = new ArrayList<>();

        addEventsFromDB();
        return view;
    }

    private void addEventsFromDB() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                new DataHandler().getEntries(30).forEach(entry -> {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("ss:mm:HH dd-MM-yyyy", Locale.ENGLISH);
                    try {
                        cal.setTime(sdf.parse(entry.getTime()));
                        if (entry.getAction().equals("forgotten")) {
                            events.add(new EventDay(cal, R.drawable.forget_action));
                        } else if (entry.getAction().equals("taken")) {
                            events.add(new EventDay(cal, R.drawable.intake_action));
                        }
                    } catch (Exception e) {
                        Log.e("DateParsing", e.getMessage());
                    }
                    calendarView.setEvents(events);
                });
            }
        });
    }
}
