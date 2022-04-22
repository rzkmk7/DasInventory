package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog; //buat loading
    private EditText email,password;

    private String uid;
    public DatabaseReference usersRef;
    public FirebaseDatabase userDB;
    private FirebaseUser users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email=findViewById(R.id.et_username);
        password=findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();

        users = FirebaseAuth.getInstance().getCurrentUser();
        usersRef = userDB.getInstance().getReference("Users");
        if(users != null)
        {
            reload();
        }


        //buat loading
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("silakan tunggu");
        progressDialog.setCancelable(false);

        btnLogin.setOnClickListener(v -> {
            if(email.getText().length()>0&& password.getText().length()>0){
                login(email.getText().toString(),password.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "isi dulu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(String email, String password){
        //coding login
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()&&task.getResult()!=null){

                    users = FirebaseAuth.getInstance().getCurrentUser();
                    usersRef = userDB.getInstance().getReference("Users");
                    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.hasChild(users.getUid()))
                                usersRef.child(users.getUid()).child("profile").child("nama").setValue("UserName");


                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {


                        }

                    });
//                    buat loading
//                    progressDialog = new ProgressDialog(Login.this);
//                    progressDialog.setTitle("Loading");
//                    progressDialog.setMessage("silakan tunggu");
//                    progressDialog.setCancelable(false);
                    reload();
                }else{
                    Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void reload(){
        startActivity(new Intent(getApplicationContext(),Home.class));
    }

}