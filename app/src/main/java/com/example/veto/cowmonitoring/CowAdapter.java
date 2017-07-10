package com.example.veto.cowmonitoring;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YaRa on 7/10/2017.
 */

public class CowAdapter extends RecyclerView.Adapter<CowAdapter.MyViewHolder> {

    Context context;
    List<String> dataSet;
    View view ;



    public CowAdapter(Context context, List<String> data)
    {

        this.context = context;
        dataSet= data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_view,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.cowId.setText(dataSet.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"clicked",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context,Node.class);
                intent.putExtra("nodeKey", dataSet.get(position));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cowId;

        public MyViewHolder(View view) {
            super(view);
            cowId = (TextView) view.findViewById(R.id.cowId);
        }
    }
}
