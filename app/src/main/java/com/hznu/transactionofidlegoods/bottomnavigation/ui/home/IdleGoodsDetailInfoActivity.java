package com.hznu.transactionofidlegoods.bottomnavigation.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.myview.MyTitleBar;
import com.hznu.transactionofidlegoods.utils.SharePreferencesUtils;

import java.util.Map;

public class IdleGoodsDetailInfoActivity extends AppCompatActivity {
    private MyTitleBar idleGoodsDetailInfoMyTitleBar;
    private WebView idleGoodsDetailInfoWebView;
    private SharedPreferences userinformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle_goods_detail_info);

        // 获取闲置物id
        Intent intent = getIntent();
        String goodsId = intent.getStringExtra("goodsId");

        // 获取用户id值
        userinformation = getSharedPreferences(SharePreferencesUtils.USER_INFORMATION_FILE, MODE_PRIVATE);
        String localUserId = userinformation.getString("userId", null);

        idleGoodsDetailInfoMyTitleBar = (MyTitleBar) findViewById(R.id.myTitleBar_idleGoodsDetailInfo);
        idleGoodsDetailInfoWebView = (WebView) findViewById(R.id.wv_idleGoodsDetailInfo);

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        // 更改顶部菜单栏标题
        idleGoodsDetailInfoMyTitleBar.setTvTitleText("闲置物详情");
        idleGoodsDetailInfoMyTitleBar.getTvForward().setVisibility(View.INVISIBLE);

        idleGoodsDetailInfoWebView.getSettings().setJavaScriptEnabled(true);
        idleGoodsDetailInfoWebView.setWebViewClient(new WebViewClient());
        String url = new StringBuilder("http://wowowowo.vipgz1.idcfengye.com/demo/android/idlegooddetail.jsp?goodsId=")
                .append(goodsId).append("&userId=").append(localUserId).toString();
        idleGoodsDetailInfoWebView.loadUrl(url);
    }
}