package com.example.pomodoro.ui.bucket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BucketViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public BucketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is about fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
