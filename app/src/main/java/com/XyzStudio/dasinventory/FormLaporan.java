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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormLaporan extends AppCompatActivity {

 /*   public FormLaporan(String tgl_laporan, String tempat_laporan) {
        this.tgl_laporan = tgl_laporan;
        this.tempat_laporan = tempat_laporan;
    }
*/
    String tgl_laporan,tempat_laporan;

    public String uid;
    public FirebaseDatabase laporanDB ;
    public DatabaseReference laporanRef = laporanDB.getInstance().getReference("Users");
    public FirebaseUser userLaporan =  FirebaseAuth.getInstance().getCurrentUser();

    TextView et_tgl_laporan;
    TextView et_tempat_laporan;
    TextView type_mesin;
    TextView att;

    Button btn_laporanSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_laporan);
        uid = userLaporan.getUid();

      //  et_tgl_laporan = findViewById(R.id.et_tgl_laporan);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        Date c = Calendar.getInstance().getTime();
        String date = df.format(c);
        et_tempat_laporan = findViewById(R.id.et_tempat_laporan);
        type_mesin = findViewById(R.id.et_typeMesin);
        att = findViewById(R.id.et_att);

        btn_laporanSave = findViewById(R.id.btn_laporanSave);
        /*et_tgl_laporan.setText(tgl_laporan);
        et_tempat_laporan.setText(tempat_laporan);*/

        btn_laporanSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String tgl_laporan = et_tgl_laporan.getText().toString();
                String tempat_laporan = et_tempat_laporan.getText().toString();
                String type_mesinLap = type_mesin.getText().toString();
                String attLap = att.getText().toString();


                if (TextUtils.isEmpty(tempat_laporan)) {
                    input((EditText) et_tempat_laporan, "tempat");
                } else if (TextUtils.isEmpty(attLap)) {
                    input((EditText) att, "Att");
                } else {
                    Log.d("ad",tempat_laporan);
                    laporanRef.child(uid).child("Laporan").push().setValue(new LaporanData(date, tempat_laporan, type_mesinLap, attLap)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finish();
                            overridePendingTransition(1, 1);
                            startActivity(new Intent(FormLaporan.this,Laporan.class));
                            overridePendingTransition(1, 1);
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