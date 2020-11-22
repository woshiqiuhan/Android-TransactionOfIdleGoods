package com.hznu.transactionofidlegoods.bottomnavigation.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hznu.transactionofidlegoods.utils.FilePersistenceIO;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    // 数据的列表，将搜索记录存储到安卓本地
    private List<String> searchRecords;

    public HomeViewModel() {
    }

    //从文件中读取搜索记录
    public List<String> getSearchRecords(Context context) {
        initSearchRecords(context);
        return this.searchRecords;
    }

    private void initSearchRecords(Context context) {
        try {
            searchRecords = new LinkedList<>(Arrays.asList(FilePersistenceIO.load(context, "searchrecords").split("\n")));
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    //添加记录
    public void addSearchRecord(String str) {
        searchRecords.add(0, str);
    }

    //删除一条记录
    public void removeSearchRecord(String str) {
        searchRecords.remove(str);
    }
}