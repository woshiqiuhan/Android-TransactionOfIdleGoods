package com.hznu.transactionofidlegoods.login.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 * 登录表单的数据验证状态
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;  //输入用户名是否有误
    @Nullable
    private Integer passwordError; //输入密码是否有误
    private boolean isDataValid;  //输入信息是否有误

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}