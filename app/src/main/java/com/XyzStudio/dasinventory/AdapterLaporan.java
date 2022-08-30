package com.XyzStudio.dasinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterLaporan extends ArrayAdapter<LaporanData> {

    Context context;
    private AdapterInventory.OnClickListener mListener;
    private AdapterInventory.OnClickListenerDel mListenerDel;
    public List<LaporanData> arrayListCustomer;

    public AdapterLaporan(@NonNull Context context,
                          List<LaporanData> arrayListCustomer,
                          AdapterInventory.OnClickListener mListener,
                          AdapterInventory.OnClickListenerDel mListenerDel) {
        super(context, R.layout.laporan_item, arrayListCustomer);

        this.context = context;
        this.arrayListCustomer = arrayListCustomer;
        this.mListener = mListener;
        this.mListenerDel = mListenerDel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.laporan_item, null, true);

        TextView tv_tgl_laporan = view.findViewById(R.id.txt_tgl_laporan);
        TextView tv_tempat_laporan = view.findViewById(R.id.txt_tempat_laporan);

        ImageButton ed_inv = view.findViewById(R.id.btn_edLap);
        ImageButton ed_invDel = view.findViewById(R.id.btn_delLapItem);

        tv_tgl_laporan.setText(arrayListCustomer.get(position).getTgl_laporan());
        tv_tempat_laporan.setText(arrayListCustomer.get(position).getTempat_laporan());

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

        return view;
    }

    public interface OnClickListener {
        public void onClick(Integer message);
    }

    public interface OnClickListenerDel {
        public void onClick(Integer message);
    }

}
