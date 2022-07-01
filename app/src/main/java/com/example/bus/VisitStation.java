package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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

public class VisitStation extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    String stationId,stationName;
    URL url,url2,url3;
    XmlPullParserFactory xmlPullParserFactory,xmlPullParserFactory2,xmlPullParserFactory3;
    XmlPullParser parser,parser2,parser3;
    InputStream is,is2,is3;
    ArrayList<VisitBusInfo> list;
    VisitStationAdapter visitStationAdapter;
    ProgressDialog progressDialog;
    TextView mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_station);

        progressDialog=new ProgressDialog(VisitStation.this); //로딩창 객체
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        recyclerView=findViewById(R.id.recyclerView);
        visitStationAdapter=new VisitStationAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(visitStationAdapter);
        mobileNo=findViewById(R.id.mobileNo);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        stationId=getIntent().getExtras().getString("stationId");
        stationName=getIntent().getExtras().getString("stationName");
        mobileNo.setText(getIntent().getExtras().getString("mobileNo"));
        toolbar.setTitle(stationName);

        list=new ArrayList<>();
        visitStationAdapter.setOnClickItemListener(new VisitStationAdapter.onClickItemListener() {
            @Override
            public void onClickItem(View view, int position) {
                Intent intent=new Intent(VisitStation.this,BusRouteList.class);
                Thread mThread=new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        String o=null,n=null,m=null,l=null,a=null,b=null,c=null,d=null,e=null,f=null,g=null,h=null,j=null,k=null;
                        try {
                            url3=new URL(getString(R.string.station_url)+list.get(position).getRouteId());
                            xmlPullParserFactory3=XmlPullParserFactory.newInstance();
                            parser3=xmlPullParserFactory3.newPullParser();
                            InputStream is=url3.openStream();
                            parser3.setInput(new InputStreamReader(is,"UTF-8"));
                            String tagName="";
                            int eventType=parser3.getEventType();

                            while(eventType != XmlPullParser.END_DOCUMENT){
                                switch(eventType){
                                    case XmlPullParser.START_TAG:
                                        tagName=parser3.getName();
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if(parser3.getName().equals("msgBody")){
                                            startActivity(intent);
                                            return;
                                        }
                                        break;
                                    case XmlPullParser.TEXT:
                                        switch(tagName){
                                            case "routeId":
                                                intent.putExtra("routeId",parser3.getText());
                                                break;
                                            case "routeName":
                                                intent.putExtra("routeName",parser3.getText());
                                                break;
                                            case "startStationId":
                                                intent.putExtra("startStationId",parser3.getText());
                                                break;
                                            case "startStationName":
                                                intent.putExtra("startStationName",parser3.getText());
                                                break;
                                            case "startMobileNo":
                                                intent.putExtra("startMobileNo",parser3.getText());
                                                break;
                                            case "endStationId":
                                                intent.putExtra("endStationId",parser3.getText());
                                                break;
                                            case "endStationName":
                                                intent.putExtra("endStationName",parser3.getText());
                                                break;
                                            case "endMobileNo":
                                                intent.putExtra("endMobileNo",parser3.getText());
                                                break;
                                            case "upFirstTime":
                                                intent.putExtra("upFirstTime",parser3.getText());
                                                break;
                                            case "upLastTime":
                                                intent.putExtra("upLastTime",parser3.getText());
                                                break;
                                            case "downFirstTime":
                                                intent.putExtra("downFirstTime",parser3.getText());
                                                break;
                                            case "downLastTime":
                                                intent.putExtra("downLastTime",parser3.getText());
                                                break;
                                            case "peekAlooc":
                                                intent.putExtra("peekAlooc",parser3.getText());
                                                break;
                                            case "nPeekAlooc":
                                                intent.putExtra("nPeekAlooc",parser3.getText());
                                                break;
                                            case "routeTypeCd":
                                                intent.putExtra("routeTypeCd",parser3.getText());
                                                break;
                                            default:
                                                break;
                                        }
                                        break;
                                }
                                eventType=parser3.next();
                            }
                        } catch (MalformedURLException | XmlPullParserException malformedURLException) {
                            malformedURLException.printStackTrace();
                        } catch (UnsupportedEncodingException unsupportedEncodingException) {
                            unsupportedEncodingException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                };
                mThread.start();

                /*try {
                    mThread.join();
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

            }
        });

        progressDialog.show();
        Thread th=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    url = new URL(getString(R.string.station_url2)+stationId);
                    xmlPullParserFactory=XmlPullParserFactory.newInstance();
                    parser=xmlPullParserFactory.newPullParser();
                    is=url.openStream();
                    parser.setInput(new InputStreamReader(is,"UTF-8"));

                    String tagName="";
                    int eventType=parser.getEventType();
                    String a=null,b=null,c=null,d=null,e=null;
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
                                                progressDialog.cancel();
                                                Toast.makeText(getApplicationContext(), "시스템 오류", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        return;
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if(parser.getName().equals("busRouteList")){
                                    VisitBusInfo visitBusInfo=new VisitBusInfo(a,b,c,d,e);
                                    list.add(visitBusInfo);
                                    int cnt=list.size()-1;
                                    url2 = new URL(getString(R.string.station_url3) +
                                            stationId+ "&routeId=" + b + "&staOrder=" + e);
                                    xmlPullParserFactory2=XmlPullParserFactory.newInstance();
                                    parser2=xmlPullParserFactory2.newPullParser();
                                    is2=url2.openStream();
                                    parser2.setInput(new InputStreamReader(is2,"UTF-8"));

                                    String tagName2="";
                                    int eventType2=parser2.getEventType();;

                                    while(eventType2 != XmlPullParser.END_DOCUMENT){
                                        switch(eventType2){
                                            case XmlPullParser.START_TAG:
                                                tagName2=parser2.getName();
                                                if(tagName2.equals("resultCode")){
                                                    parser2.next();
                                                    if(parser2.getText().equals("4")){
                                                        break;
                                                    }else if(!parser2.getText().equals("0")){
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(getApplicationContext(), "시스템 오류", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        return;
                                                    }
                                                }
                                                break;
                                            case XmlPullParser.END_TAG:
                                                if(parser2.getName().equals("busArrivalList")){

                                                }
                                                break;
                                            case XmlPullParser.TEXT:
                                                switch(tagName2){
                                                    case "predictTime1":
                                                        list.get(cnt).setPredictTime1(parser2.getText());
                                                        break;
                                                    case "predictTime2":
                                                        list.get(cnt).setPredictTime2(parser2.getText());
                                                        break;
                                                    case "stationId":
                                                        list.get(cnt).setStationId(parser2.getText());
                                                        break;
                                                }
                                                break;
                                        }
                                        eventType2=parser2.next();
                                    }
                                }
                                break;
                            case XmlPullParser.TEXT:
                                switch(tagName){
                                    case "regionName":
                                        a=parser.getText();
                                        break;
                                    case "routeId":
                                        b=parser.getText();
                                        break;
                                    case "routeName":
                                        c=parser.getText();
                                        break;
                                    case "routeTypeCd":
                                        d=parser.getText();
                                        break;
                                    case "staOrder":
                                        e=parser.getText();
                                        break;
                                }
                                break;
                        }
                        eventType=parser.next();
                    }
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                        visitStationAdapter.setList(list);
                    }
                });
            }
        };
        th.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.home:
                Intent intent=new Intent(VisitStation.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return false;
    }
}