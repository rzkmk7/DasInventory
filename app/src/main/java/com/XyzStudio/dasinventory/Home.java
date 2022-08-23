package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    TextView profileJabatan;
    Button btnEditProfile;
    Button btn_inventory;
    Button btn_laporan;
    Button btn_billing;
    ImageButton btn_logout;


    private String uid;
    public DatabaseReference usersRef;
    public FirebaseDatabase userDB;
    private FirebaseUser users;
    public FirebaseUser logout;

//    private static int SPLASH_TIME_OUT=3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
////nyoba keep login
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SharedPreferences sharedPreferences = getSharedPreferences(Login.PREFS_NAME,0);
//                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false);
//
//                if(hasLoggedIn){
//                    Intent intent = new Intent(Home.this,Home.class);
//                    startActivity(intent);
//                    finish();
//                }else {
//                    Intent intent = new Intent(Home.this,Login.class);
//                    startActivity(intent);
//                    finish();
//                }
//
//            }
//        },SPLASH_TIME_OUT);
//        //nyoba keep login

        btnEditProfile = findViewById(R.id.btnEditProfile);
        profileName = findViewById(R.id.profileName);
        profileJabatan = findViewById(R.id.profileJabatan);
        btn_inventory = findViewById(R.id.btn_inventory);
        btn_laporan = findViewById(R.id.btn_laporan);
        btn_billing = findViewById(R.id.btn_billing);
        btn_logout = findViewById(R.id.btn_logout);

        users = FirebaseAuth.getInstance().getCurrentUser();
        /*logout = FirebaseAuth.getInstance().signOut();*/
        usersRef = userDB.getInstance().getReference("Users");
        /*Login login = new Login("s")*/
      uid = users.getUid();

        usersRef.child(uid).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String nama = userProfile.nama;
                    profileName.setText(nama);
                    String jabatan = userProfile.jabatan;
                    profileJabatan.setText(jabatan);
                }
                else {
                    usersRef.child(uid).child("profile").child("nama").setValue("Profile Name");
                    usersRef.child(uid).child("profile").child("jabatan").setValue("Jabatan");
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "salah", Toast.LENGTH_SHORT).show();

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* if(uid != null)
                {
                    logout();
                    String restUid = null;
                    uid = restUid;
                    Log.d("logout",uid);
                }*/

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Home.this,Login.class));
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this,EditProfile.class));
            }
        });

        btn_inventory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Inventory.class));
            }
        });

        btn_laporan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Laporan.class));
            }
        });
        btn_billing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Home.this,BillingMachine.class));
            }
        });
    }
}