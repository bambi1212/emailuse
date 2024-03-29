package com.example.logintry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        mAuth = FirebaseAuth.getInstance();
    }
    public void onStart(){ //if register no need o login again
        super.onStart();
        //check if user sign in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser !=null)
            startActivity(new Intent(EmailActivity.this, TextToSpeechActivity.class));

    }

    public void register(View v) {
        EditText emailText = findViewById(R.id. edittext_email);
        EditText passwordText = findViewById(R.id. edittext_password);

        mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //if register work move to welcome
                            startActivity(new Intent(EmailActivity.this, TextToSpeechActivity.class));
                        } else
                                {
                                    Toast.makeText(EmailActivity.this, "registe failed",Toast.LENGTH_LONG).show();
                                }
                    }
                });
    }

    public void login(View v) {
        EditText emailText = findViewById(R.id. edittext_email);
        EditText passwordText = findViewById(R.id. edittext_password);

        mAuth.signInWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //sign in succses, update UI with the signed in user information
                            startActivity(new Intent(EmailActivity.this, TextToSpeechActivity.class));
                        }
                        else
                            {
                                Toast.makeText(EmailActivity.this, "login failed",Toast.LENGTH_LONG).show();
                            }
                    }
                });

    }


}
