package com.hznu.transactionofidlegoods.login.data;

import com.hznu.transactionofidlegoods.login.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 * <p>
 * 该类处理带登录凭据的身份验证并检索用户信息
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            // 处理loggedInUser身份验证
            if (username.equals("admin") && password.equals("123456")) {
                return new Result.Success<>(new LoggedInUser(
                        java.util.UUID.randomUUID().toString(),
                        "Admin"));
            } else {
                return new Result.Error(new IOException("Error logging in"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}