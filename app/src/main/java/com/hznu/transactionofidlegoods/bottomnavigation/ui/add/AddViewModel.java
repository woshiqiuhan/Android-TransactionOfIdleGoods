package com.hznu.transactionofidlegoods.bottomnavigation.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("这里是发布闲置品界面！");
    }

    public LiveData<String> getText() {
        return mText;
    }
}