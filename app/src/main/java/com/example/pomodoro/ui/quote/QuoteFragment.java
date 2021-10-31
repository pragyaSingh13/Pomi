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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.databinding.FragmentHomeBinding;
import com.example.pomodoro.databinding.FragmentQuoteBinding;
import com.example.pomodoro.ui.quote.QuoteViewModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class QuoteFragment extends Fragment {

    private QuoteViewModel quoteViewModel;
    private FragmentQuoteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);binding = FragmentQuoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Button savebtn = binding.saveButton;
        EditText q1 = binding.editTextTextPersonName;
        EditText q2 = binding.editTextTextPersonName4;
        EditText q3 = binding.editTextTextPersonName5;
        EditText q4 = binding.editTextTextPersonName6;
        EditText q5 = binding.editTextTextPersonName7;
        EditText q6 = binding.editTextTextPersonName8;
        EditText q7 = binding.editTextTextPersonName9;
        EditText q8 = binding.editTextTextPersonName10;

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
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
