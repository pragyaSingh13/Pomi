package com.example.pomodoro.ui.bucket;

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

import com.example.pomodoro.databinding.FragmentAboutBinding;
import com.example.pomodoro.databinding.FragmentBucketBinding;

import java.util.ArrayList;
import java.util.Arrays;


public class BucketFragment extends Fragment {
    private BucketViewModel bucketViewModel;
    private FragmentBucketBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) { bucketViewModel = new ViewModelProvider(this).get(BucketViewModel.class);binding = FragmentBucketBinding.inflate(inflater, container, false);View root = binding.getRoot();
        final ListView list = binding.bucketList;
        String items[] = {"bitch","lasagna"};
        ArrayList arrayList = new ArrayList<String>();
        arrayList.addAll(Arrays.asList(items));
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                arrayList);
        list.setAdapter(adapter);

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
}
