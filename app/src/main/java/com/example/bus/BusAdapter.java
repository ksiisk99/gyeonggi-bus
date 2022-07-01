package com.example.bus;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {
    ArrayList<BusRouteInfo> list=null;

    interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener listener=null;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    @NonNull
    @Override
    public BusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bus_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusAdapter.ViewHolder holder, int position) {
        switch(list.get(position).getRouteTypeCd()){
            case "11":
            case "14":
            case "16":
            case "21":
                holder.bgColor.setBackgroundColor(Color.parseColor("#ff0000"));
                holder.routeName.setTextColor(Color.parseColor("#ff0000"));
                break;
            case "12":
            case "22":
                holder.bgColor.setBackgroundColor(Color.parseColor("#0000ff"));
                holder.routeName.setTextColor(Color.parseColor("#0000ff"));
                break;
            case "23":
            case "13":
            case "15":
                holder.bgColor.setBackgroundColor(Color.parseColor("#067240"));
                holder.routeName.setTextColor(Color.parseColor("#067240"));
                break;
            case "30":
                holder.bgColor.setBackgroundColor(Color.parseColor("#efd200"));
                holder.routeName.setTextColor(Color.parseColor("#efd200"));
                break;
            case "41":
            case "42":
            case "43":
                holder.bgColor.setBackgroundColor(Color.parseColor("#8400ff"));
                holder.routeName.setTextColor(Color.parseColor("#8400ff"));
                break;
            case "52":
            case "53":
                holder.bgColor.setBackgroundColor(Color.parseColor("#6d6d6d"));
                holder.routeName.setTextColor(Color.parseColor("#6d6d6d"));
                break;
        }
        holder.routeName.setText(list.get(position).getRouteName());
        holder.stationSE.setText(list.get(position).getStartStationName() +" -> "+list.get(position).getEndStationName());
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }else {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView routeName,stationSE;
        View bgColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            routeName=(TextView)itemView.findViewById(R.id.routeName);
            stationSE=(TextView)itemView.findViewById(R.id.stationSE);
            bgColor=(View)itemView.findViewById(R.id.bgColor);

            itemView.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        if(listener!=null){
                            listener.onItemClick(view,position);
                        }
                    }
                }
            });
        }
    }

    public void setList(ArrayList<BusRouteInfo> list){
        if(this.list!=null) {
            this.list.clear();
        }
        this.list=list;
        notifyDataSetChanged();
    }
}
