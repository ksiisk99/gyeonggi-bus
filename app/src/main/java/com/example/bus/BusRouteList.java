package com.example.bus;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.appbar.AppBarLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BusRouteList extends AppCompatActivity {
    String routeId, routeName, startStationName,endStationName,upFirstTime,upLastTime,downFirstTime,downLastTime,peekAlooc,nPeekAlooc,routeTypeCd;
    ArrayList<BusStationInfo> busList;
    ArrayList<BusLocationInfo> locationList;
    XmlPullParserFactory xmlPullParserFactory, xmlPullParserFactory2, xmlPullParserFactory3,xmlPullParserFactory4;
    URL url,url2,url3,url4;
    XmlPullParser parser, parser2, parser3,parser4;
    InputStream is,is2, is3,is4;
    RecyclerView recyclerView;
    RouteAdapter adapter;
    Toolbar toolbar;
    TextView busCount;
    Button startStationBtn,endStationBtn;
    int turnNum=0,mark_position;
    AppBarLayout appBarLayout;
    boolean check=false,check2=false,mark=false,threadStop=false;
    RoomDB db=null;
    MarkDAO markDAO;
    SingleMark singleMark;
    List<Integer> list_position;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route_list);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog=new ProgressDialog(BusRouteList.this); //로딩창 객체
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        Intent intent=getIntent();
        routeId=intent.getExtras().getString("routeId");
        routeName=intent.getExtras().getString("routeName");
        startStationName=intent.getExtras().getString("startStationName");
        endStationName=intent.getExtras().getString("endStationName");
        upFirstTime= intent.getExtras().getString("upFirstTime");
        upLastTime=intent.getExtras().getString("upLastTime");
        downFirstTime=intent.getExtras().getString("downFirstTime");
        downLastTime=intent.getExtras().getString("downLastTime");
        peekAlooc=intent.getExtras().getString("peekAlooc");
        nPeekAlooc=intent.getExtras().getString("nPeekAlooc");
        routeTypeCd=intent.getExtras().getString("routeTypeCd");
        mark=intent.getExtras().getBoolean("mark");
        mark_position=intent.getExtras().getInt("mark_position");

        db= RoomDB.getInstance(this);
        markDAO=db.markDAO();
        singleMark=SingleMark.getInstance();

        if(singleMark.hash.containsKey(routeId)){ //즐찾된 정류소가 있다면
            list_position=singleMark.hash.get(routeId);
        }else{
            list_position=new ArrayList<>();
        }

        toolbar.setTitle(routeName);
        recyclerView=findViewById(R.id.recyclerView);
        startStationBtn=findViewById(R.id.startStationBtn);
        endStationBtn=findViewById(R.id.endStationBtn);
        busCount=findViewById(R.id.busCount);
        appBarLayout=findViewById(R.id.appBarLayout);

        startStationBtn.setText(startStationName+"\n"+upFirstTime+"~"+upLastTime);
        endStationBtn.setText(endStationName+"\n"+downFirstTime+"~"+downLastTime);

        switch(routeTypeCd){
            case "11":
            case "14":
            case "16":
            case "21":
                appBarLayout.setBackgroundColor(Color.parseColor("#ff0000"));
                toolbar.setBackgroundColor(Color.parseColor("#ff0000"));
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    getWindow().setStatusBarColor(Color.parseColor("#ff0000"));
                }
                break;
            case "12":
            case "22":
                appBarLayout.setBackgroundColor(Color.parseColor("#0000ff"));
                toolbar.setBackgroundColor(Color.parseColor("#0000ff"));
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    getWindow().setStatusBarColor(Color.parseColor("#0000ff"));
                }
                break;
            case "23":
            case "13":
            case "15":
                appBarLayout.setBackgroundColor(Color.parseColor("#067240"));
                toolbar.setBackgroundColor(Color.parseColor("#067240"));
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    getWindow().setStatusBarColor(Color.parseColor("#067240"));
                }
                break;
            case "30":
                appBarLayout.setBackgroundColor(Color.parseColor("#efd200"));
                toolbar.setBackgroundColor(Color.parseColor("#efd200"));
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    getWindow().setStatusBarColor(Color.parseColor("#efd200"));
                }
                break;
            case "41":
            case "42":
            case "43":
                appBarLayout.setBackgroundColor(Color.parseColor("#8400ff"));
                toolbar.setBackgroundColor(Color.parseColor("#8400ff"));
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    getWindow().setStatusBarColor(Color.parseColor("#8400ff"));
                }
                break;
            case "52":
            case "53":
                appBarLayout.setBackgroundColor(Color.parseColor("#6d6d6d"));
                toolbar.setBackgroundColor(Color.parseColor("#6d6d6d"));
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    getWindow().setStatusBarColor(Color.parseColor("#6d6d6d"));
                }
                break;
        }

        adapter=new RouteAdapter();
        adapter.setOnItemClickListener(new RouteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(BusRouteList.this,VisitStation.class);
                intent.putExtra("stationId",busList.get(position).getStationId());
                intent.putExtra("stationName",busList.get(position).getStationName());
                intent.putExtra("mobileNo",busList.get(position).getMobileNo());
                startActivity(intent);
            }

            @Override
            public void onMarkClick(View view, int position, boolean ck) {
                if(position==-1){
                    Toast.makeText(getApplicationContext(),"6개까지 등록가능",Toast.LENGTH_SHORT).show();
                }else {
                    busList.get(position).setMark(ck);
                    if (ck) {
                        MarkData markData = new MarkData(position, routeName, routeId, startStationName
                                , endStationName, peekAlooc, nPeekAlooc, upFirstTime, upLastTime, downFirstTime, downLastTime, routeTypeCd, busList.get(position).getStationId()
                                , busList.get(position).getStationName(), busList.get(position).getStationSeq());
                        markDAO.insert(markData);
                        busList.get(position).setMark(true);
                        list_position.add((Integer) position);
                        singleMark.hash.put(routeId, list_position);
                    } else {
                        markDAO.selectDelete(routeId, position);
                        busList.get(position).setMark(false);
                        list_position.remove(new Integer(position));
                        singleMark.hash.put(routeId, list_position);
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        busList=new ArrayList<>();
        locationList=new ArrayList<>();

        startStationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.scrollToPosition(0);
            }
        });
        endStationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layoutManager.findLastVisibleItemPosition()>=turnNum){
                    recyclerView.scrollToPosition(turnNum-3);
                }else{
                    recyclerView.scrollToPosition(turnNum+3);
                }
            }
        });


        Thread th=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String a=null,b=null,c=null,d=null,e=null,f=null,g=null;
                    url=new URL(getString(R.string.buslist_url)+routeId);
                    xmlPullParserFactory=XmlPullParserFactory.newInstance();
                    parser=xmlPullParserFactory.newPullParser();
                    is=url.openStream();
                    parser.setInput(new InputStreamReader(is,"UTF-8"));
                    String tagName="";
                    int eventType=parser.getEventType();

                    while(eventType != XmlPullParser.END_DOCUMENT){
                        switch(eventType){
                            case XmlPullParser.START_TAG:
                                tagName=parser.getName();
                                if(tagName.equals("resultCode")){
                                    parser.next();
                                    if(!parser.getText().equals("0")){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "시스템 오류", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        });
                                        return;
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if(parser.getName().equals("busRouteStationList")){
                                    BusStationInfo busStationInfo=new BusStationInfo(a,b,c,d,e,f,g);
                                    if(f.equals("N")) {
                                        busStationInfo.setViewType(1);
                                    }else{
                                        busStationInfo.setViewType(3);
                                        turnNum=Integer.parseInt(e);
                                    }
                                    busStationInfo.setRouteTypeCd(routeTypeCd);
                                    busList.add(busStationInfo);
                                }
                                if(parser.getName().equals("msgBody")){
                                    busList.get(0).setViewType(0);
                                    busList.get(busList.size()-1).setViewType(2);
                                    if(!list_position.isEmpty()){
                                        for(int i=0;i<list_position.size();i++){
                                            busList.get((int)list_position.get(i)).setMark(true);
                                        }
                                    }
                                    check=true;
                                }
                                break;
                            case XmlPullParser.TEXT:
                                switch(tagName){
                                    case "stationId":
                                        a=parser.getText();
                                        break;
                                    case "stationName":
                                        b=parser.getText();
                                        break;
                                    case "mobileNo":
                                        g=parser.getText();
                                        break;
                                    case "x":
                                        c=parser.getText();
                                        break;
                                    case "y":
                                        d=parser.getText();
                                        break;
                                    case "stationSeq":
                                        e=parser.getText();
                                        break;
                                    case "turnYn":
                                        f=parser.getText();
                                }
                        }
                        eventType=parser.next();
                    }

                } catch (XmlPullParserException | MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        th.start();
        Toast.makeText(getApplicationContext(),"20초마다 업데이트 됩니다.",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        threadStop=false;
        updateBus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        threadStop=true;
    }

    private void updateBus(){
        Thread th2=new Thread(){
            @Override
            public void run() {
                super.run();

                while(true){
                    if(threadStop)break;
                    if(check){
                        //updateBus();
                        try {
                            url2=new URL(getString(R.string.buslist_url2)+routeId);
                            xmlPullParserFactory2=XmlPullParserFactory.newInstance();
                            parser2=xmlPullParserFactory2.newPullParser();
                            is2=url2.openStream();
                            parser2.setInput(new InputStreamReader(is2,"UTF-8"));

                            String tagName="";
                            String a=null,b=null,c=null;
                            int eventType=parser2.getEventType();

                            while(eventType != XmlPullParser.END_DOCUMENT){
                                switch(eventType){
                                    case XmlPullParser.START_TAG:
                                        tagName=parser2.getName();
                                        if(tagName.equals("resultCode")){
                                            parser2.next();
                                            if(parser2.getText().equals("4")){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        busCount.setText(String.valueOf(locationList.size())+"대 운행중");
                                                        if(!check2) {
                                                            startStationBtn.setEnabled(true);
                                                            endStationBtn.setEnabled(true);
                                                            check2 = true;
                                                            progressDialog.cancel();
                                                            adapter.setBusList(busList);
                                                            if (mark) {
                                                                recyclerView.smoothScrollToPosition(mark_position + 3);
                                                                mark = false;
                                                            }
                                                        }else {
                                                            adapter.setBusList(busList);
                                                        }

                                                    }
                                                });
                                                return;
                                            }else if(!parser2.getText().equals("0")){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if(!check2) {
                                                            progressDialog.cancel();
                                                        }
                                                        Toast.makeText(getApplicationContext(), "시스템 오류", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                });
                                                return;
                                            }
                                        }
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if(parser2.getName().equals("busLocationList")){
                                            BusLocationInfo busLocationInfo=new BusLocationInfo(a,b);
                                            locationList.add(busLocationInfo);
                                            busList.get(Integer.parseInt(b) - 1).setBus(true);
                                            int SIZE=busList.size();
                                            if (Integer.parseInt(b) < SIZE) {
                                                String q = null;
                                                try {
                                                    url3 = new URL(getString(R.string.buslist_url3) +
                                                            busList.get(Integer.parseInt(b)).getStationId() + "&routeId=" + routeId + "&staOrder=" + String.valueOf(Integer.parseInt(b)+1));
                                                    xmlPullParserFactory3 = XmlPullParserFactory.newInstance();
                                                    parser3 = xmlPullParserFactory3.newPullParser();
                                                    is3 = url3.openStream();
                                                    parser3.setInput(new InputStreamReader(is3, "UTF-8"));
                                                    String tagName2 = "";
                                                    int eventType2 = parser3.getEventType();
                                                    boolean over=false;

                                                    while (eventType2 != XmlPullParser.END_DOCUMENT) {
                                                        if(over)break;

                                                        switch (eventType2) {
                                                            case XmlPullParser.START_TAG:
                                                                tagName2 = parser3.getName();
                                                                if (tagName2.equals("resultCode")) {
                                                                    parser3.next();
                                                                    if (!parser3.getText().equals("0")) {
                                                                        over=true;

                                                                        busList.get(Integer.parseInt(b) - 1).setBus(false);
                                                                        busList.get(Integer.parseInt(b)).setBus(true);
                                                                        BusLocationInfo busLocationInfo2=new BusLocationInfo(busList.get(Integer.parseInt(b)).getStationId(),String.valueOf(Integer.parseInt(b)+1));
                                                                        locationList.add(busLocationInfo2);

                                                                        locationList.remove(locationList.size()-1);
                                                                        url4=new URL(getString(R.string.buslist_url4) +
                                                                                busList.get(Integer.parseInt(b)+1).getStationId() + "&routeId=" + routeId + "&staOrder=" + String.valueOf(Integer.parseInt(b)+2));
                                                                        xmlPullParserFactory4=XmlPullParserFactory.newInstance();
                                                                        parser4=xmlPullParserFactory4.newPullParser();
                                                                        is4=url4.openStream();
                                                                        parser4.setInput(new InputStreamReader(is4,"UTF-8"));
                                                                        String tagName3="";
                                                                        int eventType3=parser4.getEventType();
                                                                        String t="0";

                                                                        while(eventType3!=XmlPullParser.END_DOCUMENT){
                                                                            switch (eventType3){
                                                                                case XmlPullParser.START_TAG:
                                                                                    tagName3=parser4.getName();
                                                                                    if(tagName3.equals("resultCode")){
                                                                                        parser4.next();
                                                                                        if(!parser4.getText().equals("0")){
                                                                                            runOnUiThread(new Runnable() {
                                                                                                @Override
                                                                                                public void run() {
                                                                                                    Toast.makeText(getApplicationContext(), "시스템 오류4", Toast.LENGTH_SHORT).show();
                                                                                                    return;
                                                                                                }
                                                                                            });
                                                                                            return;
                                                                                        }
                                                                                    }
                                                                                    break;
                                                                                case XmlPullParser.END_TAG:
                                                                                    if (parser4.getName().equals("busArrivalItem")) {
                                                                                        busList.get(Integer.parseInt(b)).setTime(t);
                                                                                        busList.get(Integer.parseInt(b)-1).setPlateNo(c);
                                                                                    }
                                                                                    break;
                                                                                case XmlPullParser.TEXT:
                                                                                    switch (tagName3) {
                                                                                        case "predictTime1":
                                                                                            t = parser4.getText();
                                                                                            break;
                                                                                    }
                                                                                    break;
                                                                            }
                                                                        }
                                                                        runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Toast.makeText(getApplicationContext(), "시스템 오류", Toast.LENGTH_SHORT).show();
                                                                                return;
                                                                            }
                                                                        });
                                                                        return;
                                                                    }
                                                                }
                                                                break;
                                                            case XmlPullParser.END_TAG:
                                                                if (parser3.getName().equals("busArrivalItem")) {
                                                                    busList.get(Integer.parseInt(b)-1).setTime(q);
                                                                    busList.get(Integer.parseInt(b)-1).setPlateNo(c);
                                                                }
                                                                break;
                                                            case XmlPullParser.TEXT:
                                                                switch (tagName2) {
                                                                    case "predictTime1":
                                                                        q = parser3.getText();
                                                                        break;
                                                                }
                                                                break;
                                                        }
                                                        eventType2 = parser3.next();
                                                    }
                                                } catch (MalformedURLException e) {
                                                    e.printStackTrace();
                                                } catch (UnsupportedEncodingException e) {
                                                    e.printStackTrace();
                                                } catch (XmlPullParserException e) {
                                                    e.printStackTrace();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }
                                        break;
                                    case XmlPullParser.TEXT:
                                        switch(tagName){
                                            case "stationId":
                                                a=parser2.getText();
                                                break;
                                            case "stationSeq":
                                                b=parser2.getText();
                                                break;
                                            case "plateNo":
                                                c=parser2.getText();
                                        }
                                }
                                eventType=parser2.next();
                            }
                        } catch (MalformedURLException | XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                busCount.setText(String.valueOf(locationList.size())+"대 운행중");
                                if(!check2){
                                    startStationBtn.setEnabled(true);
                                    endStationBtn.setEnabled(true);
                                    progressDialog.cancel();
                                    adapter.setBusList(busList);
                                    check2=true;
                                    if(mark){
                                        recyclerView.smoothScrollToPosition(mark_position+3);
                                        mark=false;
                                    }
                                }else{
                                    adapter.setBusList(busList);
                                }
                            }
                        });
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        locationList.clear();
                        for(int i=0;i<busList.size();i++){
                            busList.get(i).setBus(false);
                            busList.get(i).setTime("0");
                        }

                    }
                }
            }
        };
        th2.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.home:
                Intent intent=new Intent(BusRouteList.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RoomDB.destroyInstance();
    }


}