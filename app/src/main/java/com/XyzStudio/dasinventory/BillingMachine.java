package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BillingMachine extends AppCompatActivity {

    FloatingActionButton fab_add_billing;

    ListView listView;
    AdapterBilling adapter;
    public static ArrayList<BillingData> billingArrayList = new ArrayList<>();
    Context context;

    public FirebaseDatabase database;
    public DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_machine);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Billing");

        context = this;
        fab_add_billing = findViewById(R.id.fab_add_billing);

        adapter = new AdapterBilling(this, billingArrayList, new AdapterBilling.OnClickListener(){
            @Override
            public void onClick(Integer msg){
                Log.d("asd", billingArrayList.get(msg).getTempatBil());
//                FragBilling fragBilling = new FragBilling(billingArrayList.get(msg).getTempatBil(),billingArrayList.get(msg).getTypeBil(),billingArrayList.get(msg).getCounterAwalBil(),billingArrayList.get(msg).getCounterAkhirBil(),billingArrayList.get(msg).getTotalBiayaBil());
//                fragBilling.show(getSupportFragmentManager(), "activity_frag_bil");
            }
        });
        adapter.arrayListBilling.clear();

        listView = findViewById(R.id.ListBilling);
        listView.setAdapter(adapter);


        fab_add_billing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* startActivity(new Intent(Inventory.this,FormInventory.class));*/
                FormBilling formBilling = new FormBilling();
                formBilling.show(getSupportFragmentManager(), "activity_form_billing");
            }
        });
        getData();
    }
    public void getData(){
        billingArrayList.clear();
        ref.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("tag",snapshot.getKey());
                BillingData billing = snapshot.getValue(BillingData.class);
                billing.setKey(snapshot.getKey());
                billingArrayList.add(billing);
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