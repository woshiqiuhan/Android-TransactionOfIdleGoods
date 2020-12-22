package com.hznu.transactionofidlegoods.bottomnavigation.ui.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.login.LoginActivity;
import com.hznu.transactionofidlegoods.utils.SharePreferencesUtils;

import static android.content.Context.MODE_PRIVATE;

public class MyFragment extends Fragment {

    private MyViewModel myViewModel;
    private Button logoutButton;
    private Button showMyInfoDetailButton;
    private ImageView myCollectionImageView;
    private TextView myCollectionTextView;
    private TextView myUserNameTextView;
    private LinearLayout myUserInfoLinearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my, container, false);

        showMyInfoDetailButton = (Button) root.findViewById(R.id.btn_showMyInfoDetail);
        logoutButton = (Button) root.findViewById(R.id.btn_logout);
        myCollectionImageView = (ImageView) root.findViewById(R.id.iv_myCollection);
        myCollectionTextView = (TextView) root.findViewById(R.id.tv_myCollection);
        myUserNameTextView = (TextView) root.findViewById(R.id.tv_myUserName);
        myUserInfoLinearLayout = (LinearLayout) root.findViewById(R.id.ly_myUserInfo);

        SharedPreferences userinfo = getContext().getSharedPreferences(SharePreferencesUtils.USER_INFORMATION_FILE, MODE_PRIVATE);
        String localUserId = userinfo.getString("userId", null);
        String localUserLoginId = userinfo.getString("userLoginId", null);
        String localUserName = userinfo.getString("userName", null);
        String localUserPhoneNum = userinfo.getString("userPhoneNum", null);
        String localUserEmail = userinfo.getString("userEmail", null);
        String localUserRegisterDate = userinfo.getString("userRegisterDate", null);

        if (localUserName != null) {
            myUserNameTextView.setText(localUserName);
        }

        // 查看我收藏的事件绑定
        myCollectionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You clicked me", Toast.LENGTH_SHORT).show();
            }
        });
        myCollectionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You clicked me", Toast.LENGTH_SHORT).show();
            }
        });

        // 简单个人信息展示点击进入详细信息展示页
        myUserInfoLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You clicked me", Toast.LENGTH_SHORT).show();
            }
        });

        // 查看详情信息事件绑定
        showMyInfoDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You clicked me", Toast.LENGTH_SHORT).show();
            }
        });

        // 退出登录事件绑定
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferencesUtils.clear(getContext(), SharePreferencesUtils.USER_INFORMATION_FILE);

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "退出登录", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}