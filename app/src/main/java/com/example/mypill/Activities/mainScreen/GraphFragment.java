package com.example.mypill.Activities.mainScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mypill.R;

import androidx.fragment.app.Fragment;

public class GraphFragment extends Fragment {
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "index";

    private String tabTitle;
    private int tabIndex;

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
        return view;
    }
}
