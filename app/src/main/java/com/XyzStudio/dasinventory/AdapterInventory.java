package com.XyzStudio.dasinventory;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


    public AdapterInventory(@NonNull Context context,
                            List<InventoryData> arrayListInventory,
                            OnClickListener mListener,
                            OnClickListenerDel mListenerDel,
                            OnClickListenerHistory mListenerHistory) {
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
//        Log.d("bbb","asdaasdasd");
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
//                Log.d("zzz","asdaasdasd");
            }
        });
        ed_invHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListenerHistory.onClick(position);
//                Log.d("aaa","asdaasdasd");
            }
        });
        ed_invDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListenerDel.onClick(position);


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
        et_ambil.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ambil = Integer.parseInt(charSequence.toString());
//                searchByName(charSequence.toString());
//                Log.d("testa", String.valueOf(ambil));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btn_saveStokInv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer stokAkhir = Integer.parseInt(arrayListInventory.get(position).getStokAkhir()) - ambil;
                String stokAkhirItem = String.valueOf(stokAkhir);
                //getcurrentDate
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                Date c = Calendar.getInstance().getTime();
                String formattedDate = df.format(c);
                //test set value
                Log.d("testa", String.valueOf(ambil));
                ref.child(arrayListInventory.get(position).getKey()).child("stokAkhir").setValue(stokAkhirItem);
        //        adapter.notifyDataSetChanged();

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
                        //refresh
                        ((Inventory)context).refresh();
                        //((Inventory)context).recreate();
                    }
                }));
                notifyDataSetChanged();
            }
        });

        tv_namaBarang.setText("Nama : "+arrayListInventory.get(position).getNamaBarang());
        tv_type.setText("Type : "+arrayListInventory.get(position).getType());
        tv_jmlStok.setText("STOK : "+arrayListInventory.get(position).getStokAkhir());

        //stok akhir view item blm nge reference ke last history database
//       ref.child(arrayListInventory.get(position).getKey()).addValueEventListener(new ValueEventListener() {
//                     @Override
//                     public void onDataChange(@NonNull DataSnapshot snapshot) {
//                         String stokAkhir = snapshot.child("stokAkhir").getValue().toString();
//                         Log.d("kiwo", stokAkhir);
//                         tv_jmlStok.setText("STOK : "+stokAkhir);
//                     }
//
//                     @Override
//                     public void onCancelled(@NonNull DatabaseError error) {
//
//                     }
//                 });


       //String test = ref.child(arrayListInventory.get(position).getKey()).child("history")
       // Log.d("kiwo", test);
//        ref.child(arrayListInventory.get(position)
//                .getKey())
//                .child("history").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("kiwo", snapshot.child("namBarang").getValue().toString());
//              // String test= snapshot.child("namaBarang").getValue().toString();
//               // int finalStokAkhir1;
//          //      finalStokAkhir1 = Integer.valueOf(snapshot.child("stokAkhir").getValue());
//        //        Log.d("kiwo", String.valueOf(finalStokAkhir1));
////                String finalStokAkhir = String.valueOf(finalStokAkhir1);
//             //  tv_jmlStok.setText("Stok : "+ test);
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });





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
