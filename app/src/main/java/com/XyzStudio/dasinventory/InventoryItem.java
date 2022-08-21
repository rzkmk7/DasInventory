package com.XyzStudio.dasinventory;

import static com.XyzStudio.dasinventory.FragItemInv.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class InventoryItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button ed_frag_item;

        ed_frag_item = findViewById(R.id.test123);

        ed_frag_item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("ttt","asdaasdasd");
                /* startActivity(new Intent(Inventory.this,FormInventory.class));*/
                FragItemInv fragItemInv = new FragItemInv();
                fragItemInv.show(getSupportFragmentManager(), "activity_frag_item_inventory");
            }
    });
    }
}