package com.example.bus;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MarkDAO {

    @Insert
    void insert(MarkData markData);

    @Query("DELETE FROM MarkBus WHERE id=:id")
    void delete(int id);

    @Query("DELETE FROM MarkBus")
    void allDelete();

    @Query("DELETE FROM MarkBus WHERE routeId=:routeId and position=:position")
    void selectDelete(String routeId,int position);

    @Query("SELECT * FROM MarkBus")
    List<MarkData> getMarkList();
}
