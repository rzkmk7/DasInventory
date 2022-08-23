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

public class FormInventory extends DialogFragment {
   /* public FormInventory(String a) {
        this.a = a;
    }

    String a;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
       final View view = inflater.inflate(R.layout.activity_form_inventory, container, false);

       DatabaseReference database = FirebaseDatabase.getInstance().getReference();

       TextView et_namaBarang = view.findViewById(R.id.et_namaBarang);
       TextView et_jmlStok = view.findViewById(R.id.et_jmlStok);
       TextView et_type = view.findViewById(R.id.et_type);
       TextView et_ket = view.findViewById(R.id.et_ket);
       Button btn_invSave = view.findViewById(R.id.btn_invSave);

        btn_invSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaBarang = et_namaBarang.getText().toString();
                String jmlStok = et_jmlStok.getText().toString();
                String ket = et_ket.getText().toString();
                String type = et_type.getText().toString();

                if(TextUtils.isEmpty(namaBarang)){
                    input((EditText) et_namaBarang, "namaBarang" );
                }else if (TextUtils.isEmpty(jmlStok)){
                    input((EditText) et_jmlStok, "jmlStok" );
                }else if (TextUtils.isEmpty(type)){
                    input((EditText) et_type, "type" );
                }else {
                    database.child("Inventory").push().setValue(new InventoryData(namaBarang, jmlStok, type, ket)).addOnSuccessListener((new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(view.getContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();

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