package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    TextView profileName;
    Button btnEditProfile;
    Button btn_inventori;
    Button btn_laporan;

    private String uid;
    public DatabaseReference usersRef;
    public FirebaseDatabase userDB;
    private FirebaseUser users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnEditProfile = findViewById(R.id.btnEditProfile);
        profileName = findViewById(R.id.profileName);
        btn_inventori = findViewById(R.id.btn_inventori);
        btn_laporan = findViewById(R.id.btn_laporan);

        users = FirebaseAuth.getInstance().getCurrentUser();
        usersRef = userDB.getInstance().getReference("Users");
        uid = users.getUid();

        usersRef.child(uid).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String nama = userProfile.nama;
                    profileName.setText(nama);
                }
//                else if (userProfile.nama = "null"){
//                    String nama = userProfile.nama;
//                    profileName.setText("UserName");
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "salah", Toast.LENGTH_SHORT).show();

            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this,EditProfile.class));
            }
        });

        btn_inventori.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Inventory.class));
            }
        });

        btn_laporan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Inventory.class));
            }
        });
    }
}