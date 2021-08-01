package com.example.pomodoro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText usern, emailt, passt;
    private TextView login;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usern = findViewById(R.id.username);
        emailt = findViewById(R.id.email1);
        passt = findViewById(R.id.pass1);
        ok = findViewById(R.id.signup);
        login  = findViewById(R.id.textView13);

        ok.setOnClickListener(this);
        login.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView13: startActivity(new Intent(Register.this, LogIn.class)); break;
            case R.id.signup: registerUser();break;
        }
    }

    private void registerUser(){

    String email  = emailt.getText().toString().trim();
    String password = passt.getText().toString().trim();
    String username = usern.getText().toString().trim();

    if(username.isEmpty()){
        usern.setError("Username required");
        usern.requestFocus();
        return;
    }
    if(password.isEmpty()){
        passt.setError("Password required");
        passt.requestFocus();
        return;
    }
    if(email.isEmpty()){
        emailt.setError("Email required");
        emailt.requestFocus();
        return;
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        emailt.setError("Enter a valid email");
        emailt.requestFocus();
        return;
    }
    if(password.length() <= 6){
        passt.setError("Password too short");
        passt.requestFocus();
        return;
    }
    }
}
