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
        final View view = inflater.inflate(R.layout.activity_form_inventory, container, false);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        TextView et_namaBarangBil = view.findViewById(R.id.et_namaBarangBil);
        TextView et_jmlStokBil = view.findViewById(R.id.et_jmlStokBil);
        TextView et_typeBil = view.findViewById(R.id.et_typeBil);
        TextView et_ketBil = view.findViewById(R.id.et_ketBil);
        Button btn_bilSave = view.findViewById(R.id.btn_bilSave);

        btn_bilSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaBarang = et_namaBarangBil.getText().toString();
                String jmlStok = et_jmlStokBil.getText().toString();
                String ket = et_ketBil.getText().toString();
                String type = et_typeBil.getText().toString();

                if(TextUtils.isEmpty(namaBarang)){
                    input((EditText) et_namaBarangBil, "namaBarang" );
                }else if (TextUtils.isEmpty(jmlStok)){
                    input((EditText) et_jmlStokBil, "jmlStok" );
                }else if (TextUtils.isEmpty(type)){
                    input((EditText) et_typeBil, "type" );
                }else {
                    database.child("Billing").push().setValue(new InventoryData(namaBarang, jmlStok, type, ket)).addOnSuccessListener((new OnSuccessListener<Void>() {
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