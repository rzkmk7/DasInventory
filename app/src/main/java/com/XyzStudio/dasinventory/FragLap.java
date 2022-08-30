package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragLap extends DialogFragment {

    public FragLap(String tgl_laporan, String tempat_laporan, String type_mesin, String att, String key) {
        this.tgl_laporan = tgl_laporan;
        this.tempat_laporan = tempat_laporan;
        this.type_mesin = type_mesin;
        this.att = att;
        this.key = key;

    }
    private String tgl_laporan;
    private String tempat_laporan;
    private String type_mesin;
    private String att;
    private FirebaseUser users;
    private String key;

    private String uidInv;

//    public DatabaseReference invRef;
//    public FirebaseDatabase invDB;
//*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_frag_lap, container, false);

        users = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");

       TextView tgl_laporan = view.findViewById(R.id.ed_tgl_laporan);
        TextView tempat_laporan = view.findViewById(R.id.ed_tempat_laporan);
        TextView type_mesin = view.findViewById(R.id.ed_typeMesin);
        TextView att = view.findViewById(R.id.ed_att);


        Button btn_edSaveBil = view.findViewById(R.id.btn_edLaporanSave);

        tgl_laporan.setText(this.tgl_laporan);
        tempat_laporan.setText(this.tempat_laporan);
        type_mesin.setText(this.type_mesin);
        att.setText(this.att);

        /*inv = FirebaseAuth.getInstance().getCurrentUser();
        invRef = userDB.getInstance().getReference("Users");
        inv = users.getUid();*/


        btn_edSaveBil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempatLap = tempat_laporan.getText().toString();
                String typeLap = type_mesin.getText().toString();
                String attLap = att.getText().toString();
                String date = tgl_laporan.getText().toString();

//
                if (TextUtils.isEmpty(tempatLap)) {
                    input((EditText) tempat_laporan, "Tempat");
                } else if (TextUtils.isEmpty(typeLap)) {
                    input((EditText) type_mesin, "jmlStok");
                } else if (TextUtils.isEmpty(attLap)) {
                    input((EditText) att, "type");
                } else {
//


                    database.child(users.getUid()).child(key).setValue(new LaporanData(
                            date,
                            tempatLap,
                            typeLap,
                            attLap

                    )).addOnSuccessListener((new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dismiss();
                            ((BillingMachine)getActivity()).refresh();
                            Toast.makeText(view.getContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                        }
                    }));

                }

            }
        });

        return view;


    }

    private void input(EditText txt, String s) {
        txt.setError(s + "tidak boleh kosong");
        txt.requestFocus();
    }
}