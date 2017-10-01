package com.example.veto.cowmonitoring;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class TempreatureFragment extends Fragment implements View.OnClickListener {

    View  rootView;
    BarChart chart ;
    String nodeKey;
    Intent intent;
    ImageButton refreshButton;

    TextView textNodeKey ;


    public TempreatureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initializeViews(inflater,container);
        fillTempChart();

        return rootView;
    }

    void initializeViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_tempreature, container, false);
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

    void fillTempChart()
    {
        List<NodeData> data = getChartData();

        ArrayList<BarEntry> entries= new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        Log.d("data2", data.get(0).getTempData());

        for (int i = 0 ; i < data.size() ; i++)
        {
            //Log.d("data3", data.get(i).getTempData());
            float datafloat = Float.parseFloat(data.get(i).getTempData());

            Log.d("float", Float.toString(datafloat) );
            entries.add(new BarEntry(datafloat,i));
            labels.add(data.get(i).getTime());
        }

        MyBarDataSet barDataSet = new MyBarDataSet(entries,"درجة الحرارة") ;
        BarData barData = new BarData(labels,barDataSet) ;
        chart.setData(barData);

        barDataSet.setColors(new int[
                ]{ContextCompat.getColor(getContext() ,R.color.orange)
                ,ContextCompat.getColor(getContext() ,R.color.red),
                ContextCompat.getColor(getContext(),R.color.green)});

        chart.animateY(4000);
        chart.setDescription("");
    }

    @Override
    public void onClick(View v) {
        this.fillTempChart();
    }

    public class MyBarDataSet extends BarDataSet {


        public MyBarDataSet(List<BarEntry> yVals, String label) {
            super(yVals, label);
        }

        @Override
        public int getColor(int index) {
            if (getEntryForXIndex(index).getVal() < 38.5)
                return mColors.get(0);
            else if (getEntryForXIndex(index).getVal() > 39.5)
                return mColors.get(1);
            else
                return mColors.get(2);
        }

    }


}
