package com.example.pomodoro.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.R;
import com.example.pomodoro.databinding.FragmentGalleryBinding;
import com.example.pomodoro.shortInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    //write the shit here!!!!!!!!!!!!1
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        final ListView list = binding.shortList;
        Button infoButton = binding.infobutton2;

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("short term theory shit");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Got It!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#172949")));
                alert11.show();
            }
        });


        String items[] = {"bitch","lasagna"};
        ArrayList arrayList = new ArrayList<String>();
        arrayList.addAll(Arrays.asList(items));
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                arrayList);
        list.setAdapter(adapter);


        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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