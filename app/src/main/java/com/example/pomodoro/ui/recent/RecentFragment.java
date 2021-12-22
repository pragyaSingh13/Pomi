package com.example.pomodoro.ui.recent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.databinding.FragmentRecentBinding;


public class RecentFragment extends Fragment {

    private RecentViewModel recentViewModel;
    private FragmentRecentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recentViewModel =
                new ViewModelProvider(this).get(RecentViewModel.class);

        binding = FragmentRecentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


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
}
