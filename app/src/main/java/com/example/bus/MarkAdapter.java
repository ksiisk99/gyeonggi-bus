package com.example.bus;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<MarkData> list=null;
    List<MarkData2> list2=null;

    interface onItemClickListener{
        void onItemClick(View view, int position);
        void onBGClick(View view,int position);
    }
    private onItemClickListener listener=null;
    public void setOnItemClickListener(onItemClickListener listener){
        this.listener=listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mark_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            switch(list.get(position).getRouteTypeCd()){
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
           ((ViewHolder) holder).routeName.setText(list.get(position).getRouteName());
           ((ViewHolder) holder).stationName.setText(list.get(position).getStationName());
            if(list2.get(position).getPredictTime1()!=null){
                ((ViewHolder) holder).predictTime1.setText(list2.get(position).getPredictTime1());
                if(list2.get(position).getPredictTime2()!=null){
                    ((ViewHolder) holder).predictTime2.setText(list2.get(position).getPredictTime2());
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
        if(list!=null){
            return list.size();
        }else{
            return 0;
        }
    }

    public void setList(List<MarkData> list, List<MarkData2> list2){
        this.list=list;
        this.list2=list2;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView routeName,stationName,predictTime1,predictTime2;
        AppCompatButton deleteMark;
        View bgColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            routeName=itemView.findViewById(R.id.routeName);
            stationName=itemView.findViewById(R.id.stationName);
            predictTime1=itemView.findViewById(R.id.predictTime1);
            predictTime2=itemView.findViewById(R.id.predictTime2);
            deleteMark=itemView.findViewById(R.id.deleteMark);
            bgColor=itemView.findViewById(R.id.bgColor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        if(listener!=null){
                            listener.onBGClick(view,position);
                        }
                    }
                }
            });

            deleteMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
}
