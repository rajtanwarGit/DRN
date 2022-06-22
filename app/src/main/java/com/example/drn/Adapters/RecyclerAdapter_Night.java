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
import com.example.drn.Models.Night_Medicines;
import com.example.drn.R;

import java.util.List;

public class RecyclerAdapter_Night extends RecyclerView.Adapter<RecyclerAdapter_Night.MyViewHolder> {

    private Context context;
    private List<Night_Medicines> nightList;
    private DatabaseHelper myDB;

    public RecyclerAdapter_Night(Context context, List<Night_Medicines> nightList){
        this.context = context;
        this.nightList = nightList;
    }

    @NonNull
    @Override
    public RecyclerAdapter_Night.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meds_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter_Night.MyViewHolder holder, int position) {
        myDB = DatabaseHelper.getDB(context);
        final Night_Medicines item = nightList.get(position);

        holder.mCheckBox.setText(item.getMedicine());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));

        if(holder.mCheckBox.isChecked())
            holder.mCheckBox.setPaintFlags(holder.mCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    myDB.dao_night().updateStatus_Night(new Night_Medicines(item.getId() , item.getMedicine(), 1));
                    holder.mCheckBox.setPaintFlags(holder.mCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    myDB.dao_night().updateStatus_Night(new Night_Medicines(item.getId() ,item.getMedicine(), 0));
                    holder.mCheckBox.setPaintFlags(holder.mCheckBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    @Override
    public int getItemCount() { return  this.nightList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(View view){
            super(view);
            mCheckBox = view.findViewById(R.id.mcheckbox);
        }
    }
}
