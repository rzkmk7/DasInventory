package com.XyzStudio.dasinventory;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private CircleImageView profileImageView;
    private Button closeButton,saveButton;
    private TextView profileChangeBtn;
    private EditText edtNama;

    private String uid;
    public DatabaseReference databaseReference;
    public DatabaseReference dataRef;
    public FirebaseDatabase userDB;
    private FirebaseUser users;

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;


    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
   // private StorageReference storageProfilePicsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ////init
        mAuth = FirebaseAuth.getInstance();

        users = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = userDB.getInstance().getReference("Users");

       //storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("ProfilePic");
        dataRef = FirebaseDatabase.getInstance().getReference().child("User");

        uid = users.getUid();


        Log.d("lol","testttttttttttt");

        profileImageView = findViewById(R.id.profileIMG);

        closeButton = findViewById(R.id.btnCloseEditProfile);
        saveButton = findViewById(R.id.btnSaveEditProfile);



        profileChangeBtn = findViewById(R.id.btn_change_profile);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(1, 1);
                startActivity(getIntent());
                overridePendingTransition(1, 1);
                startActivity(new Intent(EditProfile.this,Home.class));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  EditText nama = (EditText) findViewById(R.id.edtNama);
                final  EditText jabatan = (EditText) findViewById(R.id.jabatan);
                databaseReference.child(uid).child("profile").child("nama").setValue(nama.getText().toString());
                databaseReference.child(uid).child("profile").child("jabatan").setValue(jabatan.getText().toString());
                Toast.makeText(getApplicationContext(), "berhasil disimpan", Toast.LENGTH_SHORT).show();

                //nyoba
               uploadProfileImage();
//                validateAndsave();
              //  StorageReference storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Image");
            }
        });

        profileChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(EditProfile.this);
            }
        });


        //getUserinfo();

    }

//    private void getUserinfo() {
//        dataRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//             if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
//
//                 if (dataSnapshot.hasChild("image")){
//                     String image = dataSnapshot.child("image").getValue().toString();
//                     Picasso.get().load(image).into(profileImageView);
//                 }
//             }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }

//    private void uploadProfileImage() {
//    }

//    private void validateAndsave() {
//        if (TextUtils.isEmpty(edtName.getText().toString()))
//        {
//            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
//        }else if (TextUtils.isEmpty(edtPhone.getText().toString()))
//        {
//            Toast.makeText(this, "Please Enter Your Phone No. ", Toast.LENGTH_SHORT).show();
//        }else {
//            HashMap<String,Object> userMap = new HashMap<>();
//            userMap.put("name",edtName.getText().toString());
//            userMap.put("phone",edtPhone.getText().toString());
//
//            databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
//
//            uploadProfileImage();
//        }
//    }
//
//
//    private void getUserinfo() {
//        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0)
//                {
//                    String name = dataSnapshot.child("name").getValue().toString();
//                    String  phone = dataSnapshot.child("phone").getValue().toString();
//
//                    edtName.setText(name);
//                    edtPhone.setText(phone);
//
//                    if (dataSnapshot.hasChild("image"))
//                    {
//                        String image = dataSnapshot.child("image").getValue().toString();
//                        Picasso.get().load(image).into(profileImageView);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode  == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
            Log.d("kontol",imageUri.getLastPathSegment());
        }
        else {
            Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show();
        }

    }


////
   private void uploadProfileImage() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Set your profile");
//        progressDialog.setMessage("Please wait, while we are setting your data ");
//        progressDialog.show();

        if (imageUri != null)
        {
          //  final StorageReference fileRef = storageProfilePicsRef.child(mAuth.getCurrentUser().getUid()+ ".jpg");
         StorageReference storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Image").child(imageUri.getLastPathSegment());
           // uploadTask = fileRef.putFile(imageUri);
            uploadTask = storageProfilePicsRef.putFile(imageUri);


//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//                    if (!task.isSuccessful())
//                    {
//                        throw task.getException();
//                    }
//                    return storageProfilePicsRef.getDownloadUrl();
//                }
//            }).  addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful())
//                    {
//                        Uri downloadUrl =task.getResult();
//                        myUri = downloadUrl.toString();
//
//                        HashMap<String, Object> userMap = new HashMap<>();
//                        userMap.put("image",myUri);
//
//                        dataRef.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
//
//                        progressDialog.dismiss();
//
//
//                    }
//
//                }
//            });
        }
        else {
//            progressDialog.dismiss();
            Toast.makeText(this, "Data update", Toast.LENGTH_SHORT).show();
        }
//
   }
}