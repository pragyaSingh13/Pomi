package com.example.pomodoro.ui.about;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pomodoro.R;

public class AboutViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is about fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
