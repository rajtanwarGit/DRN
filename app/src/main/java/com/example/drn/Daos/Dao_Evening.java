package com.example.drn.Daos;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.drn.Models.Evening_Medicines;

import java.util.List;

@androidx.room.Dao
public interface Dao_Evening {

    //Evening
    @Query("Select * from Evening_Medicines ORDER BY id DESC")
    List<Evening_Medicines> getAll_EveningMedicines();

    @Insert
    void addMedicine_Evening(Evening_Medicines medicine);

    @Update
    void updateMedicine_Evening(Evening_Medicines medicine);

    @Delete
    void deleteMedicine_Evening(Evening_Medicines medicine);

    @Update
    void updateStatus_Evening(Evening_Medicines medicine);

    @Query("Select COUNT(id) from Evening_Medicines WHERE status = 0")
    int getStatusEvening();

    @Query("Update Evening_Medicines SET status = 0")
    void resetStatusEvening();

}
