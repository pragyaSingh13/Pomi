package com.example.pomodoro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pomodoro.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LogIn extends AppCompatActivity  {
TextView signup, forgot ;
EditText emailt,passwordt;
Button login;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        signup = findViewById(R.id.textView9);
        login = findViewById(R.id.buttonsave4);
        emailt = findViewById(R.id.email1);
        passwordt = findViewById(R.id.password1);
        forgot  = findViewById(R.id.textView7);

        forgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = emailt.getText().toString();
                if(email.isEmpty()){
                    emailt.setError("Email is must.");
                }
                else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LogIn.this, "Email sent. Please check your email", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LogIn.this, "Please try again after some time", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this, Register.class));
            }
        });

    }
//On Click Method
    public void logIn(View view) {
        String password = passwordt.getText().toString();
        String email = emailt.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        if(password.isEmpty()){
            emailt.setError("Required Field");
        }
        if(email.isEmpty()){
            emailt.setError("Required Field");
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogIn.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LogIn.this, home.class));
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }
                    }
                });

    }
}