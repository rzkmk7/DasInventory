package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    //    TextView dateInvForm = view.findViewById(R.id.invFormDate);
       Button btn_invSave = view.findViewById(R.id.btn_invSave);

        btn_invSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaBarang = et_namaBarang.getText().toString();
                String jmlStok = et_jmlStok.getText().toString();
                String ket = et_ket.getText().toString();
                String type = et_type.getText().toString();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                Date c = Calendar.getInstance().getTime();
                String date = df.format(c);
                //String date = dateInvForm.getText().toString();
                String stokAkhir = jmlStok;

                if(TextUtils.isEmpty(namaBarang)){
                    input((EditText) et_namaBarang, "namaBarang" );
                }else if (TextUtils.isEmpty(jmlStok)){
                    input((EditText) et_jmlStok, "jmlStok" );
                }else if (TextUtils.isEmpty(type)){
                    input((EditText) et_type, "type" );
                }else {
                    database.child("Inventory").push().setValue(new InventoryData(namaBarang,
                            jmlStok,
                            type,
                            ket,date
                            ,stokAkhir)).addOnSuccessListener((new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(view.getContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                            dismiss();
                        //  getActivity().recreate();
                            //startActivity(new Intent(this, MainActivity.class));
//                            refreshActivity();
                            ((Inventory)getActivity()).refresh();

                           /* Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.action_Second2Fragment_to_First2Fragment);

                            if (currentFragment instanceof "NAME OF YOUR FRAGMENT CLASS") {
                                FragmentTransaction fragTransaction =   (getActivity()).getFragmentManager().beginTransaction();
                                fragTransaction.detach(currentFragment);
                                fragTransaction.attach(currentFragment);
                                fragTransaction.commit();
                            }*/

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

//    public void refreshActivity(){
//        Inventory mDashboardActivity = new Inventory();
//        mDashboardActivity.refreshMyData();
//
//    }
}