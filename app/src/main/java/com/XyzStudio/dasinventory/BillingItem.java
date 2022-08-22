package com.XyzStudio.dasinventory;

import static com.XyzStudio.dasinventory.FragItemInv.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class BillingItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button ed_frag_billing;
        ImageButton hitungBil;
        ed_frag_billing = findViewById(R.id.rr);
        hitungBil = findViewById(R.id.hitungBil);

        ed_frag_billing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("ttt","asdaasdasd");
                /* startActivity(new Intent(Inventory.this,FormInventory.class));*/
                FragBilling fragBilling = new FragBilling("","","","","");
                fragBilling.show(getSupportFragmentManager(), "activity_frag_billing");
            }
        });
        hitungBil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* startActivity(new Intent(Inventory.this,FormInventory.class));*/
                FormInventory formInventory = new FormInventory();
                formInventory.show(getSupportFragmentManager(), "activity_hitung_billing");
            }
        });

    }
}