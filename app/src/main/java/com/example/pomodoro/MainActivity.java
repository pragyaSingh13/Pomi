package com.example.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  FirebaseApp.initializeApp(this);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser == null){
                    startActivity(new Intent(MainActivity.this, LogIn.class));
                }
                else {
                    startActivity(new Intent(MainActivity.this, home.class));
                }

            }
        },2000);
    }
}