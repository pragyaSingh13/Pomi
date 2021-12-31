package com.example.pomodoro.ui.slideshow;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.pomodoro.databinding.FragmentSlideshowBinding;
import com.example.pomodoro.longInfo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);View root = binding.getRoot();
///Do from here--------------------------------------------------------------------------
        FloatingActionButton floatingActionButton = binding.actionBtn1;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        ListView listView = binding.longList;
        ArrayList<GoalText> listItems = new ArrayList<>();
        initItems(listView);


        //listview stuff


        //on items click dialog action
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listItemAlert(parent,position);

            }
        });

        //floating action button

       final AlertDialog alert1 = createFloatAlert(dialogBuilder,listItems).create();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert1.show();

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


    private AlertDialog.Builder createFloatAlert(AlertDialog.Builder dialogBuilder,ArrayList<GoalText> listItems){

        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
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
                mDatabase = FirebaseDatabase.getInstance().getReference();
                String goal = input.getText().toString();
                String date = datepick.getText().toString();
                Map<String,Object> map = new HashMap<>();
                map.put(date,goal);
                mDatabase.child("users").child(curUser.getUid()).child("Long Term Goals").updateChildren(map);
             //   updateListOnClick(binding.longList,date,listItems,goal);
                initItems(binding.longList);


            }
        });
        dialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return dialogBuilder;
    }


    private void initItems(ListView listView){
        ArrayList<GoalText> arrayList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("Long Term Goals");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
              Map<Date, Object> list = (Map<Date, Object>) snapshot.getValue();
              Map< Date, Object> sortedMap = new TreeMap< Date, Object>(list);
                Iterator hmIterator = sortedMap.entrySet().iterator();

                while(hmIterator.hasNext()){
                    Map.Entry mapElement
                            = (Map.Entry)hmIterator.next();
                    arrayList.add(new GoalText((String)mapElement.getValue(), (String) mapElement.getKey()));
                    GoalItemAdapter goalItemAdapter = new GoalItemAdapter(getContext(),arrayList);
                    listView.setAdapter(goalItemAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }

    private void listItemAlert(AdapterView<?> adapt, int position){
        Toast.makeText(getContext(), ""+position, Toast.LENGTH_LONG).show();
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
                        removeItemFromDB(position);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setTitle("Are you sure want to delete?").show();
            }
        });

        aAchieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAchieved(position);
                removeItemFromDB(position);
                Snackbar.make(getView(),"Check your Recent Achievements ",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();
                Toast.makeText(getContext(),"Check your Recent Achievements",Toast.LENGTH_SHORT);
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
        dialogBuilder.show();
    }

    void removeItemFromDB(int position){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("Long Term Goals");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String finalkey = null;
                int i=0;
                Map<String,String> map = (Map<String, String>) snapshot.getValue();
                Map<String,String> sortedMap = new TreeMap<>(map);
                for(String key: sortedMap.keySet()){
                    finalkey = key+"";
                    if(i==position)
                        break;
                    i++;
                }
                Toast.makeText(getActivity(), ""+finalkey, Toast.LENGTH_SHORT).show();
                dbRef.child(finalkey).removeValue();
                initItems(binding.longList);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    void setAchieved(int position){
      /*  TextView textView;
        final LayoutInflater factory = getLayoutInflater();
        final View textEntryView = factory.inflate(R.layout.goal_list_item, null);
        textView = textEntryView.findViewById(R.id.date_text);*/
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("Long Term Goals");
        DatabaseReference recentRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Map.Entry<String, String> mapEnt = null;
                int i=0;
                Map<String,String> map = (Map<String, String>) snapshot.getValue();
                Map<String,String> sortedMap = new TreeMap<>(map);
                Map<String, Object> mapk = new HashMap<>();
                for (Map.Entry<String,String> entry : sortedMap.entrySet()){
                    mapEnt = entry;
                    mapEnt.setValue("Long Term-"+mapEnt.getValue());
                    if(i==position)
                        break;
                    i++;

                }

                mapk.put(mapEnt.getKey(),(Object)mapEnt.getValue());
                recentRef.child("users").child(curUser.getUid()).child("Recents").updateChildren(mapk);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }


}

