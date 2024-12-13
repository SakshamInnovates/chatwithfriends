package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        Button button ;
        EditText email,password;
        TextView signup;

        FirebaseAuth auth;
        String emalPatter = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        auth = FirebaseAuth.getInstance();
        signup=findViewById(R.id.signup);
        button = findViewById(R.id.buttonlog);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);



signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(login.this,registration.class);
        startActivity(intent);
        finish();
    }
});
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if(TextUtils.isEmpty(Email)){
                   Toast.makeText(login.this,"Email is required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Password)) {
                    
                    Toast.makeText(login.this,"password is required", Toast.LENGTH_SHORT).show();
                } else if (Email.matches(emalPatter)) {
                    email.setError("Invalid Email");

                } else if (Password.length() < 6) {
                    password.setError("Password must be more than  6 characters");
                    Toast.makeText(login.this,"Password must be more than  6 characters", Toast.LENGTH_SHORT).show();
                } else{

                    auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                try{
                                    Intent  intent= new Intent(login.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }catch (Exception e){
                                    Toast.makeText(login.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }else{
                                Toast.makeText(login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                }


        });


    }


}