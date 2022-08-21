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

public class FragItemInv extends DialogFragment {
   /* public FormInventory(String a) {
        this.a = a;
    }

    String a;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.activity_frag_item_inv, container, false);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        TextView ed_namaBarang = view.findViewById(R.id.et_namaBarang);
        TextView ed_jmlStok = view.findViewById(R.id.et_jmlStok);
        TextView ed_type = view.findViewById(R.id.et_type);
        TextView ed_ket = view.findViewById(R.id.et_ket);
        Button ed_btn_invSave = view.findViewById(R.id.btn_invSave);

        ed_btn_invSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaBarang = ed_namaBarang.getText().toString();
                String jmlStok = ed_jmlStok.getText().toString();
                String ket = ed_ket.getText().toString();
                String type = ed_type.getText().toString();

                if(TextUtils.isEmpty(namaBarang)){
                    input((EditText) ed_namaBarang, "namaBarang" );
                }else if (TextUtils.isEmpty(jmlStok)){
                    input((EditText) ed_jmlStok, "jmlStok" );
                }else if (TextUtils.isEmpty(type)){
                    input((EditText) ed_type, "type" );
                }else {
                    database.child("Inventory").push().setValue(new InventoryData(namaBarang, jmlStok, type, ket)).addOnSuccessListener((new OnSuccessListener<Void>() {
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