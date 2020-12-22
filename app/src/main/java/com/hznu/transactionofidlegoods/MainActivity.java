package com.hznu.transactionofidlegoods;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.hznu.transactionofidlegoods.domain.IdleGoods;
import com.hznu.transactionofidlegoods.login.LoginActivity;
import com.hznu.transactionofidlegoods.myview.MyImageView;
import com.hznu.transactionofidlegoods.service.GetIdleGoodsInfoList;
import com.hznu.transactionofidlegoods.utils.BaseActivity;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏系统自带顶部状态栏
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Toast.makeText(MainActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
        startActivity(intent);

        /*List<IdleGoods> idleGoodsInfoList = GetIdleGoodsInfoList.getIdleGoodsInfoList();
        Log.d("IdleGoodsInfoList", idleGoodsInfoList.size() + "");

        for (IdleGoods goods : idleGoodsInfoList) {
            Log.d("IdleGoodsInfoList", goods.getGoodsCoverImgDir());
        }*/

        /*MyImageView idlePropertyImgMyImageView = (MyImageView) findViewById(R.id.mv_idleGoodsImg);

        TextView idlePropertyTitleTextView = (TextView) findViewById(R.id.tv_idleGoodsTitle);
        TextView idlePropertyPersonTextView = (TextView) findViewById(R.id.tv_idleGoodsPerson);
        TextView idlePropertyLocationTextView = (TextView) findViewById(R.id.tv_idleGoodsLocation);
        TextView idlePropertyPriceTextView = (TextView) findViewById(R.id.tv_idleGoodsPrice);

        idlePropertyImgMyImageView.setImageURL(idleGoodsInfoList.get(0).getGoodsCoverImgDir());
        idlePropertyTitleTextView.setText(idleGoodsInfoList.get(0).getGoodsName());
        idlePropertyPersonTextView.setText(idleGoodsInfoList.get(0).getUser().getUserName());
        idlePropertyLocationTextView.setText(idleGoodsInfoList.get(0).getGoodsProvince());
        idlePropertyPriceTextView.setText(idleGoodsInfoList.get(0).getGoodsPrice() + "");*/
    }

}