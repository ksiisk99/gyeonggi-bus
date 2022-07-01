package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private final int[] tabElements2={R.drawable.ic_baseline_home_24,R.drawable.ic_baseline_directions_bus_24,
            R.drawable.ic_baseline_station_24};
    RoomDB db;
    MarkDAO markDAO;
    SingleMark singleMark;
    HashMap<String,String> hash=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db= RoomDB.getInstance(this);
        markDAO=db.markDAO();
        singleMark=SingleMark.getInstance();
        singleMark.list=markDAO.getMarkList();
        if(singleMark.list!=null){
            singleMark.list2=new ArrayList<>();
        }
        for(int i=0;i<singleMark.list.size();i++){
            MarkData2 markData2=new MarkData2();
            singleMark.list2.add(markData2);
            List<Integer> list_position;
            if(singleMark.hash.containsKey(singleMark.list.get(i).getRouteId())){ //즐찾된 정류소가 있다면
                list_position=singleMark.hash.get(singleMark.list.get(i).getRouteId());
                list_position.add((Integer) singleMark.list.get(i).getPosition());
            }else{ //즐찾이 없다면
                list_position=new ArrayList<>();
                list_position.add((Integer)singleMark.list.get(i).getPosition());
            }
            singleMark.hash.put(singleMark.list.get(i).getRouteId(),list_position);
        }

        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(fragment1.newInstance("a","b"));
        fragments.add(fragment2.newInstance("aa","bb"));
        fragments.add(fragment3.newInstance("aaa","bbb"));

        viewPager2=(ViewPager2) findViewById(R.id.viewPager);

        ViewPager2Adapter viewPager2Adapter=new ViewPager2Adapter(this,fragments);
        viewPager2.setAdapter(viewPager2Adapter);
        tabLayout=findViewById(R.id.tab_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#FF6200EE"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#7E000000"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setIcon(tabElements2[position]);
            }
        }).attach();


    }
}