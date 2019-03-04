package com.example.mypill.Activities.mainScreen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mypill.Activities.data.LocalDBHandler;
import com.example.mypill.Activities.utils.GlobalApplication;
import com.example.mypill.R;

public class ListFragment extends Fragment {

    public ListFragment() { }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // get the SQLiteOpenHelper class connecting to my DB
        LocalDBHandler handler = new LocalDBHandler(GlobalApplication.getAppContext());
        // Get access to the underlying readable database as no editing will be allowed
        SQLiteDatabase db = handler.getReadableDatabase();
        // Query for items from the database and get a cursor back
        Cursor historyActionCursor = db.rawQuery("SELECT * FROM entries WHERE action!=\"snooze\" order by _id desc limit 10", null);

        // Find recycleView to populate
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalApplication.getAppContext()));

        // specify an adapter
        ActionsRecycleViewAdapter mAdapter = new ActionsRecycleViewAdapter(GlobalApplication.getAppContext(), historyActionCursor);
        recyclerView.setAdapter(mAdapter);

        return view;
    }
}
