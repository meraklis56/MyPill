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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

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
        chart.getDescription().setText("");
//        chart.getDescription().setTextSize(12);
//        chart.getDescription().setPosition(190f,40f);
        chart.getLegend().setEnabled(false);

        List<PieEntry> entries = new ArrayList<>();

        // get the SQLiteOpenHelper class connecting to my DB
        LocalDBHandler handler = new LocalDBHandler(GlobalApplication.getAppContext());

        ArrayList<String[]> data = handler.getTotalEntries();

        if (data.size() > 1) {
            PieEntry ingestedEntry = new PieEntry(Integer.parseInt(data.get(0)[1]), data.get(0)[0]);
            entries.add(ingestedEntry);

            PieEntry forgottenEntry = new PieEntry(Integer.parseInt(data.get(1)[1]), data.get(1)[0]);
            entries.add(forgottenEntry);
        }


        PieDataSet dataSet = new PieDataSet(entries , "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(18f);
        dataSet.setSliceSpace(8);

        // to remove any decimal points
        IValueFormatter formatter = new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format("%.0f", value);
            }

        };
        dataSet.setValueFormatter(formatter);

        PieData pieData = new PieData(dataSet);

        chart.setData(pieData);
        chart.animateY(500);

        chart.invalidate();

        return view;
    }
}
