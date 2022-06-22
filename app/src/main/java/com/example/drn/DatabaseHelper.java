package com.example.drn;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.drn.Daos.Dao_Evening;
import com.example.drn.Daos.Dao_Morning;
import com.example.drn.Daos.Dao_Night;
import com.example.drn.Models.Evening_Medicines;
import com.example.drn.Models.Morning_Medicines;
import com.example.drn.Models.Night_Medicines;

@Database(entities = {Morning_Medicines.class, Evening_Medicines.class, Night_Medicines.class}, exportSchema = false, version = 3)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "Medicine_DB";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getDB(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract Dao_Morning dao_morning();
    public abstract Dao_Evening dao_evening();
    public abstract Dao_Night dao_night();

}
