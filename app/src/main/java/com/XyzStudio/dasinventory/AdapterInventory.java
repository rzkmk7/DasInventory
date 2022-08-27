package com.XyzStudio.dasinventory;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterInventory<context> extends ArrayAdapter<InventoryData> {

    Context context;
    private OnClickListener mListener;
    private OnClickListenerDel mListenerDel;
    private OnClickListenerHistory mListenerHistory;
    public List<InventoryData> arrayListInventory;


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

        ImageButton ed_inv = view.findViewById(R.id.ed_inv);
        ImageButton ed_invDel = view.findViewById(R.id.ed_invDel);
        ImageButton ed_invHistory = view.findViewById(R.id.btnHistory);

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
}
