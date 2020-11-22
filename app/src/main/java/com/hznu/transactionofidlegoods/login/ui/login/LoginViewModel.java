package com.hznu.transactionofidlegoods.login.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hznu.transactionofidlegoods.R;
import com.hznu.transactionofidlegoods.login.data.LoginRepository;
import com.hznu.transactionofidlegoods.login.data.Result;
import com.hznu.transactionofidlegoods.login.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    //MutableLiveData,一个观察者模式的数据实体类,它可以跟它注册的观察者回调数据是否已经更新
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    //检测用户名的合法性
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {  //若为邮箱检测其正确性
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {  //否则为电话号码
//            return Patterns.PHONE.matcher(username).matches();
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    //检测密码的合法性
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}