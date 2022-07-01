package com.example.bus;


import static androidx.core.content.ContextCompat.getSystemService;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ProgressDialog progressDialog;
    EditText routeId;
    FloatingActionButton btn;

    XmlPullParser parser,parser2;
    XmlPullParserFactory xmlPullParserFactory,xmlPullParserFactory2;
    URL url,url2;
    ArrayList<BusRoute> routeList;
    ArrayList<BusRouteInfo> routeInfoList;
    RecyclerView recyclerView;
    BusAdapter busAdapter;
    boolean check=false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment2 newInstance(String param1, String param2) {
        fragment2 fragment = new fragment2();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment2, container, false);

        progressDialog=new ProgressDialog(getActivity()); //로딩창 객체
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        routeId=view.findViewById(R.id.routeId);
        btn=view.findViewById(R.id.Btn);
        recyclerView=view.findViewById(R.id.recyclerView);
        routeList=new ArrayList<BusRoute>();
        routeInfoList=new ArrayList<BusRouteInfo>();
        busAdapter=new BusAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(busAdapter);
        busAdapter.setOnItemClickListener(new BusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(),BusRouteList.class);
                intent.putExtra("routeName",routeInfoList.get(position).routeName);
                intent.putExtra("routeId",routeInfoList.get(position).routeId);
                intent.putExtra("startStationName",routeInfoList.get(position).startStationName);
                intent.putExtra("endStationName",routeInfoList.get(position).endStationName);
                intent.putExtra("peekAlooc",routeInfoList.get(position).peekAlooc);
                intent.putExtra("nPeekAlooc",routeInfoList.get(position).nPeekAlooc);
                intent.putExtra("upFirstTime",routeInfoList.get(position).upFirstTime);
                intent.putExtra("upLastTime",routeInfoList.get(position).upLastTime);
                intent.putExtra("downFirstTime",routeInfoList.get(position).downFirstTime);
                intent.putExtra("downLastTime",routeInfoList.get(position).downLastTime);
                intent.putExtra("routeTypeCd",routeList.get(position).routetypecd);
                intent.putExtra("mark",false);
                startActivity(intent);
            }
        });

        routeId.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.ACTION_DOWN){
                    progressDialog.show();
                    findRoute();
                    return true;
                }
                return false;
            }
        });

        btn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                progressDialog.show();
                findRoute();
            }
        });
        return view;
    }

    private void findRoute(){
        Thread mThread=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String a=null,b=null,c=null;
                    url=new URL(getString(R.string.frag2_url)+routeId.getText().toString());
                    xmlPullParserFactory=XmlPullParserFactory.newInstance();
                    parser=xmlPullParserFactory.newPullParser();

                    InputStream is=url.openStream();
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
                                        //runOnUiThread 주자 ㅇㅋ?
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressDialog.cancel();
                                                Toast.makeText(getActivity(), "존재하지 않는 버스입니다.", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        });
                                        return;
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if(parser.getName().equals("busRouteList")){
                                    BusRoute route=new BusRoute();
                                    route.routeid=a;
                                    route.routename=b;
                                    route.routetypecd=c;
                                    routeList.add(route); //객체를 리스트에 추가 나중에 리사이클러뷰에 뿌려주자
                                }
                                if(parser.getName().equals("msgBody")){
                                    check=true;
                                }
                                break;
                            case XmlPullParser.TEXT:
                                switch(tagName){
                                    case "routeId":
                                        a=parser.getText();
                                        break;
                                    case "routeName":
                                        b=parser.getText();
                                        break;
                                    case "routeTypeCd":
                                        c=parser.getText();
                                        break;
                                }
                            default:
                                break;
                        }
                        eventType=parser.next();
                    }

                } catch (MalformedURLException | XmlPullParserException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread m2Thread=new Thread(){
            @Override
            public void run() {
                super.run();
                while(true) {
                    if (check) {
                        for (int i = 0; i < routeList.size(); i++) {
                            String o = null, n = null, m = null, l = null, a = null, b = null, c = null, d = null, e = null, f = null, g = null, h = null, j = null, k = null, p = null;
                            try {
                                url2 = new URL(getString(R.string.frag2_url2) + routeList.get(i).routeid);
                                xmlPullParserFactory2 = XmlPullParserFactory.newInstance();
                                parser2 = xmlPullParserFactory2.newPullParser();
                                InputStream is = url2.openStream();
                                parser2.setInput(new InputStreamReader(is, "UTF-8"));
                                String tagName = "";
                                int eventType = parser2.getEventType();

                                while (eventType != XmlPullParser.END_DOCUMENT) {
                                    switch (eventType) {
                                        case XmlPullParser.START_TAG:
                                            tagName = parser2.getName();
                                            /*if(!tagName.equals("busRouteInfoItem")){
                                                parser.nextTag();
                                                continue;
                                            }*/
                                            break;
                                        case XmlPullParser.END_TAG:
                                            if (parser2.getName().equals("busRouteInfoItem")) {
                                                BusRouteInfo routeInfo = new BusRouteInfo(a, o, b, c, d, e, f, g, h, j, k, l, m, n, p);
                                                routeInfoList.add(routeInfo);
                                            }
                                            break;
                                        case XmlPullParser.TEXT:
                                            switch (tagName) {
                                                case "routeId":
                                                    a = parser2.getText();
                                                    break;
                                                case "routeTypeCd":
                                                    p = parser2.getText();
                                                    break;
                                                case "routeName":
                                                    o = parser2.getText();
                                                    break;
                                                case "startStationId":
                                                    b = parser2.getText();
                                                    break;
                                                case "startStationName":
                                                    c = parser2.getText();
                                                    break;
                                                case "startMobileNo":
                                                    d = parser2.getText();
                                                    break;
                                                case "endStationId":
                                                    e = parser2.getText();
                                                    break;
                                                case "endStationName":
                                                    f = parser2.getText();
                                                    break;
                                                case "endMobileNo":
                                                    g = parser2.getText();
                                                    break;
                                                case "upFirstTime":
                                                    h = parser2.getText();
                                                    break;
                                                case "upLastTime":
                                                    j = parser2.getText();
                                                    break;
                                                case "downFirstTime":
                                                    k = parser2.getText();
                                                    break;
                                                case "downLastTime":
                                                    l = parser2.getText();
                                                    break;
                                                case "peekAlooc":
                                                    m = parser2.getText();
                                                    break;
                                                case "nPeekAlooc":
                                                    n = parser2.getText();
                                                    break;
                                                default:
                                                    break;
                                            }
                                            break;
                                    }
                                    eventType = parser2.next();
                                }
                            } catch (MalformedURLException | XmlPullParserException malformedURLException) {
                                malformedURLException.printStackTrace();
                            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                                unsupportedEncodingException.printStackTrace();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        progressDialog.cancel();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                busAdapter.setList(routeInfoList);
                            }
                        });
                        break;
                    }
                }
            }
        };
        mThread.start();
        m2Thread.start();
    }
}