package com.example.drn.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Night_Medicines")
public class Night_Medicines {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "status")
    private int status;

    @ColumnInfo(name = "medicine")
    private String medicine;

    public Night_Medicines(int id, String medicine, int status){
        this.id = id;
        this.medicine = medicine;
        this.status = status;
    }

    @Ignore
    public Night_Medicines(String medicine, int status){
        this.medicine = medicine;
        this.status = status;
    }

    @Ignore
    public Night_Medicines(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}
