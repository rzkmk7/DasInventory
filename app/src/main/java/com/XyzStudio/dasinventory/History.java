package com.XyzStudio.dasinventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.XyzStudio.dasinventory.databinding.ActivityHistoryBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHistoryBinding binding;

    invAdapterHistory adapter;
    ListView listView;
    Context context;

    public FirebaseDatabase database;
    public DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_history);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Inventory");


//        adapter = new invAdapterHistory(this, historyDataArrayList, new invAdapterHistory.OnClickListener() {
//
//            /// button click untuk item list konsepnya adapter ngasih tau tap location dengan mListener ke dalam posisi click >lalu diterima oleh inventory dengan posisi integer msg
//            /*@Override
//            public void onClick(Integer msg) {
////                Log.d("asd", inventoryArrayList.get(msg).getNamaBarang());
////                FragItemInv fragItemInv = new FragItemInv(
////                        inventoryArrayList.get(msg).getNamaBarang(),
////                        inventoryArrayList.get(msg).getJmlStok(),
////                        inventoryArrayList.get(msg).getType(),
////                        inventoryArrayList.get(msg).getKet(),
////                        inventoryArrayList.get(msg).getDate(),
////                        inventoryArrayList.get(msg).getStokAkhir(),
////                        inventoryArrayList.get(msg).getKey()                        );
////                fragItemInv.show(getSupportFragmentManager(), "activity_frag_item_inventory");
//            }*/
//        });
//        adapter.arrayListHistory.clear();

        listView = findViewById(R.id.ListHistory);
        listView.setAdapter(adapter);

        getData();
    }
    public static ArrayList<HistoryData> historyDataArrayList = new ArrayList<>();


    public void getData(){
        historyDataArrayList.clear();
        ref.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("tag",snapshot.getKey());
                HistoryData history = snapshot.getValue(HistoryData.class);
                history.setKey(snapshot.getKey());
                historyDataArrayList.add(history);
                adapter.notifyDataSetChanged(); //teeuingggg

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}