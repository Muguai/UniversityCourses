package com.example.GarbageBinTrack;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    List<GarbageBin> garbageBinList;
    Context context;

    public RecycleViewAdapter(List<GarbageBin> garbageBinList, Context context) {
        this.garbageBinList = garbageBinList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_garbage, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_garbageid.setText(garbageBinList.get(position).getName());
        holder.tv_garbagecapacity.setText(String.valueOf(garbageBinList.get(position).getPercent()) + "%");
        holder.tv_idNumber.setText("0" + (position + 1));
        holder.btn_info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoScreen.class);
                intent.putExtra("topic", garbageBinList.get(position).getTopic());
                context.startActivity(intent);

            }
        });

        holder.btn_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("id", garbageBinList.get(position).getId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return garbageBinList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_garbagepic;
        ImageButton btn_info1;
        ImageButton btn_gps;
        TextView tv_idName;
        TextView tv_CapacityName;
        TextView tv_idNumber;

        TextView tv_garbageid;
        TextView tv_garbagecapacity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_garbagepic = itemView.findViewById(R.id.iv_garbagepic);
            btn_info1 = itemView.findViewById(R.id.btn_info1);
            btn_gps = itemView.findViewById(R.id.btn_gps);
            tv_idName = itemView.findViewById(R.id.tv_idName);
            tv_CapacityName = itemView.findViewById(R.id.tv_CapacaityName);
            tv_idNumber = itemView.findViewById(R.id.tv_idNumber);

            tv_garbageid = itemView.findViewById(R.id.tv_garbageid);
            tv_garbagecapacity = itemView.findViewById(R.id.tv_garbagecapacity);

        }
    }
}