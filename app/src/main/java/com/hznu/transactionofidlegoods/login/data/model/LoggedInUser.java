package com.hznu.transactionofidlegoods.login.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 * <p>
 * 数据类，为从LoginRepository检索的已登录用户捕获用户信息
 */
public class LoggedInUser {

    private String userId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}