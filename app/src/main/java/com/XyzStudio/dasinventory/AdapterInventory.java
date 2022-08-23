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
    public List<InventoryData> arrayListInventory;

    public AdapterInventory(@NonNull Context context, List<InventoryData> arrayListInventory, OnClickListener mListener, OnClickListenerDel mListenerDel) {
        super(context, R.layout.activity_inventory_item, arrayListInventory);

        this.context = context;
        this.arrayListInventory = arrayListInventory;
        this.mListener = mListener;
        this.mListenerDel = mListenerDel;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inventory_item, null, true);

        TextView tv_namaBarang = view.findViewById(R.id.tv_namaBarang);
        TextView tv_type = view.findViewById(R.id.tv_type);
        TextView tv_jmlStok = view.findViewById(R.id.tv_jmlStok);

        ImageButton ed_inv = view.findViewById(R.id.ed_inv);
        ImageButton ed_invDel = view.findViewById(R.id.ed_invDel);

        ed_inv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onClick(position);
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

    public interface OnClickListener {
        public void onClick(Integer message);
    }
    public interface OnClickListenerDel {
        public void onClick(Integer message);
    }
}
