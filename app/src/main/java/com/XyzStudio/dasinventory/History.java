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
import android.widget.ImageButton;
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
    String invKey = "";
    public FirebaseDatabase database;
    public DatabaseReference ref;
    ImageButton hisHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_history);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            invKey = extras.getString("invKey");
            //The key argument here must match that used in the other activity
        }
        Log.d("testa", invKey);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Inventory");
        hisHome = findViewById(R.id.hisHome);

        adapter = new invAdapterHistory(this, historyDataArrayList, new invAdapterHistory.OnClickListener() {
//
//            /// button click untuk item list konsepnya adapter ngasih tau tap location dengan mListener ke dalam posisi click >lalu diterima oleh inventory dengan posisi integer msg
            @Override
           public void onClick(Integer msg) {
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
           }
       });

        adapter.arrayListHistory.clear();

        listView = findViewById(R.id.ListHistory);
        listView.setAdapter(adapter);

        hisHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(History.this,Home.class));
            }
        });
        getData();
    }
    public static ArrayList<HistoryData> historyDataArrayList = new ArrayList<>();


    public void getData(){
        historyDataArrayList.clear();
        ref.child(invKey).child("history").addChildEventListener(new ChildEventListener(){
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