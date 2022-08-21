package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {

    FloatingActionButton fab_add_inventory;

    ListView listView;
    AdapterInventory adapter;
    public static ArrayList<InventoryData> inventoryArrayList = new ArrayList<>();
    Context context;

    public FirebaseDatabase database;
    public DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Inventory");

        context = this;
        fab_add_inventory = findViewById(R.id.fab_add_inventory);

        adapter = new AdapterInventory(this, inventoryArrayList);
        adapter.arrayListInventory.clear();

        listView = findViewById(R.id.ListInventory);
        listView.setAdapter(adapter);


        fab_add_inventory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* startActivity(new Intent(Inventory.this,FormInventory.class));*/
                FormInventory formInventory = new FormInventory();
                formInventory.show(getSupportFragmentManager(), "activity_form_inventory");
            }
        });
        getData();
    }
    public void getData(){
        inventoryArrayList.clear();
        ref.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                InventoryData inventory = snapshot.getValue(InventoryData.class);
                inventoryArrayList.add(inventory);
                /*adapter.notifyDataSetChanged();*/ //teeuing

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