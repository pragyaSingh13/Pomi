package com.example.pomodoro.ui.bucket;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.databinding.FragmentAboutBinding;
import com.example.pomodoro.databinding.FragmentBucketBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class BucketFragment extends Fragment {
    private BucketViewModel bucketViewModel;
    private FragmentBucketBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bucketViewModel = new ViewModelProvider(this).get(BucketViewModel.class);
        binding = FragmentBucketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
///////////////////////////////////////////////////////////////////////////////////////////////////////
        final ListView list = binding.bucketList;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = mAuth.getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        EditText editText = binding.addEt;
        Button savebtn = binding.button3;
        List<String> goals = new ArrayList<String>();
        DatabaseReference pDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("bucket list");
        goals.add("garbage");
        goals.add("garbage2");
        pDatabase.setValue(goals);
        updateList(curUser, list);
        //getInitData(curUser);
        //goals.addAll(getInitData(curUser));
        Toast.makeText(getContext(), "" + goals, Toast.LENGTH_SHORT).show();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item_str = editText.getText().toString();
                if (item_str.isEmpty()) {
                    Snackbar.make(editText, "Field is empty!", Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int) Color.WHITE).show();
                } else {
                  addToList(goals,item_str,curUser,list);
                }
            }
        });


        bucketViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

    private void updateList(FirebaseUser curUser, ListView listView) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("bucket list");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                List<String> obj = (List<String>) snapshot.getValue();
                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1,
                        obj);

                listView.setAdapter(adapter);
                return;
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return;
    }
    private int addToList(List<String> goals,String item_str,FirebaseUser curUser,ListView list){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("bucket list");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                goals.clear();
                goals.addAll((List<String>) snapshot.getValue());
                goals.add(item_str);
                mDatabase.setValue(goals);
                updateList(curUser,list);
                Snackbar.make(list,"List Updated Successfully.",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();
                return;
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {


            }
        });
        return 0;
    }
}



/*    private void getInitData(FirebaseUser curUser) {
        List<String> list = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("bucket list");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            list.addAll((List<String>) snapshot.getValue());

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        Toast.makeText(getActivity(), ""+list, Toast.LENGTH_SHORT).show();
    }
}*/

/*
 goals.add(item_str);
                    mDatabase.child("users").child(curUser.getUid()).child("bucket list").setValue(goals);
                    updateList(curUser,list);
                    Snackbar.make(editText,"List Updated Successfully.",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();

 */

/////////////////////loop one
/*
 goals.clear();
                goals.addAll((List<String>) snapshot.getValue());
                goals.add(item_str);
                mDatabase.setValue(goals);
                updateList(curUser,list);
                Snackbar.make(list,"List Updated Successfully.",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();
 */