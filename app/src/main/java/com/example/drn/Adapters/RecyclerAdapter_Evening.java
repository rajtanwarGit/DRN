package com.example.drn.Adapters;

import static android.widget.CompoundButton.OnCheckedChangeListener;

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
import com.example.drn.Models.Evening_Medicines;
import com.example.drn.R;

import java.util.List;

public class RecyclerAdapter_Evening extends RecyclerView.Adapter<RecyclerAdapter_Evening.MyViewHolder> {

    private Context context;
    private List<Evening_Medicines> eveningList;
    private DatabaseHelper myDB;

    public RecyclerAdapter_Evening(Context context, List<Evening_Medicines> eveningList){
        this.context = context;
        this.eveningList = eveningList;
    }

    @NonNull
    @Override
    public RecyclerAdapter_Evening.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meds_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter_Evening.MyViewHolder holder, int position) {
        myDB = DatabaseHelper.getDB(context);
        final Evening_Medicines item = eveningList.get(position);

        holder.mCheckBox.setText(item.getMedicine());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));

        if(holder.mCheckBox.isChecked())
            holder.mCheckBox.setPaintFlags(holder.mCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    myDB.dao_evening().updateStatus_Evening(new Evening_Medicines(item.getId() , item.getMedicine(), 1));
                    holder.mCheckBox.setPaintFlags(holder.mCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    myDB.dao_evening().updateStatus_Evening(new Evening_Medicines(item.getId() ,item.getMedicine(), 0));
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
        return  this.eveningList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(View view){
            super(view);
            mCheckBox = view.findViewById(R.id.mcheckbox);
        }
    }
}
