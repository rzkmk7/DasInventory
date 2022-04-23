package com.XyzStudio.dasinventory;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Objects;

public class TeknisiActivity extends AppCompatActivity {

    FloatingActionButton fab_add_laporan;

    private EditText userName;
    private Button btnName;
    public String nama;

    private String uid;
    public DatabaseReference usersRef;
    public FirebaseDatabase userDB;
    private FirebaseUser users;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teknisi);


        userName = findViewById(R.id.nama_teknisi);
        btnName= findViewById(R.id.btn_edit_nama);
        fab_add_laporan = findViewById(R.id.fab_add_laporan);


        users = FirebaseAuth.getInstance().getCurrentUser();
        usersRef = userDB.getInstance().getReference("Users");
        uid = users.getUid();

        usersRef.child(uid).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String nama = userProfile.nama;
                    userName.setText(nama);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeknisiActivity.this, "salah", Toast.LENGTH_SHORT).show();

            }
        });
//        Log.d("tt",usersRef.child(uid).child("DisplayName").toString());
//       String nama = usersRef.child(users.getUid()).child("DisplayName").toString();




        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  EditText nama = (EditText) findViewById(R.id.nama_teknisi);
                FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("profile").child("nama").setValue(nama.getText().toString());
            }
        });

        fab_add_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           /*     FormLaporan formLaporan = new FormLaporan();
                formLaporan.show(getSupportFragmentManager(),"form");
*/
            }
        });
    }
}