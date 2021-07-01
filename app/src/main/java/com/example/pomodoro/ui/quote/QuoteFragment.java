package com.example.pomodoro.ui.quote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class QuoteFragment extends Fragment {

    private QuoteViewModel quoteViewModel;
    private FragmentQuoteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        quoteViewModel = new ViewModelProvider(this).get(QuoteViewModel.class);binding = FragmentQuoteBinding.inflate(inflater, container, false);View root = binding.getRoot();quoteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

            Button saveButton = binding.saveButton;
            String quotes[] = {"mah life","mah rules","fucc my life"};

          


            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

     public void writetoFile(String[] quotes) {
        File fout = new File("quotes.txt");
        try {
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (String s : quotes) {
                bw.write(s);
                bw.newLine();
            }


        } catch (Exception e) {
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
