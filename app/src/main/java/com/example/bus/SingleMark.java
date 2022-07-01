package com.example.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SingleMark {
    private static SingleMark instance=null;
    List<MarkData> list;
    List<MarkData2> list2;
    HashMap<String,List<Integer>> hash=new HashMap<>();

    public static SingleMark getInstance(){
        if(instance==null){
            instance=new SingleMark();
        }
        return instance;
    }

}
