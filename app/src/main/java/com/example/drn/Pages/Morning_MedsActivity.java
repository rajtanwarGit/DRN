package com.example.drn.Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drn.Adapters.RecyclerAdapter_Morning;
import com.example.drn.DatabaseHelper;
import com.example.drn.Models.Morning_Medicines;
import com.example.drn.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Morning_MedsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private TextView title;
    private DatabaseHelper databaseHelper;
    private RecyclerAdapter_Morning adapter;
    private ArrayList<Morning_Medicines> arrayList;
    private String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meds);

        title = findViewById(R.id.title);
        str = getIntent().getStringExtra("message_key");
        title.setText(str);

        mRecyclerview = findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager((new LinearLayoutManager(this)));

        fab = findViewById(R.id.fab);
        databaseHelper = DatabaseHelper.getDB(Morning_MedsActivity.this);

        arrayList = (ArrayList<Morning_Medicines>) databaseHelper.dao_morning().getAll_MorningMedicines();

        adapter = new RecyclerAdapter_Morning(this, arrayList);
        mRecyclerview.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Morning_MedsActivity.this);
                dialog.setContentView(R.layout.add_newmedicine);
                EditText editText = dialog.findViewById(R.id.edittext);
                Button save = dialog.findViewById(R.id.button_save);

                save.setOnClickListener(new View.OnClickListener() {
                    private String med = "";
                    @Override
                    public void onClick(View view) {
                        DatabaseHelper databaseHelper = DatabaseHelper.getDB(Morning_MedsActivity.this);
                        if (!editText.getText().toString().equals("")){
                            med = editText.getText().toString();
                            databaseHelper.dao_morning().addMedicine_Morning(
                                    new Morning_Medicines(editText.getText().toString(),0)
                            );

                            arrayList = (ArrayList<Morning_Medicines>) databaseHelper.dao_morning().getAll_MorningMedicines();
                            adapter = new RecyclerAdapter_Morning(Morning_MedsActivity.this, arrayList);
                            mRecyclerview.setAdapter(adapter);

                        }
                        else{
                            Toast.makeText(Morning_MedsActivity.this, "Please Enter a Medicine", Toast.LENGTH_SHORT).show();
                        }

                        dialog.dismiss();
                        Toast.makeText(Morning_MedsActivity.this, "Added: " + med, Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerview);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
            ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            final Morning_Medicines item = arrayList.get(position);

            switch (direction){
                case ItemTouchHelper.LEFT:
                    //DELETE
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    databaseHelper.dao_morning().deleteMedicine_Morning(new Morning_Medicines(item.getId()));
                                    arrayList.remove(position);
                                    adapter.notifyItemRemoved(position);

                                    //Animate the Recycler view Item on Deletion
                                    mRecyclerview.getViewTreeObserver().addOnPreDrawListener(
                                            new ViewTreeObserver.OnPreDrawListener() {

                                                @Override
                                                public boolean onPreDraw() {
                                                    mRecyclerview.getViewTreeObserver().removeOnPreDrawListener(this);

                                                    for (int i = position; i < mRecyclerview.getChildCount(); i++) {
                                                        View v = mRecyclerview.getChildAt(i);
                                                        v.setAlpha(0.0f);
                                                        v.animate().alpha(1.0f)
                                                                .setDuration(300)
                                                                .setStartDelay(i * 50)
                                                                .start();
                                                    }

                                                    return true;
                                                }
                                            });

                                    Toast.makeText(Morning_MedsActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(Morning_MedsActivity.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Delete", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                    break;

                case ItemTouchHelper.RIGHT:
                    //EDIT
                    Dialog dialog_update = new Dialog(Morning_MedsActivity.this);
                    dialog_update.setContentView(R.layout.add_newmedicine);
                    EditText editText = dialog_update.findViewById(R.id.edittext);
                    Button save = dialog_update.findViewById(R.id.button_save);

                    editText.setText(String.valueOf(item.getMedicine()));
                    save.setText("Update");

                    save.setOnClickListener(new View.OnClickListener() {
                        private String med = "";
                        @Override
                        public void onClick(View view) {
                            DatabaseHelper databaseHelper = DatabaseHelper.getDB(Morning_MedsActivity.this);
                            if (!editText.getText().toString().equals("")){
                                med = editText.getText().toString();
                                databaseHelper.dao_morning().updateMedicine_Morning(
                                        new Morning_Medicines(item.getId(), editText.getText().toString(), item.getStatus())
                                );
                                arrayList = (ArrayList<Morning_Medicines>) databaseHelper.dao_morning().getAll_MorningMedicines();
                                adapter = new RecyclerAdapter_Morning(Morning_MedsActivity.this, arrayList);
                                mRecyclerview.setAdapter(adapter);

                                Toast.makeText(Morning_MedsActivity.this, "Updated: " + med, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Morning_MedsActivity.this, "Please Enter a Medicine", Toast.LENGTH_SHORT).show();
                            }

                            dialog_update.dismiss();
                        }
                    });
                    adapter.notifyDataSetChanged();
                    dialog_update.show();

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(Morning_MedsActivity.this, R.color.colorRed))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(Morning_MedsActivity.this, R.color.colorGreen))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_edit)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

}