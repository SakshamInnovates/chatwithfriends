package com.example.chatapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;






public class registration extends AppCompatActivity {


    TextView rg_loginbtn;
    EditText rg_name, rg_email, rg_Password, rg_RePassword;
    Button rg_btn;
    CircleImageView rg_image;

    FirebaseAuth auth;
    Uri imageURI;
    String imageuri;
    String emalPatter = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase database;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        rg_loginbtn = findViewById(R.id.rg_loginbtn);
        rg_name = findViewById(R.id.rg_name);
        rg_email = findViewById(R.id.rg_email);
        rg_Password = findViewById(R.id.rg_Password);
        rg_RePassword = findViewById(R.id.rg_RePassword);
        rg_btn = findViewById(R.id.rg_btn);
        rg_image = findViewById(R.id.rg_image);




        rg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = rg_name.getText().toString();
                String email = rg_email.getText().toString();
                String password = rg_Password.getText().toString();
                String rePassword = rg_RePassword.getText().toString();
                String status = "online";

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {

                    Toast.makeText(registration.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if(!email.matches(emalPatter)){
                    rg_email.setError("Invalid Email");

                } else if (password.length()<6) {
                    rg_Password.setError("Password must be more than 6 characters");

                } else if (!password.equals(rePassword)) {
                    rg_RePassword.setError("Password does not match");
                    
                } else{
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String id = task.getResult().getUser().getUid();
                                DatabaseReference reference = database.getReference().child("Users").child(id);
                                StorageReference storageReference = storage.getReference().child("upload").child(id);

                                if (imageURI!=null){
                                    storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                            if(task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {

                                                        imageuri =uri.toString();
                                                        User users = new User(id,name,email,password,rePassword,imageuri,status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
if(task.isSuccessful()){
    Intent intent = new Intent(registration.this, MainActivity.class);
    startActivity(intent);
    finish();
} else{
    Toast.makeText(registration.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    String status = "hey i m using this application ";
                                }
                            }

                        }
                    });
                }
            }
        });


                rg_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);

                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (data != null) {
                imageURI = data.getData();
                rg_image.setImageURI(imageURI);
            }
        }
    }
}
