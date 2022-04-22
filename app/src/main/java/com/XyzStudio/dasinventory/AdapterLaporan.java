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

public class AdapterLaporan extends ArrayAdapter<LaporanData> {

    Context context;
    public List<LaporanData> arrayListCustomer;

    public AdapterLaporan(@NonNull Context context, List<LaporanData> arrayListCustomer) {
        super(context, R.layout.laporan_item, arrayListCustomer);

        this.context = context;
        this.arrayListCustomer = arrayListCustomer;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.laporan_item, null, true);

        TextView tv_tgl_laporan = view.findViewById(R.id.txt_tgl_laporan);
        TextView tv_tempat_laporan = view.findViewById(R.id.txt_tempat_laporan);

        tv_tgl_laporan.setText(arrayListCustomer.get(position).getTgl_laporan());
        tv_tempat_laporan.setText(arrayListCustomer.get(position).getTempat_laporan());

        return view;
    }
}
