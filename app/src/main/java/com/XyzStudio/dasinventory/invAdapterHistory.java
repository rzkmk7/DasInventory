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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class invAdapterHistory<context> extends ArrayAdapter<HistoryData> {

    Context context;
   private OnClickListener mListener;
//    private AdapterInventory.OnClickListenerDel mListenerDel;
//    private AdapterInventory.OnClickListenerHistory mListenerHistory;
    public List<HistoryData> arrayListHistory;
//    public Integer ambil = 0;
    public DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Inventory");

    public invAdapterHistory(@NonNull Context context, List<HistoryData> arrayListHistory, OnClickListener mListener) {
        super(context, R.layout.activity_history_item, arrayListHistory);

        this.mListener = mListener;
        this.context = context;

    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_inv_history, null, true);

        TextView date = view.findViewById(R.id.tv_dateAmbil);
        TextView ambil = view.findViewById(R.id.tv_ambilStok);
        TextView sisaStok = view.findViewById(R.id.tv_sisaStok);

//        ImageButton ed_bil = view.findViewById(R.id.ed_bil);
//        ImageButton ed_bilDel = view.findViewById(R.id.ed_bilDel);

//        ed_bil.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mListener.onClick(position);
//            }
//        });
//        ed_bilDel.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                mListenerDel.onClick(position);
//            }
//        });

        date.setText("Tempat  :"+arrayListHistory.get(position).getDateAmbil());
        ambil.setText("Type       :"+arrayListHistory.get(position).getAmbil());
        sisaStok.setText("Sn          :"+arrayListHistory.get(position).getStokAkhir());


        return view;
    }

    public interface OnClickListener {
        public void onClick(Integer message);
    }
}