package com.example.pomodoro.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.R;
import com.example.pomodoro.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import pl.droidsonroids.gif.GifImageView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Button timButton = binding.timerBtn;;
        timButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer(timButton);
            }
        });
        initQuotes();
        //timerRun(binding.textView3);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    void initQuotes(){
        TextView quoteView = binding.textView4;
        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("quotes");
        if(!isInternetAvailable()){
            Snackbar.make(getView(),"You're not connected to internet.",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();

        }
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                List<String> quotes = (List<String>) snapshot.getValue();
                int rand =  0 + (int)(Math.random() * (((quotes.size()-1 )- 0) + 1));
                quoteView.setText("''" +quotes.get(rand)+"''");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    void timerRun(TextView timerView, long millis,Button timbtn,String goal){
        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.tick);
        mp.setLooping(true);
        mp.start();
        TextView goalText = binding.goalText;
        Button pauseBtn = binding.button2;
        Button stopBtn = binding.button;
        pauseBtn.setVisibility(View.VISIBLE);
        stopBtn.setVisibility(View.VISIBLE);
        goalText.setText(goal);
        initQuotes(millis*4);
        new CountDownTimer(millis,1000){
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished/ 1000) % 60 ;
                int minutes = (int) ((millisUntilFinished/ (1000*60)) % 60);
                int hours   = (int) ((millisUntilFinished/ (1000*60*60)) % 24);
                timerView.setText(hours+" : "+ minutes+" : "+seconds);
            }

            public void onFinish() {
                mp.stop();
                MediaPlayer mp2 = MediaPlayer.create(getContext(), R.raw.bellding);
                mp2.start();
                Toast.makeText(getContext(), "Done!!", Toast.LENGTH_SHORT).show();
                pauseBtn.setVisibility(View.GONE);
                stopBtn.setVisibility(View.GONE);
                timbtn.setVisibility(View.VISIBLE);
                AlertDialog alert = getFinishAlert().create();
                alert.show();
            }
        }.start();
    }

    void setTimer(Button timbtn){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        Context context = dialogBuilder.getContext();
        dialogBuilder.setTitle("Set Timer");
        EditText input = new EditText(context);
        EditText goal = new EditText(context);
        goal.setHint("what do you plan to get done?");
        goal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        input.setHint("minutes");
        input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(38,20,38,20);
        Button startbtn = new Button(context);
        startbtn.setText("Start!");
        startbtn.setBackgroundColor((int) Color.RED);
        startbtn.setTextColor((int)Color.WHITE);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int minutes = Integer.parseInt(input.getText().toString());
                String goalStr = goal.getText().toString();
                timbtn.setVisibility(View.GONE);
                Toast.makeText(context, "Let's do it!", Toast.LENGTH_LONG).show();
                timerRun(binding.textView3,minutes * 60 * 1000,timbtn,goalStr);

            }
        });
        linearLayout.addView(goal);
        linearLayout.addView(input);
        linearLayout.addView(startbtn);
        dialogBuilder.setView(linearLayout);
        AlertDialog alert = dialogBuilder.create();
        alert.show();
    }

    AlertDialog.Builder getFinishAlert(){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("Recents");
        LinearLayout lineL  = new LinearLayout(getContext());
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        Button startbtn = new Button(dialogBuilder.getContext());
        EditText editText = new EditText(dialogBuilder.getContext());
        TextView titleView  = new TextView(dialogBuilder.getContext());
        GifImageView gifImageView = new GifImageView(dialogBuilder.getContext());
        startbtn.setText("Finish");
        startbtn.setBackgroundColor((int) Color.RED);
        startbtn.setTextColor((int)Color.WHITE);
        titleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        titleView.setText("You did it!");
        titleView.setTextSize(30);
        titleView.setTextColor((int)Color.WHITE);
        editText.setHint("Type your result here");
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
                String str = formatter.format(date);
                String result = "RT"+editText.getText().toString();
                Map<String, Object> entMap = new HashMap<>();
                entMap.put(str,(Object)result);

                editText.setText("");
                Toast.makeText(dialogBuilder.getContext(), "Good Job! Check your recents.", Toast.LENGTH_LONG).show();
            }
        });
        lineL.setOrientation(LinearLayout.VERTICAL);
        lineL.setPadding(30,10,30,30);
        lineL.addView(titleView);
        lineL.addView(gifImageView);
        lineL.addView(editText);
        lineL.addView(startbtn);
        gifImageView.setImageResource(R.drawable.hurray);
        dialogBuilder.setView(lineL);
        return dialogBuilder;
    }

    void initQuotes(long time){
        TextView quoteView = binding.textView4;
        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("quotes");
        if(!isInternetAvailable()){
            Snackbar.make(getView(),"You're not connected to internet.",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();

        }
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                List<String> quotes = (List<String>) snapshot.getValue();
                new CountDownTimer(time,4000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int rand =  0 + (int)(Math.random() * (((quotes.size()-1 )- 0) + 1));
                        quoteView.setText("''" +quotes.get(rand)+"''");
                    }

                    @Override
                    public void onFinish() {

                    }
                };

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}