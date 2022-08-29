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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormBilling extends DialogFragment {
   /* public FormInventory(String a) {
        this.a = a;
    }

    String a;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.activity_form_billing, container, false);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        TextView et_tempatBil = view.findViewById(R.id.et_tempatBil);
        TextView et_typeBil = view.findViewById(R.id.et_typeBil);
        TextView et_snBil = view.findViewById(R.id.et_snBil);
        TextView et_biayaBil = view.findViewById(R.id.et_biayaBil);
        TextView et_counterAwalBil = view.findViewById(R.id.et_counterAwalBil);
//        TextView et_counterAkhirBil = view.findViewById(R.id.et_counterAkhirBil);
//        TextView et_totalBiayaBil = view.findViewById(R.id.et_totalBiayaBil);
//        Button btn_hitungBil = view.findViewById(R.id.btn_hitungBil);
        Button btn_saveBil = view.findViewById(R.id.btn_saveBil);

        btn_saveBil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempatBil = et_tempatBil.getText().toString();
                String typeBil = et_typeBil.getText().toString();
                String snBil = et_snBil.getText().toString();
                Integer biayaBil = Integer.parseInt(et_biayaBil.getText().toString());
                Integer counterAwalBil = Integer.parseInt(et_counterAwalBil.getText().toString());
                Integer counterAkhirBil = Integer.valueOf(counterAwalBil);

                if(TextUtils.isEmpty(tempatBil)){
                    input((EditText) et_tempatBil, "masukan tempat" );
                }else if (TextUtils.isEmpty(typeBil)){
                    input((EditText) et_typeBil, "masukan Type" );
                }else if (TextUtils.isEmpty(snBil)){
                    input((EditText) et_snBil, "masukan sn" );
                }/*else if (TextUtils.isEmpty(biayaBil)){
                    input((EditText) et_biayaBil, "masukan biaya" );
                }else if (TextUtils.isEmpty(counterAwal)){
                    input((EditText) et_counterAwalBil, "masukan counter" );
                }*/else {
                    database.child("Billing").push().setValue
                            (new BillingData(tempatBil
                            , typeBil
                            , snBil
                             , biayaBil
                             ,counterAwalBil
                            ,counterAkhirBil
                            ,0)).addOnSuccessListener((new OnSuccessListener<Void>() {
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
}