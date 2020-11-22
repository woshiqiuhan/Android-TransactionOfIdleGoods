package com.hznu.transactionofidlegoods.bottomnavigation.ui.my;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("这里是个人信息界面！");
    }

    public LiveData<String> getText() {
        return mText;
    }
}