package com.XyzStudio.dasinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterInventory<context> extends ArrayAdapter<InventoryData> {

    Context context;
    public List<InventoryData> arrayListInventory;

    public AdapterInventory(@NonNull Context context, List<InventoryData> arrayListInventory) {
        super(context, R.layout.laporan_item, arrayListInventory);

        this.context = context;
        this.arrayListInventory = arrayListInventory;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inventory_item, null, true);

        TextView tv_namaBarang = view.findViewById(R.id.tv_namaBarang);
        TextView tv_type = view.findViewById(R.id.tv_type);
        TextView tv_jmlStok = view.findViewById(R.id.tv_jmlStok);

        tv_namaBarang.setText(arrayListInventory.get(position).getNamaBarang());
        tv_type.setText(arrayListInventory.get(position).getType());
        tv_jmlStok.setText(arrayListInventory.get(position).getJmlStok());


        return view;
    }
}
