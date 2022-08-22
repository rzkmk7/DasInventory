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

        ed_frag_billing = findViewById(R.id.test123);

        ed_frag_billing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("ttt","asdaasdasd");
                /* startActivity(new Intent(Inventory.this,FormInventory.class));*/
                FragBilling fragBilling = new FragBilling("","","","","");
                fragBilling.show(getSupportFragmentManager(), "activity_frag_billing");
            }
        });
    }
}