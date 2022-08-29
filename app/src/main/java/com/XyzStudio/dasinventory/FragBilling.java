package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class FragBilling extends DialogFragment {
    public FragBilling(String tempatBil
            ,String typeBil
            , String snBil
            , int biaya
            , int counterAwalBil
            , int counterAkhirBil
            , int totalBiayaBil
            , String key) {
        this.typeBil = typeBil;
        this.snBil = snBil;
        this.biaya = biaya;
        this.counterAwalBil = counterAwalBil;
        this.counterAkhirBil = counterAkhirBil;
        this.totalBiayaBil = totalBiayaBil;
        this.tempatBil =tempatBil;
        this.key = key;
    }

    private String tempatBil ;
    private String snBil ;
    private String typeBil ;
    private int biaya ;
    private int counterAwalBil;
    private int counterAkhirBil;
    private int totalBiayaBil;
    private String key;

    private String uidInv;

//    public DatabaseReference invRef;
//    public FirebaseDatabase invDB;
//*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.activity_frag_billing, container, false);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        TextView ed_tempatBil  = view.findViewById(R.id.ed_tempatBil );
        TextView ed_typeBil  = view.findViewById(R.id.ed_typeBil );
        TextView ed_snBil  = view.findViewById(R.id.ed_snBil );
        TextView ed_counterAwalBil  = view.findViewById(R.id.ed_counterAwalBil );
        TextView ed_biayaBil  = view.findViewById(R.id.ed_biayaBil );
        TextView ed_counterAkhirBil  = view.findViewById(R.id.ed_counterAkhirBil );
        TextView ed_totalBiayaBil  = view.findViewById(R.id.ed_totalBiayaBil );

        Button btn_edSaveBil = view.findViewById(R.id.btn_edSaveBil );
        Button btn_edHitungBil = view.findViewById(R.id.btn_edHitungBil );

        ed_tempatBil.setText(this.tempatBil );
        ed_snBil.setText(this.snBil );
        ed_typeBil.setText(this.typeBil );
        ed_biayaBil.setText(this.biaya );
        ed_counterAwalBil.setText(this.counterAwalBil );
        ed_counterAkhirBil.setText(this.counterAkhirBil );
        ed_totalBiayaBil.setText(this.totalBiayaBil );

        /*inv = FirebaseAuth.getInstance().getCurrentUser();
        invRef = userDB.getInstance().getReference("Users");
        inv = users.getUid();*/

        btn_edSaveBil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempatBil = ed_tempatBil .getText().toString();
                String snBil = ed_snBil .getText().toString();
                String typeBil = ed_typeBil .getText().toString();
                int biaya = Integer.parseInt(ed_biayaBil.getText().toString());
                int counterAwalBil = Integer.parseInt(ed_counterAwalBil.getText().toString());
                int counterAkhirBil = Integer.parseInt(ed_counterAkhirBil.getText().toString());
                int totalBiayaBil = Integer.parseInt(ed_totalBiayaBil.getText().toString());
//
                if(TextUtils.isEmpty(tempatBil)){
                    input((EditText) ed_tempatBil , "Tempat" );
                }else if (TextUtils.isEmpty(snBil)){
                    input((EditText) ed_snBil , "jmlStok" );
                }else if (TextUtils.isEmpty(typeBil)){
                    input((EditText) ed_typeBil , "type" );
                }else {
//


                    database.child("Billing").child(key).setValue(new BillingData(String tempatBil
                            ,String typeBil
                            , String snBil
                            , int biaya
                            , int counterAwalBil
                            , int counterAkhirBil
                            , int totalBiayaBil)).addOnSuccessListener((new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(view.getContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                        }
                    }));

                }

            }
        });

        return view;


    }

    private void input(EditText txt, String s){
        txt.setError(s+"tidak boleh kosong");
        txt.requestFocus();
    }
