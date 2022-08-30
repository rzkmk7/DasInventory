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

public class AdapterBilling<context> extends ArrayAdapter<BillingData> {

    Context context;
    private OnClickListener mListener;
    private OnClickListenerDel mListenerDel;
    public List<BillingData> arrayListBilling;

    public AdapterBilling(@NonNull Context context, List<BillingData> arrayListBilling, OnClickListener mListener, OnClickListenerDel mListenerDel) {
        super(context, R.layout.activity_billing_item, arrayListBilling);

        this.context = context;
        this.arrayListBilling = arrayListBilling;
        this.mListener = mListener;
        this.mListenerDel = mListenerDel;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_billing_item, null, true);

        TextView tv_tempatBil = view.findViewById(R.id.tv_tempatBil);
        TextView tv_typeBil = view.findViewById(R.id.tv_typeBil);
        TextView tv_snBil = view.findViewById(R.id.tv_snBil);

        ImageButton ed_bil = view.findViewById(R.id.ed_bil);
        ImageButton ed_bilDel = view.findViewById(R.id.ed_bilDel);

        ed_bil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onClick(position);
            }
        });
        ed_bilDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListenerDel.onClick(position);
            }
        });

        tv_tempatBil.setText("Tempat  :"+arrayListBilling.get(position).getTempatBil());
       tv_typeBil.setText("Type       :"+arrayListBilling.get(position).getTypeBil());
        tv_snBil.setText("Sn          :"+arrayListBilling.get(position).getSnBil());


        return view;
    }

    public interface OnClickListener {
        public void onClick(Integer message);
    }
    public interface OnClickListenerDel {
        public void onClick(Integer message);
    }
}
