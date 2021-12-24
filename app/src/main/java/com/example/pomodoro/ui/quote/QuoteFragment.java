package com.example.pomodoro.ui.quote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.databinding.FragmentHomeBinding;
import com.example.pomodoro.databinding.FragmentQuoteBinding;
import com.example.pomodoro.ui.quote.QuoteViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuoteFragment extends Fragment {

    private QuoteViewModel quoteViewModel;
    private FragmentQuoteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);binding = FragmentQuoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//////////////////////////////////////////////////////////////////////////////////
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = mAuth.getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Button savebtn = binding.saveButton;
        EditText q1 = binding.editTextTextPersonName8;
        EditText q2 = binding.editTextTextPersonName9;
        EditText q3 = binding.editTextTextPersonName10;
        EditText q4 = binding.editTextTextPersonName4;
        EditText q5 = binding.editTextTextPersonName5;
        EditText q6 = binding.editTextTextPersonName6;
        EditText q7 = binding.editTextTextPersonName7;
        EditText q8 = binding.editTextTextPersonName;
        EditText[] quoteET = {q1,q2,q3,q4,q5,q6,q7,q8};
        String quotes[] = new String[8];
        Map<String, String> quoteMap = new HashMap<String, String>();
        retrieveQuotes(curUser,quotes,quoteET);


     //   Toast.makeText(getActivity(),""+curUser.getUid(),Toast.LENGTH_LONG).show();

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(isInternetAvailable())){
                    Snackbar.make(q1,"Please check your internet.",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();
                }
                quotes[0] = q1.getText().toString();
                quotes[1] = q2.getText().toString();
                quotes[2] = q3.getText().toString();
                quotes[3] = q4.getText().toString();
                quotes[4] = q5.getText().toString();
                quotes[5] = q6.getText().toString();
                quotes[6] = q7.getText().toString();
                quotes[7] = q8.getText().toString();

                for(int i=0;i <=7; i++){
                    quoteMap.put(""+i,quotes[i]);
                }
                try {
                    mDatabase.child("users").child(curUser.getUid()).child("quotes").setValue(quoteMap);
                    Snackbar.make(q1,"List Updated Successfully.",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();
                }catch(Exception e){
                    Snackbar.make(q1,"Oops something went wrong",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();
                }
            }
        });




        quoteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void retrieveQuotes(FirebaseUser curUser,String[] quotes, EditText[] quoteET){
        if(!(isInternetAvailable())){
            Snackbar.make(getView(),"You're not connected to internet.",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();

        }
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("quotes");

       mDatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               List<Object> obj = (List<Object>) snapshot.getValue();
               for(int i=0;i<quoteET.length;i++){
                   quoteET[i].setText(""+obj.get(i));
                  // Toast.makeText(getActivity(), ""+obj, Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onCancelled(@NonNull @NotNull DatabaseError error) {

           }
       });

    }
    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
