package com.example.mypill.Activities.mainScreen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mypill.Activities.data.LocalDBHandler;
import com.example.mypill.Activities.utils.GlobalApplication;
import com.example.mypill.R;

public class HistoryFragment extends Fragment {
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "index";

    private String tabTitle;
    private int tabIndex;

    public HistoryFragment() { }

    public static HistoryFragment newInstance(String param1, int param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // get the SQLiteOpenHelper class connecting to my DB
        LocalDBHandler handler = new LocalDBHandler(GlobalApplication.getAppContext());
        // Get access to the underlying readable database as no editing will be allowed
        SQLiteDatabase db = handler.getReadableDatabase();
        // Query for items from the database and get a cursor back
        Cursor historyActionCursor = db.rawQuery("SELECT * FROM entries order by _id desc limit 10", null);

        // Find ListView to populate
        ListView actionHistoryListView = (ListView) view.findViewById(R.id.actionHistoryListView);
        // Setup cursor adapter using cursor from last step
        ActionsAdapter todoAdapter = new ActionsAdapter(GlobalApplication.getAppContext(), historyActionCursor);
        // Attach cursor adapter to the ListView
        actionHistoryListView.setAdapter(todoAdapter);

        return view;
    }
}
