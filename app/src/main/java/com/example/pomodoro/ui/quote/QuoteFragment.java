package com.example.pomodoro.ui.quote;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        EditText q1 = binding.editTextTextPersonName;
        EditText q2 = binding.editTextTextPersonName4;
        EditText q3 = binding.editTextTextPersonName5;
        EditText q4 = binding.editTextTextPersonName6;
        EditText q5 = binding.editTextTextPersonName7;
        EditText q6 = binding.editTextTextPersonName8;
        EditText q7 = binding.editTextTextPersonName9;
        EditText q8 = binding.editTextTextPersonName10;
        String quotes[] = new String[8];
        Map<String, String> quoteMap = new HashMap<String, String>();

        mDatabase.child("users").child(""+curUser.getUid()).child("quotes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                  Map<String,String>  quoteMapRead = (Map<String,String>)snapshot.getValue();
                  String quote = quoteMapRead.get("1");
                  Toast.makeText(getActivity(),quote,Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
     //   Toast.makeText(getActivity(),""+curUser.getUid(),Toast.LENGTH_LONG).show();

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                mDatabase.child("users").child(curUser.getUid()).child("quotes").setValue(quoteMap);
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

}
