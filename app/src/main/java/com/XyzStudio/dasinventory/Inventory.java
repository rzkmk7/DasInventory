package com.XyzStudio.dasinventory;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {

    FloatingActionButton fab_add_inventory;
    FloatingActionButton fab_add_scan;
    ImageButton invHome;
    ImageButton delAllInv;
    ImageButton refInv;

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
        invHome = findViewById(R.id.invHome);
        delAllInv =  findViewById(R.id.delAllInv);
        refInv =  findViewById(R.id.refInv);
        fab_add_scan = findViewById(R.id.fab_add_scan);

        adapter = new AdapterInventory(this, inventoryArrayList, new AdapterInventory.OnClickListener() {
            @Override
            public void onClick(Integer msg) {
                Log.d("asd", inventoryArrayList.get(msg).getNamaBarang());
                FragItemInv fragItemInv = new FragItemInv(inventoryArrayList.get(msg).getNamaBarang(),inventoryArrayList.get(msg).getJmlStok(),inventoryArrayList.get(msg).getType(),inventoryArrayList.get(msg).getKet(),inventoryArrayList.get(msg).getKey());
                fragItemInv.show(getSupportFragmentManager(), "activity_frag_item_inventory");
            }
        }, new AdapterInventory.OnClickListenerDel(){
            @Override
            public void onClick(Integer msg){
                // Get key
//                Log.d("asd", inventoryArrayList.get(msg).getKey());
                // Get namaBarang
//                Log.d("asd", inventoryArrayList.get(msg).getJmlStok());
                int jmlStok = Integer.parseInt(inventoryArrayList.get(msg).getJmlStok());
                int total = jmlStok + 5;
                Log.d("asd", String.valueOf(jmlStok));
                Log.d("asd", String.valueOf(total));
            }
        });
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
        fab_add_scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scanCode();
            }
        });
        invHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inventory.this,Home.class));
            }
        });

       delAllInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("kliked","s");
                AlertDialog.Builder builder = new AlertDialog.Builder(Inventory.this);
                builder.setTitle("Do this action");
                builder.setMessage("delete semua data?");
                builder.setPositiveButton("YES", (dialog, which) -> {
                    // Do do my action here
                    ref.removeValue();
                    finish();
                    overridePendingTransition(1, 1);
                    startActivity(getIntent());
                    overridePendingTransition(1, 1);
                });

                builder.setNegativeButton("NO", (dialog, which) -> {
                    // I do not need any action here you might
                    dialog.dismiss();
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        refInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(1, 1);
                startActivity(getIntent());
                overridePendingTransition(1, 1);
            }
        });
        getData();
    }
    public void getData(){
        inventoryArrayList.clear();
        ref.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("tag",snapshot.getKey());
                InventoryData inventory = snapshot.getValue(InventoryData.class);
                inventory.setKey(snapshot.getKey());
                inventoryArrayList.add(inventory);
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

    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result->
    {
        if(result.getContents() !=null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Inventory.this);
            builder.setTitle("Result");
            try {
                JSONObject obj = new JSONObject(result.getContents());

                String namaBarang = obj.getString("namaBarang");
                String jmlStok = obj.getString("jmlStok");
                String ket = obj.getString("ket");
                String type = obj.getString("type");

                InventoryData data = new InventoryData(namaBarang, jmlStok, type, ket);

                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child("Inventory").push().setValue(data).addOnSuccessListener((new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    }
                }));

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            builder.setMessage(result.getContents());
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
//            {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i)
//                {
//                    dialogInterface.dismiss();
//                }
//            }).show();
        }
    });

}