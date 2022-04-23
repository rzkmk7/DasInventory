package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Laporan extends AppCompatActivity {
    FloatingActionButton fab_add_laporan;

    ListView listView;
    AdapterLaporan adapter;
    public static ArrayList<LaporanData> laporanArrayList = new ArrayList<>();
    Context context;

    private FirebaseUser users;
    public FirebaseDatabase database;
    public DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        context = this;

        users = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        adapter = new AdapterLaporan(this, laporanArrayList);
        adapter.arrayListCustomer.clear();

        listView = findViewById(R.id.ListLaporan);
        listView.setAdapter(adapter);

        fab_add_laporan = findViewById(R.id.fab_add_laporan);

        fab_add_laporan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Laporan.this,FormLaporan.class));
            }
        });

        getData();
    }

    public void getData(){
        laporanArrayList.clear();
        ref.child(users.getUid()).child("Laporan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                LaporanData laporan = snapshot.getValue(LaporanData.class);
                laporanArrayList.add(laporan);
                adapter.notifyDataSetChanged();
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