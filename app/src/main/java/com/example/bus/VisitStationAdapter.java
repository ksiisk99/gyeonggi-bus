package com.example.bus;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VisitStationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<VisitBusInfo> list=null;

    public interface onClickItemListener{
        void onClickItem(View view, int position);
    }
    onClickItemListener listener=null;
    public void setOnClickItemListener(onClickItemListener listener){
        this.listener=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visit_station_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            switch(list.get(position).getRouteType()){
                case "11":
                case "14":
                case "16":
                case "21":
                    ((ViewHolder) holder).bgColor.setBackgroundColor(Color.parseColor("#ff0000"));
                    ((ViewHolder) holder).routeName.setTextColor(Color.parseColor("#ff0000"));
                    break;
                case "12":
                case "22":
                    ((ViewHolder) holder).bgColor.setBackgroundColor(Color.parseColor("#0000ff"));
                    ((ViewHolder) holder).routeName.setTextColor(Color.parseColor("#0000ff"));
                    break;
                case "23":
                case "13":
                case "15":
                    ((ViewHolder) holder).bgColor.setBackgroundColor(Color.parseColor("#067240"));
                    ((ViewHolder) holder).routeName.setTextColor(Color.parseColor("#067240"));
                    break;
                case "30":
                    ((ViewHolder) holder).bgColor.setBackgroundColor(Color.parseColor("#efd200"));
                    ((ViewHolder) holder).routeName.setTextColor(Color.parseColor("#efd200"));
                    break;
                case "41":
                case "42":
                case "43":
                    ((ViewHolder) holder).bgColor.setBackgroundColor(Color.parseColor("#8400ff"));
                    ((ViewHolder) holder).routeName.setTextColor(Color.parseColor("#8400ff"));
                    break;
                case "52":
                case "53":
                    ((ViewHolder) holder).bgColor.setBackgroundColor(Color.parseColor("#6d6d6d"));
                    ((ViewHolder) holder).routeName.setTextColor(Color.parseColor("#6d6d6d"));
                    break;
            }
            ((ViewHolder) holder).routeName.setText(list.get(position).getRouteName()+"번");
            if(list.get(position).getPredictTime1()!=null){
                ((ViewHolder) holder).predictTime1.setText(list.get(position).getPredictTime1()+"분 후");
                if(list.get(position).getPredictTime2()!=null){
                    ((ViewHolder) holder).predictTime2.setText(list.get(position).getPredictTime2()+"분 후");
                }else{
                    ((ViewHolder) holder).predictTime2.setText("도착 정보 없음");
                }
            }else{
                ((ViewHolder) holder).predictTime1.setText("도착 정보 없음");
                ((ViewHolder) holder).predictTime2.setText("도착 정보 없음");
            }
        }
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
        TextView routeName,predictTime1,predictTime2;
        View bgColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            routeName=itemView.findViewById(R.id.routeName);
            predictTime1=itemView.findViewById(R.id.predictTime1);
            predictTime2=itemView.findViewById(R.id.predictTime2);
            bgColor=itemView.findViewById(R.id.bgColor);
            itemView.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        if(listener!=null){
                            listener.onClickItem(view,position);
                        }
                    }
                }
            });
        }
    }

    public void setList(ArrayList<VisitBusInfo> list){
        this.list=list;
        notifyDataSetChanged();
    }
}
