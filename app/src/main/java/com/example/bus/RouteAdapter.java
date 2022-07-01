package com.example.bus;

import static com.example.bus.R.drawable.*;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<BusStationInfo> busList=null;
    private final static int START=0;
    private final static int END=2;
    private final static int BASIC=1;
    SingleMark singleMark=SingleMark.getInstance();
    int markCnt=singleMark.list.size();

    interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onMarkClick(View view,int position,boolean ck);
    }
    private RouteAdapter.OnItemClickListener listener=null;
    public void setOnItemClickListener(RouteAdapter.OnItemClickListener listener){
        this.listener=listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==START){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_station_start_item,parent,false);
            return new StartViewHolder(view);
        }else if(viewType==BASIC){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_station_item,parent,false);
            return new BasicViewHolder(view);
        }else if(viewType==END){
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_station_end_item,parent,false);
            return new EndViewHolder(view);
        }else{
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_station_center_item,parent,false);
            return new CenterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BasicViewHolder){
            ((BasicViewHolder) holder).textView.setText(busList.get(position).getStationName());
            ((BasicViewHolder) holder).mobileNo.setText(busList.get(position).getMobileNo());

            if(busList.get(position).getMark()){
                ((BasicViewHolder) holder).markBtn.setChecked(true);
                ((BasicViewHolder) holder).markBtn.setBackgroundResource(ic_baseline_star_24);
                ((BasicViewHolder) holder).markBG.setBackgroundColor(Color.parseColor("#6DF5EAC1"));
            }else{
                ((BasicViewHolder) holder).markBtn.setChecked(false);
                ((BasicViewHolder) holder).markBtn.setBackgroundResource(ic_baseline_star_border_24);
                ((BasicViewHolder) holder).markBG.setBackgroundColor(0);
            }
            if(busList.get(position).isBus()){
                ((BasicViewHolder) holder).busImage.setImageResource(ic_baseline_directions_bus_24);
                switch(busList.get(position).getRouteTypeCd()){
                    case "11":
                    case "14":
                    case "16":
                    case "21":
                        ((BasicViewHolder) holder).busImage.setColorFilter(Color.parseColor("#ff0000"));
                        break;
                    case "12":
                    case "22":
                        ((BasicViewHolder) holder).busImage.setColorFilter(Color.parseColor("#0000ff"));
                        break;
                    case "23":
                    case "13":
                    case "15":
                        ((BasicViewHolder) holder).busImage.setColorFilter(Color.parseColor("#067240"));
                        break;
                    case "30":
                        ((BasicViewHolder) holder).busImage.setColorFilter(Color.parseColor("#efd200"));
                        break;
                    case "41":
                    case "42":
                    case "43":
                        ((BasicViewHolder) holder).busImage.setColorFilter(Color.parseColor("#8400ff"));
                        break;
                    case "52":
                    case "53":
                        ((BasicViewHolder) holder).busImage.setColorFilter(Color.parseColor("#6d6d6d"));
                        break;
                }
                ((BasicViewHolder) holder).busImage.setVisibility(View.VISIBLE);
                ((BasicViewHolder) holder).busTime.setVisibility(View.VISIBLE);
                if(busList.get(position).getTime().equals("1")){
                    ((BasicViewHolder) holder).busTime.setText("잠시 후");
                }else{
                    ((BasicViewHolder) holder).busTime.setText(busList.get(position).getTime()+"분 후");
                }
                //차량번호
                //((BasicViewHolder) holder).busTime.setText(busList.get(position).getPlateNo());
            }else{
                ((BasicViewHolder) holder).busImage.setImageResource(0);
                ((BasicViewHolder) holder).busTime.setVisibility(View.INVISIBLE);
                ((BasicViewHolder) holder).busImage.setVisibility(View.INVISIBLE);
                //((BasicViewHolder) holder).busImage.setBackgroundColor(0);
            }
        }else if(holder instanceof StartViewHolder){
            ((StartViewHolder) holder).textView.setText(busList.get(position).getStationName());
            ((StartViewHolder) holder).mobileNo.setText(busList.get(position).getMobileNo());
            if(busList.get(position).getMark()){
                ((StartViewHolder) holder).markBtn.setChecked(true);
                ((StartViewHolder) holder).markBtn.setBackgroundResource(ic_baseline_star_24);
                ((StartViewHolder) holder).markBG.setBackgroundColor(Color.parseColor("#6DF5EAC1"));
            }else{
                ((StartViewHolder) holder).markBtn.setChecked(false);
                ((StartViewHolder) holder).markBtn.setBackgroundResource(ic_baseline_star_border_24);
                ((StartViewHolder) holder).markBG.setBackgroundColor(0);
            }
            if(busList.get(position).isBus()){
                ((StartViewHolder) holder).busImage.setImageResource(ic_baseline_directions_bus_24);
                switch(busList.get(position).getRouteTypeCd()){
                    case "11":
                    case "14":
                    case "16":
                    case "21":
                        ((StartViewHolder) holder).busImage.setColorFilter(Color.parseColor("#ff0000"));
                        break;
                    case "12":
                    case "22":
                        ((StartViewHolder) holder).busImage.setColorFilter(Color.parseColor("#0000ff"));
                        break;
                    case "23":
                    case "13":
                    case "15":
                        ((StartViewHolder) holder).busImage.setColorFilter(Color.parseColor("#067240"));
                        break;
                    case "30":
                        ((StartViewHolder) holder).busImage.setColorFilter(Color.parseColor("#efd200"));
                        break;
                    case "41":
                    case "42":
                    case "43":
                        ((StartViewHolder) holder).busImage.setColorFilter(Color.parseColor("#8400ff"));
                        break;
                    case "52":
                    case "53":
                        ((StartViewHolder) holder).busImage.setColorFilter(Color.parseColor("#6d6d6d"));
                        break;
                }
                ((StartViewHolder) holder).busImage.setVisibility(View.VISIBLE);
                ((StartViewHolder) holder).busTime.setVisibility(View.VISIBLE);
                if(busList.get(position).getTime().equals("1")){
                    ((StartViewHolder) holder).busTime.setText("잠시 후");
                }else{
                    ((StartViewHolder) holder).busTime.setText(busList.get(position).getTime()+"분 후");
                }
                //차량번호
                //((StartViewHolder) holder).busTime.setText(busList.get(position).getPlateNo());
            }else{
                ((StartViewHolder) holder).busImage.setImageResource(0);
                ((StartViewHolder) holder).busTime.setVisibility(View.INVISIBLE);
                ((StartViewHolder) holder).busImage.setVisibility(View.INVISIBLE);
            }
        }else if(holder instanceof EndViewHolder){
            ((EndViewHolder) holder).textView.setText(busList.get(position).getStationName());
        }else if(holder instanceof CenterViewHolder){
            ((CenterViewHolder) holder).textView.setText(busList.get(position).getStationName());
            ((CenterViewHolder) holder).mobileNo.setText(busList.get(position).getMobileNo());
            if(busList.get(position).getMark()){
                ((CenterViewHolder) holder).markBtn.setChecked(true);
                ((CenterViewHolder) holder).markBtn.setBackgroundResource(ic_baseline_star_24);
                ((CenterViewHolder) holder).markBG.setBackgroundColor(Color.parseColor("#6DF5EAC1"));
            }else{
                ((CenterViewHolder) holder).markBtn.setChecked(false);
                ((CenterViewHolder) holder).markBG.setBackgroundColor(0);
                ((CenterViewHolder) holder).markBtn.setBackgroundResource(ic_baseline_star_border_24);
            }
            if(busList.get(position).isBus()){
                ((CenterViewHolder) holder).busImage.setImageResource(ic_baseline_directions_bus_24);
                switch(busList.get(position).getRouteTypeCd()){
                    case "11":
                    case "14":
                    case "16":
                    case "21":
                        ((CenterViewHolder) holder).busImage.setColorFilter(Color.parseColor("#ff0000"));
                        break;
                    case "12":
                    case "22":
                        ((CenterViewHolder) holder).busImage.setColorFilter(Color.parseColor("#0000ff"));
                        break;
                    case "23":
                    case "13":
                    case "15":
                        ((CenterViewHolder) holder).busImage.setColorFilter(Color.parseColor("#067240"));
                        break;
                    case "30":
                        ((CenterViewHolder) holder).busImage.setColorFilter(Color.parseColor("#efd200"));
                        break;
                    case "41":
                    case "42":
                    case "43":
                        ((CenterViewHolder) holder).busImage.setColorFilter(Color.parseColor("#8400ff"));
                        break;
                    case "52":
                    case "53":
                        ((CenterViewHolder) holder).busImage.setColorFilter(Color.parseColor("#6d6d6d"));
                        break;
                }
                ((CenterViewHolder) holder).busImage.setVisibility(View.VISIBLE);
                ((CenterViewHolder) holder).busTime.setVisibility(View.VISIBLE);
                if(busList.get(position).getTime().equals("1")){
                    ((CenterViewHolder) holder).busTime.setText("잠시 후");
                }else{
                    ((CenterViewHolder) holder).busTime.setText(busList.get(position).getTime()+"분 후");
                }
                //차량번호
                //((CenterViewHolder) holder).busTime.setText(busList.get(position).getPlateNo());
            }else{
                ((CenterViewHolder) holder).busImage.setImageResource(0);
                ((CenterViewHolder) holder).busTime.setVisibility(View.INVISIBLE);
                ((CenterViewHolder) holder).busImage.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(busList!=null){
            return busList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return busList.get(position).getViewType();
    }


    public class StartViewHolder extends RecyclerView.ViewHolder{
        TextView textView,busTime,mobileNo;
        ImageView busImage;
        LinearLayout markBG;
        AppCompatToggleButton markBtn;
        public StartViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.stationName);
            busImage=itemView.findViewById(R.id.busImage);
            busTime=itemView.findViewById(R.id.busTime);
            mobileNo=itemView.findViewById(R.id.mobileNo);
            markBtn=itemView.findViewById(R.id.markBtn);
            markBG=itemView.findViewById(R.id.markBG);

            markBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        if (listener!=null){
                            if(markBtn.isChecked()) {
                                if (markCnt < 6) {
                                    listener.onMarkClick(view, position, markBtn.isChecked());
                                    markBtn.setBackgroundResource(R.drawable.ic_baseline_star_24);
                                    markCnt++;
                                } else {
                                    markBtn.setChecked(false);
                                    listener.onMarkClick(view, -1, markBtn.isChecked());
                                }
                            }else{
                                listener.onMarkClick(view, position, false);
                                markBtn.setBackgroundResource(R.drawable.ic_baseline_star_border_24);
                                markCnt--;
                            }
                            notifyItemChanged(position);
                        }
                    }
                }
            });

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

    public class EndViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView busImage;
        public EndViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.stationName);
            busImage=itemView.findViewById(R.id.busImage);
        }
    }

    public class CenterViewHolder extends RecyclerView.ViewHolder{
        TextView textView,busTime,mobileNo;
        ImageView busImage;
        AppCompatToggleButton markBtn;
        LinearLayout markBG;
        public CenterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.stationName);
            busImage=itemView.findViewById(R.id.busImage);
            busTime=itemView.findViewById(R.id.busTime);
            mobileNo=itemView.findViewById(R.id.mobileNo);
            markBtn=itemView.findViewById(R.id.markBtn);
            markBG=itemView.findViewById(R.id.markBG);

            markBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        if (listener!=null){
                            if(markBtn.isChecked()) {
                                if (markCnt < 6) {
                                    listener.onMarkClick(view, position, markBtn.isChecked());
                                    markBtn.setBackgroundResource(R.drawable.ic_baseline_star_24);
                                    markCnt++;
                                } else {
                                    markBtn.setChecked(false);
                                    listener.onMarkClick(view, -1, markBtn.isChecked());
                                }
                            }else{
                                listener.onMarkClick(view, position, false);
                                markBtn.setBackgroundResource(R.drawable.ic_baseline_star_border_24);
                                markCnt--;
                            }
                            notifyItemChanged(position);
                        }
                    }
                }
            });

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

    public class BasicViewHolder extends RecyclerView.ViewHolder{
        TextView textView,busTime,mobileNo;
        ImageView busImage;
        AppCompatToggleButton markBtn;
        LinearLayout markBG;

        public BasicViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.stationName);
            busImage=itemView.findViewById(R.id.busImage);
            busTime=itemView.findViewById(R.id.busTime);
            mobileNo=itemView.findViewById(R.id.mobileNo);
            markBtn=itemView.findViewById(R.id.markBtn);
            markBG=itemView.findViewById(R.id.markBG);

             markBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        if (listener!=null){
                            if(markBtn.isChecked()) {
                                if (markCnt < 6) {
                                    listener.onMarkClick(view, position, markBtn.isChecked());
                                    markBtn.setBackgroundResource(R.drawable.ic_baseline_star_24);
                                    markCnt++;
                                } else {
                                    markBtn.setChecked(false);
                                    listener.onMarkClick(view, -1, markBtn.isChecked());
                                }
                            }else{
                                listener.onMarkClick(view, position, false);
                                markBtn.setBackgroundResource(R.drawable.ic_baseline_star_border_24);
                                markCnt--;
                            }
                            notifyItemChanged(position);
                        }
                        //busList.get(position).setMark(markBtn.isChecked());
                    }
                }
            });

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

    public void setBusList(ArrayList<BusStationInfo> busList){
        this.busList=busList;
        notifyDataSetChanged();
    }
}
