package com.example.drn.Adapters;

import static android.widget.CompoundButton.*;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drn.DatabaseHelper;
import com.example.drn.Models.Morning_Medicines;
import com.example.drn.R;

import java.util.List;

public class RecyclerAdapter_Morning extends RecyclerView.Adapter<RecyclerAdapter_Morning.MyViewHolder> {

    private Context context;
    private List<Morning_Medicines> morningList;
    private DatabaseHelper myDB;

    public RecyclerAdapter_Morning(Context context, List<Morning_Medicines> morningList){
        this.context = context;
        this.morningList = morningList;
    }

    @NonNull
    @Override
    public RecyclerAdapter_Morning.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meds_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter_Morning.MyViewHolder holder, int position) {
        myDB = DatabaseHelper.getDB(context);
        final Morning_Medicines item = morningList.get(position);

        holder.mCheckBox.setText(item.getMedicine());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));

        if(holder.mCheckBox.isChecked())
            holder.mCheckBox.setPaintFlags(holder.mCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    myDB.dao_morning().updateStatus_Morning(new Morning_Medicines(item.getId() , item.getMedicine(), 1));
                    holder.mCheckBox.setPaintFlags(holder.mCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    myDB.dao_morning().updateStatus_Morning(new Morning_Medicines(item.getId() ,item.getMedicine(), 0));
                    holder.mCheckBox.setPaintFlags(holder.mCheckBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }

            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    @Override
    public int getItemCount() {
        return  this.morningList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(View view){
            super(view);
            mCheckBox = view.findViewById(R.id.mcheckbox);
        }
    }
}
