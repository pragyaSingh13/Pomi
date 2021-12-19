package com.example.pomodoro.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.GoalItemAdapter;
import com.example.pomodoro.GoalText;
import com.example.pomodoro.R;
import com.example.pomodoro.databinding.FragmentSlideshowBinding;
import com.example.pomodoro.longInfo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);View root = binding.getRoot();
///Do from here--------------------------------------------------------------------------
        FloatingActionButton floatingActionButton = binding.actionBtn1;
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(getContext());
        EditText input = new EditText(getContext());
        ListView listView = binding.longList;

        populateListView(listView);

        //listview stuff


        //floating action button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setHint("type here...");
                dialogBuilder.setView(input);
                dialogBuilder.setTitle("Add item");
                dialogBuilder.setPositiveButton("done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                dialogBuilder.show();

            }
        });


//---------------------------------------------------------------------------------------
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

    private void populateListView(ListView listView){
        ArrayList<GoalText> arrayList = new ArrayList<GoalText>();
        GoalText g1 = new GoalText("Lose Weight"," 12/10/2022");
        arrayList.add(g1);

        //set it in listview
        GoalItemAdapter goalItemAdapter = new GoalItemAdapter(getContext(),arrayList);
        listView.setAdapter(goalItemAdapter);
    }
}