package com.example.pomodoro.ui.about;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pomodoro.R;
import com.example.pomodoro.databinding.FragmentAboutBinding;
import com.example.pomodoro.databinding.FragmentGalleryBinding;
import com.example.pomodoro.ui.gallery.GalleryViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.net.InetAddress;

public class AboutFragment extends Fragment {
    private AboutViewModel aboutViewModel;
    private FragmentAboutBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutViewModel =
                new ViewModelProvider(this).get(AboutViewModel.class);

        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//////////////////////////////////////////////////////////////////////////////////
        View view = getActivity().findViewById(android.R.id.content);

        WebView webView = binding.webView;
        ImageView errview = binding.imageView4;
        TextView errText = binding.errText;

        if(isInternetAvailable()){
            errText.setVisibility(View.GONE);
            errview.setVisibility(View.GONE);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("http://apracticalteen.home.blog/2021/12/31/what-is-the-pomodoro-technique-of-getting-things-done/");
        }
        else{
            errview.setVisibility(View.VISIBLE);
            errText.setVisibility(View.VISIBLE);
            Snackbar.make(view,"You're not connected to internet.",Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#172949")).setTextColor((int)Color.WHITE).show();


        }



       aboutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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


}
