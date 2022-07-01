package com.example.bus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText stationEdit;
    FloatingActionButton btn;
    RecyclerView recyclerView;
    URL url;
    XmlPullParserFactory xmlPullParserFactory;
    XmlPullParser parser;
    InputStream is;
    ArrayList<StationInfo> list=new ArrayList<>();
    ProgressDialog progressDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment3 newInstance(String param1, String param2) {
        fragment3 fragment = new fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment3, container, false);
        progressDialog=new ProgressDialog(getActivity()); //로딩창 객체
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        stationEdit=view.findViewById(R.id.stationEdit);
        btn=view.findViewById(R.id.Btn);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        StationAdapter stationAdapter=new StationAdapter();
        recyclerView.setAdapter(stationAdapter);

        stationAdapter.setOnItemClickListener(new StationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),VisitStation.class);
                intent.putExtra("stationId",list.get(position).getStationId());
                intent.putExtra("stationName",list.get(position).getStationName());
                intent.putExtra("mobileNo",list.get(position).getMobileNo());
                intent.putExtra("regionName",list.get(position).getRegionName());
                startActivity(intent);
            }
        });

        stationEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.ACTION_DOWN){
                    progressDialog.show();
                    Thread th=new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                url=new URL(getString(R.string.frag3_url)+stationEdit.getText().toString());
                                xmlPullParserFactory=XmlPullParserFactory.newInstance();
                                parser=xmlPullParserFactory.newPullParser();
                                is=url.openStream();
                                parser.setInput(new InputStreamReader(is,"UTF-8"));
                                String tagName="";
                                int eventType=parser.getEventType();
                                String a=null,b=null,c=null,d=null;

                                while(eventType != XmlPullParser.END_DOCUMENT){
                                    switch(eventType){
                                        case XmlPullParser.START_TAG:
                                            tagName=parser.getName();
                                            if(tagName.equals("resultCode")){
                                                parser.next();
                                                if(parser.getText().equals("22")){
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            progressDialog.cancel();
                                                            Toast.makeText(getActivity(), "2자리 이상 입력", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    return;
                                                }else if(parser.getText().equals("4")){
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            progressDialog.cancel();
                                                            Toast.makeText(getActivity(), "결과 없음", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    return;
                                                }else if(!parser.getText().equals("0")){
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            progressDialog.cancel();
                                                            Toast.makeText(getActivity(), "시스템 오류", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    return;
                                                }
                                            }
                                            break;
                                        case XmlPullParser.END_TAG:
                                            if(parser.getName().equals("busStationList")){
                                                StationInfo stationInfo=new StationInfo(a,b,c,d);
                                                list.add(stationInfo);
                                            }
                                            if(parser.getName().equals("msgBody")){
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        progressDialog.cancel();
                                                        stationAdapter.setList(list);
                                                    }
                                                });
                                            }
                                            break;
                                        case XmlPullParser.TEXT:
                                            switch (tagName){
                                                case "mobileNo":
                                                    c=parser.getText();
                                                    break;
                                                case "regionName":
                                                    d=parser.getText();
                                                    break;
                                                case "stationId":
                                                    a=parser.getText();
                                                    break;
                                                case "stationName":
                                                    b=parser.getText();
                                                    break;
                                            }
                                            break;
                                    }
                                    eventType=parser.next();
                                }

                            } catch (MalformedURLException | XmlPullParserException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    th.start();
                    return true;
                }
                return false;
            }
        });

        btn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                progressDialog.show();
                Thread th=new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            url=new URL(getString(R.string.frag3_url2)+stationEdit.getText().toString());
                            xmlPullParserFactory=XmlPullParserFactory.newInstance();
                            parser=xmlPullParserFactory.newPullParser();
                            is=url.openStream();
                            parser.setInput(new InputStreamReader(is,"UTF-8"));
                            String tagName="";
                            int eventType=parser.getEventType();
                            String a=null,b=null,c=null,d=null;

                            while(eventType != XmlPullParser.END_DOCUMENT){
                                switch(eventType){
                                    case XmlPullParser.START_TAG:
                                        tagName=parser.getName();
                                        if(tagName.equals("resultCode")){
                                            parser.next();
                                            if(parser.getText().equals("22")){
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        progressDialog.cancel();
                                                        Toast.makeText(getActivity(), "2자리 이상 입력", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                return;
                                            }else if(parser.getText().equals("4")){
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        progressDialog.cancel();
                                                        Toast.makeText(getActivity(), "결과 없음", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                return;
                                            }else if(!parser.getText().equals("0")){
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        progressDialog.cancel();
                                                        Toast.makeText(getActivity(), "시스템 오류", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                return;
                                            }
                                        }
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if(parser.getName().equals("busStationList")){
                                            StationInfo stationInfo=new StationInfo(a,b,c,d);
                                            list.add(stationInfo);
                                        }
                                        if(parser.getName().equals("msgBody")){
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressDialog.cancel();
                                                    stationAdapter.setList(list);
                                                }
                                            });
                                        }
                                        break;
                                    case XmlPullParser.TEXT:
                                        switch (tagName){
                                            case "mobileNo":
                                                c=parser.getText();
                                                break;
                                            case "regionName":
                                                d=parser.getText();
                                                break;
                                            case "stationId":
                                                a=parser.getText();
                                                break;
                                            case "stationName":
                                                b=parser.getText();
                                                break;
                                        }
                                        break;
                                }
                                eventType=parser.next();
                            }

                        } catch (MalformedURLException | XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                th.start();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}