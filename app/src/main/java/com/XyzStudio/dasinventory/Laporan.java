package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Laporan extends AppCompatActivity {

    String tgl_laporan,tempat_laporan;

    public String uid;
    public FirebaseDatabase laporanDB ;
    public DatabaseReference laporanRef = laporanDB.getInstance().getReference("Users");
    public FirebaseUser userLaporan =  FirebaseAuth.getInstance().getCurrentUser();

    TextView et_tgl_laporan;
    TextView et_tempat_laporan;

    Button btn_laporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        uid = userLaporan.getUid();

        et_tgl_laporan = findViewById(R.id.et_tgl_laporan);
        et_tempat_laporan = findViewById(R.id.et_tempat_laporan);
        btn_laporan = findViewById(R.id.btn_laporan);
        et_tgl_laporan.setText(tgl_laporan);
        et_tempat_laporan.setText(tempat_laporan);

        btn_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tgl_laporan = et_tgl_laporan.getText().toString();
                String tempat_laporan = et_tempat_laporan.getText().toString();

                if (TextUtils.isEmpty(tgl_laporan)) {
                    input((EditText) et_tgl_laporan, "tgl");
                } else if (TextUtils.isEmpty(tempat_laporan)) {
                    input((EditText) et_tempat_laporan, "tempat");
                } else {
                    Log.d("ad",tempat_laporan);
                    laporanRef.child(uid).child("Laporan").push().setValue(new LaporanData(tgl_laporan, tempat_laporan)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(getApplicationContext(), "Data tersimpan", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("ad",tgl_laporan);
                            Toast.makeText(getApplicationContext(), "Data gagal tersimpan", Toast.LENGTH_SHORT).show();

                        }

                    });
                }
            }
        });
    }

    private void input(EditText txt, String s){
        {
            txt.setError(s+ "tidak boleh kosong");
            txt.requestFocus();
        }
    }
}