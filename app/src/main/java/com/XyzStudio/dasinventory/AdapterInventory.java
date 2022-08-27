package com.XyzStudio.dasinventory;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterInventory<context> extends ArrayAdapter<InventoryData> {

    Context context;
    private OnClickListener mListener;
    private OnClickListenerDel mListenerDel;
    private OnClickListenerHistory mListenerHistory;
    public List<InventoryData> arrayListInventory;
    public Integer ambil = 0;
    public DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Inventory");


    public AdapterInventory(@NonNull Context context, List<InventoryData> arrayListInventory, OnClickListener mListener, OnClickListenerDel mListenerDel, OnClickListenerHistory mListenerHistory) {
        super(context, R.layout.activity_inventory_item, arrayListInventory);

        this.context = context;
        this.arrayListInventory = arrayListInventory;
        this.mListener = mListener;
        this.mListenerDel = mListenerDel;
        this.mListenerHistory = mListenerHistory;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inventory_item, null, true);
        Log.d("bbb","asdaasdasd");
        TextView tv_namaBarang = view.findViewById(R.id.tv_namaBarang);
        TextView tv_type = view.findViewById(R.id.tv_type);
        TextView tv_jmlStok = view.findViewById(R.id.tv_jmlStok);

        EditText et_ambil = view.findViewById(R.id.et_ambil);

        ImageButton ed_inv = view.findViewById(R.id.ed_inv);
        ImageButton ed_invDel = view.findViewById(R.id.ed_invDel);
        ImageButton ed_invHistory = view.findViewById(R.id.btnHistory);

        ImageButton ed_btnPlus = view.findViewById(R.id.btnPlus);
        ImageButton ed_btnMinus = view.findViewById(R.id.btnMinus);
        Button btn_saveStokInv = view.findViewById(R.id.btn_saveStokInv);

        ed_inv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onClick(position);
                Log.d("zzz","asdaasdasd");
            }
        });
        ed_invHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListenerHistory.onClick(position);
                Log.d("aaa","asdaasdasd");
            }
        });
        ed_invDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListenerDel.onClick(position);
//                ref.child(arrayListInventory.get(position).getKey()).removeValue();
            }
        });

        ed_btnPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ambil < Integer.parseInt(arrayListInventory.get(position).getJmlStok()))
                {
                    ambil += 1;
                    et_ambil.setText(String.valueOf(ambil));
                }
            }
        });
        ed_btnMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ambil > 0)
                {
                    ambil -= 1;
                    et_ambil.setText(String.valueOf(ambil));
                }
            }
        });
        btn_saveStokInv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer stokAkhir = Integer.parseInt(arrayListInventory.get(position).getJmlStok()) - ambil;
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                Date c = Calendar.getInstance().getTime();
                String formattedDate = df.format(c);

                ref.child(arrayListInventory.get(position)
                        .getKey())
                        .child("history")
                        .push()
                        .setValue(
                                new HistoryData(
                                        arrayListInventory.get(position).getNamaBarang(),
                                        ambil,
                                        formattedDate,
                                        stokAkhir
                                )).addOnSuccessListener((new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(view.getContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    }
                }));
            }
        });

        tv_namaBarang.setText("Nama : "+arrayListInventory.get(position).getNamaBarang());
        tv_type.setText("Type :"+arrayListInventory.get(position).getType());
        tv_jmlStok.setText("Stok :"+arrayListInventory.get(position).getJmlStok());


        return view;
    }
/// posisi interface click integer massage
    public interface OnClickListener {
        public void onClick(Integer message);
    }
    public interface OnClickListenerHistory {
        public void onClick(Integer message);
    }
    public interface OnClickListenerDel {
        public void onClick(Integer message);
    }
    public interface OnPlus {
        public void onClick(Integer message);
    }
    public interface OnMinus {
        public void onClick(Integer message);
    }
    public interface OnSaveStock {
        public void onClick(Integer message);
    }
}
