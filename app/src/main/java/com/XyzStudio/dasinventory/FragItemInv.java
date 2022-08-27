package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

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

public class FragItemInv extends DialogFragment {
    public FragItemInv(String namaBarang, String jmlStok, String type, String ket, String key, String date, String stokAkhir) {
        this.namaBarang = namaBarang;
        this.jmlStok = jmlStok;
        this.type = type;
        this.ket = ket;
        this.key = key;
        this.date = date;
        this.stokAkhir = stokAkhir;

    }

    private String namaBarang;
    private String jmlStok;
    private String type;
    private String ket;
    private String key;
    private String date;
    private String stokAkhir;

    private String uidInv;

//    public DatabaseReference invRef;
//    public FirebaseDatabase invDB;
//*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.activity_frag_item_inv, container, false);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        TextView ed_namaBarang = view.findViewById(R.id.ed_namaBarang);
        TextView ed_jmlStok = view.findViewById(R.id.ed_jmlStok);
        TextView ed_type = view.findViewById(R.id.ed_type);
        TextView ed_ket = view.findViewById(R.id.ed_ket);
        TextView dateEdInv = view.findViewById(R.id.invDate);
        TextView stokEdAkhir = view.findViewById(R.id.invStokAkhir);
        Button ed_btn_invSave = view.findViewById(R.id.ed_btn_invSave);

        ed_namaBarang.setText(this.namaBarang);
        ed_jmlStok.setText(this.jmlStok);
        ed_type.setText(this.type);
        ed_ket.setText(this.ket);
        dateEdInv.setText(this.date);
        stokEdAkhir.setText(this.stokAkhir);


        /*inv = FirebaseAuth.getInstance().getCurrentUser();
        invRef = userDB.getInstance().getReference("Users");
        inv = users.getUid();*/

        ed_btn_invSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namaBarang = ed_namaBarang.getText().toString();
                String jmlStok = ed_jmlStok.getText().toString();
                String ket = ed_ket.getText().toString();
                String type = ed_type.getText().toString();
                String date = dateEdInv.getText().toString();
                String stokAkhir = stokEdAkhir.getText().toString();

                if(TextUtils.isEmpty(namaBarang)){
                    input((EditText) ed_namaBarang, "namaBarang" );
                }else if (TextUtils.isEmpty(jmlStok)){
                    input((EditText) ed_jmlStok, "jmlStok" );
                }else if (TextUtils.isEmpty(type)){
                    input((EditText) ed_type, "type" );
                }else {
//


                    database.child("Inventory").child(key).setValue(new InventoryData(namaBarang, jmlStok, type, ket,date,stokAkhir)).addOnSuccessListener((new OnSuccessListener<Void>() {
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
        txt.setError(s +""+"tidak boleh kosong");
        txt.requestFocus();
    }
}