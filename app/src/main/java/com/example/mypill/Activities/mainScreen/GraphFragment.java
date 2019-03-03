package com.example.mypill.Activities.mainScreen;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarUtils;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.mypill.Activities.data.DataHandler;
import com.example.mypill.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.Fragment;

public class GraphFragment extends Fragment {
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "index";

    private String tabTitle;
    private int tabIndex;
    private List<EventDay> events;
    private CalendarView calendarView;

    public GraphFragment() { }

    public static GraphFragment newInstance(String param1, int param2) {
        GraphFragment fragment = new GraphFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tabTitle = getArguments().getString(ARG_PARAM1);
            tabIndex = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

        // Inflate the layout for this fragment
        TextView textView = (TextView) view.findViewById(R.id.testTextView);
        textView.setText("TAB: " + tabIndex);

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
