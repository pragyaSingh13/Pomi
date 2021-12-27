package com.example.pomodoro.ui.gallery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.DatePick;
import com.example.pomodoro.GoalItemAdapter;
import com.example.pomodoro.GoalText;
import com.example.pomodoro.R;
import com.example.pomodoro.databinding.FragmentGalleryBinding;
import com.example.pomodoro.shortInfo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //-----------------------------------------------------------------------------------------------------
        ListView listView = binding.shortList;
        LinearLayout lineL  = new LinearLayout(getContext());
        View horizontalDiaLine= new View(getActivity());
        TextView aDelete = new TextView(getContext());
        TextView aAchieve = new TextView(getContext());
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        EditText input = new EditText(getContext());
        FloatingActionButton actionButton = binding.actionBtn;

        populateListView(listView);



        //listItemAlert
        AlertDialog alert2 = listItemAlert().create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alert2.show();
            }
        });
        //float action
        final AlertDialog alert1 = createFloatAlert(dialogBuilder).create();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert1.show();

            }
        });


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

    private void populateListView(ListView listView){
        ArrayList<GoalText> arrayList = new ArrayList<GoalText>();
        GoalText g1 = new GoalText("crores ieufbeougfbeourfgbe;orubfoerug"," 12/10/2022");
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);
        arrayList.add(g1);


        //set it in listview
        GoalItemAdapter goalItemAdapter = new GoalItemAdapter(getContext(),arrayList);
        listView.setAdapter(goalItemAdapter);
    }

    private androidx.appcompat.app.AlertDialog.Builder createFloatAlert(AlertDialog.Builder dialogBuilder){
        EditText input = new EditText(getContext());
        EditText datepick = new EditText((getContext()));
        Context context = dialogBuilder.getContext();
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(38,20,38,20);


        datepick.setHint("Select date");
        input.setHint("type here...");

        //datepicker listener
        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        datepick.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DatePick datePick = new DatePick(datepick,v.getContext());
                datePick.onFocusChange(v,true);
            }
        });
        linearLayout.addView(input);
        linearLayout.addView(datepick);
        dialogBuilder.setView(linearLayout);
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

        return dialogBuilder;
    }

    private androidx.appcompat.app.AlertDialog.Builder listItemAlert(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        TextView aDelete = new TextView(getContext());
        TextView aAchieve = new TextView(getContext());
        LinearLayout lineL  = new LinearLayout(getContext());
        View horizontalDiaLine= new View(getActivity());
        horizontalDiaLine.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                2
        ));
        horizontalDiaLine.setBackgroundColor(Color.parseColor("#989898"));
        lineL.setOrientation(LinearLayout.VERTICAL);
        lineL.setPadding(30,50,30,30);
        aAchieve.setText("Goal Achieved");
        aAchieve.setPadding(0,0,0,30);
        aAchieve.setTextColor((int)Color.WHITE);
        aAchieve.setTextSize(20);
        aDelete.setText("Delete goal?");
        aDelete.setPadding(0,26,0,0);
        aDelete.setTextColor((int)Color.WHITE);
        aDelete.setTextSize(20);
        aDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setTitle("Are you sure want to delete?").show();
            }
        });

        lineL.addView(aAchieve);
        lineL.addView(horizontalDiaLine);
        lineL.addView(aDelete);

        dialogBuilder.setView(lineL);
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        return dialogBuilder;
    }


}