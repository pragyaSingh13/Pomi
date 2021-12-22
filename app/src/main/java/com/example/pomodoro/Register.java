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

import com.example.pomodoro.ui.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText usern, emailt, passt;
    private TextView login;
    private Button ok;
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usern = findViewById(R.id.username);
        emailt = findViewById(R.id.email1);
        passt = findViewById(R.id.pass1);
        ok = findViewById(R.id.signup);
        login = findViewById(R.id.textView13);

        ok.setOnClickListener(this);
        login.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView13:
                startActivity(new Intent(Register.this, LogIn.class));
                break;
            case R.id.signup:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String email = emailt.getText().toString().trim();
        String password = passt.getText().toString().trim();
        String username = usern.getText().toString().trim();

        if (username.isEmpty()) {
            usern.setError("Username required");
            usern.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passt.setError("Password required");
            passt.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailt.setError("Email required");
            emailt.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailt.setError("Enter a valid email");
            emailt.requestFocus();
            return;
        }
        if (password.length() <= 6) {
            passt.setError("Password too short");
            passt.requestFocus();
            return;
        }
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                User user = new User(username, email);
                FirebaseUser fUser = mAuth.getCurrentUser();
                String userId = fUser.getUid();
                mDatabase.child("users").child(userId).setValue(user);

                Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this, LogIn.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Register.this, "Oops something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });



    }
}

    /*    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    User user = new User(username, email);
                    FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Register.this, "Oops! Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(Register.this, "Oops! Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }


        });*/



  /*  mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
*/

/*    HashMap<String,Object> map = new HashMap<>();
map.put(“username”,uname);
        map.put(“email”,email);
        mDatabase.push(map);*/

/*

 */
