package com.hznu.transactionofidlegoods.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用与存储用户信息及注销时删除用户信息
 */
public class SharePreferencesUtils {

    //存储用户用户名密码的共享文件
    public static final  String USER_INFORMATION_FILE = "userinformation";

    //登录时保存信息
    public static void save(Context context, String username, String passwordm, String fileName) {
        //将用户名密码缓存至本地共享文件
        SharedPreferences.Editor edit = context.getSharedPreferences(fileName, context.MODE_PRIVATE).edit();
        edit.putString("username", "admin");
        edit.putString("password", "123456");
        edit.apply();
    }

    //注销时删除信息
    public static void clear(Context context, String fileName) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();

        editor.commit();
    }
}
