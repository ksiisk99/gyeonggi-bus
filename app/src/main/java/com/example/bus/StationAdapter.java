package com.example.bus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<StationInfo> list=null;

    interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener listener=null;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.station_item,parent,false);
        return new Station(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Station){
            ((Station) holder).stationName.setText(list.get(position).getStationName());
            ((Station) holder).mobileNo.setText(list.get(position).getMobileNo());
        }
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }else{
            return 0;
        }
    }


    public class Station extends RecyclerView.ViewHolder{
        TextView stationName,mobileNo;

        public Station(@NonNull View itemView) {
            super(itemView);
            stationName=itemView.findViewById(R.id.stationName);
            mobileNo=itemView.findViewById(R.id.mobileNo);

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
    public void setList(ArrayList<StationInfo> list){
        this.list=list;
        notifyDataSetChanged();
        /*if(list==null) {
            this.list = list;
        }else{
            this.list.clear();
            this.list=list;
        }*/
    }


}
