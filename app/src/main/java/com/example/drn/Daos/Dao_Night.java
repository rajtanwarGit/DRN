package com.example.drn.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.drn.Models.Night_Medicines;

import java.util.List;

@androidx.room.Dao
public interface Dao_Night {

    //Night
    @Query("Select * from Night_Medicines ORDER BY id DESC")
    List<Night_Medicines> getAll_NightMedicines();

    @Insert
    void addMedicine_Night(Night_Medicines medicine);

    @Update
    void updateMedicine_Night(Night_Medicines medicine);

    @Delete
    void deleteMedicine_Night(Night_Medicines medicine);

    @Update
    void updateStatus_Night(Night_Medicines medicine);

    @Query("Select COUNT(id) from Night_Medicines WHERE status = 0")
    int getStatusNight();

    @Query("Update Night_Medicines SET status = 0")
    void resetStatusNight();

}
