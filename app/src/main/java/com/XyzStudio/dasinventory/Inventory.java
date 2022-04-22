package com.XyzStudio.dasinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Inventory extends AppCompatActivity {
    FloatingActionButton fab_add_inventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        fab_add_inventory = findViewById(R.id.fab_add_inventory);

        fab_add_inventory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Inventory.this,FormInventory.class));
            }
        });
    }
}