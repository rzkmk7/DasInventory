//package com.XyzStudio.dasinventory;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.DialogFragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class FragBilling extends DialogFragment {
//    public FragBilling(String namaBarangBil, String jmlStokBil, String typeBil, String ketBil, String key) {
//        this.namaBarangBil = namaBarangBil ;
//        this.jmlStokBil  = jmlStokBil ;
//        this.typeBil  = typeBil ;
//        this.ketBil  = ketBil ;
//        this.key = key;
//    }
//
//    private String namaBarangBil ;
//    private String jmlStokBil ;
//    private String typeBil ;
//    private String ketBil ;
//    private String key;
//
//    private String uidInv;
//
////    public DatabaseReference invRef;
////    public FirebaseDatabase invDB;
////*/
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
//        final View view = inflater.inflate(R.layout.activity_frag_billing, container, false);
//
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//
//        TextView ed_namaBarangBil  = view.findViewById(R.id.ed_namaBarangBil );
//        TextView ed_jmlStokBil  = view.findViewById(R.id.ed_jmlStokBil );
//        TextView ed_typeBil  = view.findViewById(R.id.ed_typeBil );
//        TextView ed_ketBil  = view.findViewById(R.id.ed_ketBil );
//        Button ed_btn_bilSave = view.findViewById(R.id.ed_btn_bilSave );
//
//        ed_namaBarangBil.setText(this.namaBarangBil );
//        ed_jmlStokBil.setText(this.jmlStokBil );
//        ed_typeBil.setText(this.typeBil );
//        ed_ketBil.setText(this.ketBil );
//
//        /*inv = FirebaseAuth.getInstance().getCurrentUser();
//        invRef = userDB.getInstance().getReference("Users");
//        inv = users.getUid();*/
//
//        ed_btn_bilSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String namaBarangBil = ed_namaBarangBil .getText().toString();
//                String jmlStokBil = ed_jmlStokBil .getText().toString();
//                String ketBil = ed_ketBil .getText().toString();
//                String typeBil = ed_typeBil .getText().toString();
//
//                if(TextUtils.isEmpty(namaBarangBil)){
//                    input((EditText) ed_namaBarangBil , "namaBarang" );
//                }else if (TextUtils.isEmpty(jmlStokBil)){
//                    input((EditText) ed_jmlStokBil , "jmlStok" );
//                }else if (TextUtils.isEmpty(typeBil)){
//                    input((EditText) ed_typeBil , "type" );
//                }else {
////
//
//
//                    database.child("Billing").child(key).setValue(new BillingData(namaBarangBil, jmlStokBil, typeBil, ketBil)).addOnSuccessListener((new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(view.getContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
//                        }
//                    }));
//
//                }
//
//            }
//        });
//
//        return view;
//
//
//    }
//
//    private void input(EditText txt, String s){
//        txt.setError(s+"tidak boleh kosong");
//        txt.requestFocus();
//    }
//}