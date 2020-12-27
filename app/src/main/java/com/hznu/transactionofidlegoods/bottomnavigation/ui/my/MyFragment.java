package com.hznu.transactionofidlegoods.bottomnavigation.ui.my;

import android.content.Context;
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

public class MyFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "MyFragment";

    private MyViewModel myViewModel;
    private Button logoutButton;
    private Button showMyInfoDetailButton;
    private ImageView myCollectionImageView;
    private TextView myCollectionTextView;
    private TextView myUserNameTextView;
    private LinearLayout myUserInfoLinearLayout;
    private SharedPreferences userinfo;

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

        userinfo = getContext().getSharedPreferences(SharePreferencesUtils.USER_INFORMATION_FILE, MODE_PRIVATE);
        String localUserId = userinfo.getString("userId", null);
        String localUserLoginId = userinfo.getString("userLoginId", null);
        String localUserName = userinfo.getString("userName", null);
        String localUserPhoneNum = userinfo.getString("userPhoneNum", null);
        String localUserEmail = userinfo.getString("userEmail", null);
        String localUserRegisterDate = userinfo.getString("userRegisterDate", null);

        if (localUserName != null) {
            myUserNameTextView.setText(localUserName);
        }

        // 初始化用于美化UI的点击事件
        initUIClick(root);

        // 查看我收藏的事件绑定
        myCollectionImageView.setOnClickListener(this);
        myCollectionTextView.setOnClickListener(this);

        // 简单个人信息展示点击进入详细信息展示页
        myUserInfoLinearLayout.setOnClickListener(this);
        showMyInfoDetailButton.setOnClickListener(this);

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

    private void initUIClick(View view) {
        view.findViewById(R.id.iv_myOtherFunction01).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction01).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction02).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction02).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction03).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction03).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction04).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction04).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction05).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction05).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction06).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction06).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction07).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction07).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction08).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction08).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction09).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction09).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction10).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction10).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction11).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction11).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction12).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction12).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction13).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction13).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction14).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction14).setOnClickListener(this);
        view.findViewById(R.id.iv_myOtherFunction15).setOnClickListener(this);
        view.findViewById(R.id.tv_myOtherFunction15).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_myOtherFunction01:
            case R.id.tv_myOtherFunction01:
            case R.id.iv_myOtherFunction02:
            case R.id.tv_myOtherFunction02:
            case R.id.iv_myOtherFunction03:
            case R.id.tv_myOtherFunction03:
            case R.id.iv_myOtherFunction04:
            case R.id.tv_myOtherFunction04:
            case R.id.iv_myOtherFunction05:
            case R.id.tv_myOtherFunction05:
            case R.id.iv_myOtherFunction06:
            case R.id.tv_myOtherFunction06:
            case R.id.iv_myOtherFunction07:
            case R.id.tv_myOtherFunction07:
            case R.id.iv_myOtherFunction08:
            case R.id.tv_myOtherFunction08:
            case R.id.iv_myOtherFunction10:
            case R.id.tv_myOtherFunction10:
            case R.id.iv_myOtherFunction11:
            case R.id.tv_myOtherFunction11:
            case R.id.iv_myOtherFunction12:
            case R.id.tv_myOtherFunction12:
            case R.id.iv_myOtherFunction13:
            case R.id.tv_myOtherFunction13:
            case R.id.iv_myOtherFunction14:
            case R.id.tv_myOtherFunction14:
            case R.id.iv_myOtherFunction15:
            case R.id.tv_myOtherFunction15:
                Toast.makeText(getContext(), "You clicked me!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_showMyInfoDetail:
            case R.id.ly_myUserInfo:
                // 简单个人信息展示点击进入详细信息展示页
                intent = new Intent(getContext(), MyDeatilInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_myCollection:
            case R.id.iv_myCollection:
                intent = new Intent(getContext(), MyCollectedListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        // 更新完用户信息并退出详情页时，该活动的该Fragment处于栈顶显示，此时更新用户名保持信息一致性
        String localUserName = userinfo.getString("userName", null);

        if (localUserName != null) {
            myUserNameTextView.setText(localUserName);
        }
    }
}
