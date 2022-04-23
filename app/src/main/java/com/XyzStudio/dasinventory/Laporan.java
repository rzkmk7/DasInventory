package com.XyzStudio.dasinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Laporan extends AppCompatActivity {
    FloatingActionButton fab_add_laporan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        fab_add_laporan = findViewById(R.id.fab_add_laporan);

        fab_add_laporan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Laporan.this,FormLaporan.class));
            }
        });
    }
}