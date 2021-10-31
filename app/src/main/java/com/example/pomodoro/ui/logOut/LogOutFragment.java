package com.example.pomodoro.ui.logOut;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.LogIn;
import com.example.pomodoro.databinding.FragmentHomeBinding;
import com.example.pomodoro.databinding.FragmentLogoutBinding;
import com.example.pomodoro.home;
import com.example.pomodoro.ui.home.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class LogOutFragment extends Fragment {

    private LogOutViewModel logoutViewModel;
    private FragmentLogoutBinding logoutBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logoutViewModel =
                new ViewModelProvider(this).get(LogOutViewModel.class);

        logoutBinding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = logoutBinding.getRoot();
        Button logoutButton = logoutBinding.logoutbtn;

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = null;
                Toast.makeText(getActivity(), "Logged Out.",
                        Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),LogIn.class));
            }
        });


        logoutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logoutBinding = null;
    }
}

