package com.example.drn.Daos;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.drn.Models.Morning_Medicines;

import java.util.List;

@androidx.room.Dao
public interface Dao_Morning {
    
    //Morning
    @Query("Select * from Morning_Medicines ORDER BY id DESC")
    List<Morning_Medicines> getAll_MorningMedicines();

    @Insert
    void addMedicine_Morning(Morning_Medicines medicine);

    @Update
    void updateMedicine_Morning(Morning_Medicines medicine);

    @Delete
    void deleteMedicine_Morning(Morning_Medicines medicine);

    @Update
    void updateStatus_Morning(Morning_Medicines medicine);

    @Query("Select COUNT(id) from Morning_Medicines WHERE status = 0")
    int getStatusMorning();

    @Query("Select COUNT(id) from Morning_Medicines")
    int countStatusMorning();

    @Query("Update Morning_Medicines SET status = 0")
    void resetStatusMorning();
}
