package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private CircleImageView profileImageView;
    private Button closeButton,saveButton;
    private TextView profileChangeBtn;
    private EditText edtNama;

    private String uid;
    public DatabaseReference usersRef;
    public FirebaseDatabase userDB;
    private FirebaseUser users;

//    private Uri imageUri;
//    private String myUri = "";
//    private StorageTask uploadTask;
//    private StorageReference storageProfilePicsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ////init

        users = FirebaseAuth.getInstance().getCurrentUser();
        usersRef = userDB.getInstance().getReference("Users");
        uid = users.getUid();
//        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile Pic");

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
               usersRef.child(uid).child("profile").child("nama").setValue(nama.getText().toString());
                usersRef.child(uid).child("profile").child("jabatan").setValue(jabatan.getText().toString());
                Toast.makeText(getApplicationContext(), "berhasil disimpan", Toast.LENGTH_SHORT).show();
//                validateAndsave();

            }
        });

        profileChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(EditProfile.this);
            }
        });

//        getUserinfo();

    }

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
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode  == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
//        {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            imageUri = result.getUri();
//
//            profileImageView.setImageURI(imageUri);
//        }
//        else {
//            Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    private void uploadProfileImage() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Set your profile");
//        progressDialog.setMessage("Please wait, while we are setting your data ");
//        progressDialog.show();
//
//        if (imageUri != null)
//        {
//            final StorageReference fileRef = storageProfilePicsRef
//                    .child(mAuth.getCurrentUser().getUid()+ ".jpg");
//
//            uploadTask = fileRef.putFile(imageUri);
//
//
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//                    if (!task.isSuccessful())
//                    {
//                        throw task.getException();
//                    }
//                    return fileRef.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
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
//                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
//
//                        progressDialog.dismiss();
//
//
//                    }
//
//                }
//            });
//        }
//        else {
//            progressDialog.dismiss();
//            Toast.makeText(this, "Data update", Toast.LENGTH_SHORT).show();
//        }
//
//   }
}