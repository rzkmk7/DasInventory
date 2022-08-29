package com.XyzStudio.dasinventory;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.XyzStudio.dasinventory.databinding.ActivityHistoryBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class History extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_history);

        //public List<HistoryData> arrayListInvHistory;
        //public DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Inventory");


    }
}