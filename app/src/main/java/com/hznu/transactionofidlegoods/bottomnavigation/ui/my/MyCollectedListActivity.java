package com.hznu.transactionofidlegoods.bottomnavigation.ui.my;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.domain.IdleGoods;
import com.hznu.transactionofidlegoods.myview.MyTitleBar;
import com.hznu.transactionofidlegoods.service.GetMyCollectedList;
import com.hznu.transactionofidlegoods.utils.IdleGoodsAdapter;
import com.hznu.transactionofidlegoods.utils.MyCollectedListAdapter;
import com.hznu.transactionofidlegoods.utils.SharePreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class MyCollectedListActivity extends AppCompatActivity {
    //闲置物列表
    public List<IdleGoods> idleGoodsInfoList;
    private RecyclerView myCollectedListRecyclerView;

    private MyTitleBar myCollectedMyTitleBar;
    private SharedPreferences userinformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collected_list);

        // 获取userId
        userinformation = getSharedPreferences(SharePreferencesUtils.USER_INFORMATION_FILE, MODE_PRIVATE);
        String localUserId = userinformation.getString("userId", null);

        myCollectedMyTitleBar = (MyTitleBar) findViewById(R.id.myTitleBar_myCollected);

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        myCollectedMyTitleBar.setTvTitleText("收藏夹");
        myCollectedMyTitleBar.getTvForward().setVisibility(View.INVISIBLE);

        if (localUserId != null) {
            idleGoodsInfoList = GetMyCollectedList.getMyCollectedList(localUserId);
            if (idleGoodsInfoList == null) {
                idleGoodsInfoList = new ArrayList<>();
            }

            Log.d("GetMyCollectedList", idleGoodsInfoList.toString());
            myCollectedListRecyclerView = (RecyclerView) findViewById(R.id.rv_myCollectedList);
            LinearLayoutManager manager = new LinearLayoutManager(MyCollectedListActivity.this);
            myCollectedListRecyclerView.setLayoutManager(manager);
            MyCollectedListAdapter myCollectedListAdapter = new MyCollectedListAdapter(idleGoodsInfoList);

            myCollectedListRecyclerView.setAdapter(myCollectedListAdapter);
        }

    }
}