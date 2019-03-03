package com.example.mypill.Activities.mainScreen;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mypill.Activities.data.LocalDBHandler;
import com.example.mypill.Activities.utils.GlobalApplication;
import com.example.mypill.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class GraphFragment extends Fragment {

    public GraphFragment() { }

    public static GraphFragment newInstance() {
        return new GraphFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);

        // in this example, a LineChart is initialized from xml
        PieChart chart = (PieChart) view.findViewById(R.id.pieChart);

        List<PieEntry> entries = new ArrayList<>();

        // get the SQLiteOpenHelper class connecting to my DB
        LocalDBHandler handler = new LocalDBHandler(GlobalApplication.getAppContext());

        ArrayList<String[]> data = handler.getTotalEntries();

        PieEntry pieEntry = new PieEntry(Integer.parseInt(data.get(0)[1]), data.get(0)[0]);
        entries.add(pieEntry);

        PieEntry pieEntry2 = new PieEntry(Integer.parseInt(data.get(1)[1]), data.get(1)[0]);
        entries.add(pieEntry2);


        PieDataSet dataSet1 = new PieDataSet(entries , "myLabel1");
        dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet1.setValueTextColor(Color.BLUE);

        PieData pieData = new PieData(dataSet1);

        chart.setData(pieData);
        chart.animateY(500);

        chart.invalidate();

        return view;
    }
}
