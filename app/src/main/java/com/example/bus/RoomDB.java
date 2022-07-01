package com.example.bus;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MarkData.class},version = 2,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database=null;
    private static String DB_NAME="BUS";

    public synchronized static RoomDB getInstance(Context context){
        if(database==null){
            database= Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class,DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract MarkDAO markDAO();

    public static void destroyInstance(){
        database=null;
    }
}
