package com.example.veto.cowmonitoring;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class SoundFragment extends Fragment implements View.OnClickListener {

    View  rootView;
    BarChart chart ;
    String nodeKey;
    Intent intent;
    ImageButton refreshButton;

    TextView textNodeKey ;


    public SoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeViews(inflater,container);

        fillSoundChart();

        return rootView;
    }

    void initializeViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_sound, container, false);
        intent = getActivity().getIntent();
        nodeKey = intent.getStringExtra("nodeKey");
        //textNodeKey = (TextView) rootView.findViewById(R.id.nodeKey);
        //textNodeKey.setText(nodeKey);
        chart = (BarChart) rootView.findViewById(R.id.chart) ;
        refreshButton = (ImageButton) rootView.findViewById(R.id.refresh);
        refreshButton.setOnClickListener(this);
    }

    List<NodeData> getChartData()
    {
        HttpGet httpGet = new HttpGet();
        String url = "getNodeData?nodeKey="+nodeKey;

        String dataString = "";

        try {
            dataString = httpGet.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<NodeData> data ;
        Gson gson = new Gson();

        data = gson.fromJson(dataString, new TypeToken<ArrayList<NodeData>>(){}.getType());

        return data;

    }

    void fillSoundChart()
    {
        List<NodeData> data = getChartData();

        ArrayList<BarEntry> entries= new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0 ; i < data.size() ; i++)
        {
            entries.add(new BarEntry(Float.parseFloat(data.get(i).getSoundData()),i));
            labels.add(data.get(i).getTime());
        }

        BarDataSet barDataSet = new BarDataSet(entries,"شدة الصوت") ;
        BarData barData = new BarData(labels,barDataSet) ;
        chart.setData(barData);

        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        chart.animateY(4000);
        chart.setDescription("");
    }


    @Override
    public void onClick(View v) {
        this.fillSoundChart();

    }
}
