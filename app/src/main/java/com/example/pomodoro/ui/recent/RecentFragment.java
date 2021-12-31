package com.example.pomodoro.ui.recent;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.GoalItemAdapter;
import com.example.pomodoro.GoalItemAdapterRecent;
import com.example.pomodoro.GoalText;
import com.example.pomodoro.databinding.FragmentRecentBinding;
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class RecentFragment extends Fragment {

    private RecentViewModel recentViewModel;
    private FragmentRecentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recentViewModel =
                new ViewModelProvider(this).get(RecentViewModel.class);

        binding = FragmentRecentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
/////////////////////////////////////////////////////////////////////

        initItems(binding.recentList);


        recentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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


    private void initItems(ListView listView) {

        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<GoalText> arrayList = new ArrayList<>();
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("Recents");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Map<Date, Object> list = (Map<Date, Object>) snapshot.getValue();
                Map<Date, Object> sortedMap = new TreeMap<Date, Object>(list);
                Iterator hmIterator = sortedMap.entrySet().iterator();

                while (hmIterator.hasNext()) {
                    Map.Entry mapElement
                            = (Map.Entry) hmIterator.next();
                    arrayList.add(new GoalText((String) mapElement.getValue(), (String) mapElement.getKey()));
                    GoalItemAdapterRecent goalItemAdapter = new GoalItemAdapterRecent(getContext(), arrayList);
                    listView.setAdapter(goalItemAdapter);
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
