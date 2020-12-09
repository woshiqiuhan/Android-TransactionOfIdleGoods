package com.hznu.transactionofidlegoods.bottomnavigation.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.login.LoginActivity;
import com.hznu.transactionofidlegoods.utils.SharePreferencesUtils;

public class MyFragment extends Fragment {

    private MyViewModel myViewModel;
    private Button logoutButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myViewModel =
                new ViewModelProvider(this).get(MyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my, container, false);

        logoutButton = (Button) root.findViewById(R.id.btn_logout);

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