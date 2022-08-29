package com.XyzStudio.dasinventory;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class invAdapterHistory<context> extends ArrayAdapter<HistoryData> {

    Context context;
//    private AdapterInventory.OnClickListener mListener;
//    private AdapterInventory.OnClickListenerDel mListenerDel;
//    private AdapterInventory.OnClickListenerHistory mListenerHistory;
    public List<HistoryData> arrayListHistory;
//    public Integer ambil = 0;
    public DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Inventory");

    public invAdapterHistory(@NonNull Context context, List<HistoryData> arrayListHistory, invAdapterHistory.OnClickListener mListener) {
        super(context, R.layout.activity_history_item, arrayListHistory);

        this.context = context;

    }

    public class OnClickListener {
    }
}