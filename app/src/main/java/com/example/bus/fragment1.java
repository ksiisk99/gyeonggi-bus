package com.example.bus;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView textView;
    RecyclerView recyclerView;
    MarkAdapter markAdapter;
    SingleMark singleMark;
    MarkDAO markDAO;
    RoomDB db;
    URL url;
    XmlPullParserFactory xmlPullParserFactory;
    XmlPullParser parser;
    InputStream is;
    ThreadGroup threadGroup=new ThreadGroup("HomeThread");
    boolean threadStop=false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment1 newInstance(String param1, String param2) {
        fragment1 fragment = new fragment1();
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
        View view=inflater.inflate(R.layout.fragment_fragment1, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        textView=view.findViewById(R.id.textView);
        singleMark=SingleMark.getInstance();
        markAdapter=new MarkAdapter();
        db=RoomDB.getInstance(getActivity());
        markDAO=db.markDAO();
        threadGroup.setDaemon(true);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);
        markAdapter.setOnItemClickListener(new MarkAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                markDAO.selectDelete(singleMark.list.get(position).getRouteId(),singleMark.list.get(position).getPosition());
                List<Integer> list_position=singleMark.hash.get(singleMark.list.get(position).getRouteId());
                list_position.remove(new Integer(singleMark.list.get(position).getPosition()));
                singleMark.hash.put(singleMark.list.get(position).getRouteId(),list_position);
                singleMark.list.remove(position);
                markAdapter.setList(singleMark.list,singleMark.list2);
                if(singleMark.list.isEmpty()){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onBGClick(View view, int position) {
                Intent intent=new Intent(getActivity(),BusRouteList.class);
                intent.putExtra("routeName",singleMark.list.get(position).getRouteName());
                intent.putExtra("routeId",singleMark.list.get(position).getRouteId());
                intent.putExtra("startStationName",singleMark.list.get(position).getStartStationName());
                intent.putExtra("endStationName",singleMark.list.get(position).getEndStationName());
                intent.putExtra("peekAlooc",singleMark.list.get(position).getPeekAlooc());
                intent.putExtra("nPeekAlooc",singleMark.list.get(position).getNpeekAlooc());
                intent.putExtra("upFirstTime",singleMark.list.get(position).getUpFirstTime());
                intent.putExtra("upLastTime",singleMark.list.get(position).getUpLastTime());
                intent.putExtra("downFirstTime",singleMark.list.get(position).getDownFirstTime());
                intent.putExtra("downLastTime",singleMark.list.get(position).getDownLastTime());
                intent.putExtra("routeTypeCd",singleMark.list.get(position).getRouteTypeCd());
                intent.putExtra("mark",true);
                intent.putExtra("mark_position",singleMark.list.get(position).getPosition());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(markAdapter);
        Toast.makeText(getActivity(),"20초마다 업데이트 됩니다.",Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        threadStop=false;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                singleMark.list=markDAO.getMarkList();
                if(singleMark.list!=null){
                    singleMark.list2=new ArrayList<>();
                    for(int i=0;i<singleMark.list.size();i++){
                        MarkData2 markData2=new MarkData2();
                        singleMark.list2.add(markData2);
                    }
                }
                if(!singleMark.list.isEmpty()){
                    textView.setVisibility(View.INVISIBLE);
                    Thread th=new Thread(threadGroup,"th"){
                        @Override
                        public void run() {
                            super.run();
                            while(true){
                                if(threadStop)return;
                                for(int i=0;i<singleMark.list.size();i++){
                                    int finalI = i;
                                    Thread th2=new Thread(threadGroup,String.valueOf(i)){
                                        @Override
                                        public void run() {
                                            super.run();
                                            int tmp= finalI;
                                            URL url;
                                            XmlPullParserFactory xmlPullParserFactory;
                                            XmlPullParser parser;
                                            InputStream is;

                                            try {
                                                url = new URL(getString(R.string.frag_url) +
                                                        singleMark.list.get(tmp).getStationId()+ "&routeId=" + singleMark.list.get(tmp).getRouteId() + "&staOrder=" + singleMark.list.get(tmp).getStationSeq());
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
                                                                if(parser.getText().equals("4")){
                                                                    singleMark.list2.get(tmp).setPredictTime1("도착 정보 없음");
                                                                    singleMark.list2.get(tmp).setPredictTime2("도착 정보 없음");
                                                                    break;
                                                                }else if(!parser.getText().equals("0")){

                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            singleMark.list2.get(tmp).setPredictTime1("시스템 오류");
                                                                            singleMark.list2.get(tmp).setPredictTime2("시스템 오류");
                                                                            Toast.makeText(getActivity(), "시스템 오류", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                            break;
                                                        case XmlPullParser.END_TAG:
                                                            if(parser.getName().equals("msgBody")){

                                                            }
                                                            break;
                                                        case XmlPullParser.TEXT:
                                                            switch(tagName){
                                                                case "predictTime1":
                                                                    singleMark.list2.get(tmp).setPredictTime1(parser.getText()+"분 후 도착");
                                                                    break;
                                                                case "predictTime2":
                                                                    singleMark.list2.get(tmp).setPredictTime2(parser.getText()+"분 후 도착");
                                                                    break;
                                                            }
                                                            break;
                                                    }
                                                    eventType=parser.next();
                                                }
                                            } catch (XmlPullParserException | IOException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    };
                                    th2.start();
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        markAdapter.setList(singleMark.list,singleMark.list2);
                                    }
                                });
                                try {
                                    Thread.sleep(20000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    th.start();
                }else{
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        threadStop=true;
    }
}