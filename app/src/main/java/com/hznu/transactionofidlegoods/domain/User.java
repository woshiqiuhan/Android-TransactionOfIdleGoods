package com.hznu.transactionofidlegoods.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class User {

    private String userId;
    private String userLoginId;
    private int userRole;
    private String userName;
    private String userPassword;
    private String userPhoneNum;

    public User(String userLoginId, String userPassword) {
        this.userLoginId = userLoginId;
        this.userPassword = userPassword;
    }

    private String userEmail;
    private Timestamp userRegisterDate;
    private int userStatus;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userLoginId='" + userLoginId + '\'' +
                ", userRole=" + userRole +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhoneNum='" + userPhoneNum + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userRegisterDate=" + userRegisterDate +
                ", userStatus=" + userStatus +
                '}';
    }

    public User(String userLoginId, int userRole, String userName, String userPassword, String userPhoneNum, String userEmail, int userStatus) {
        this.userId = createUserId();
        this.userLoginId = userLoginId;
        this.userRole = userRole;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhoneNum = userPhoneNum;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
    }

    public User(Map<Object, Object> mp) {
        this.userId = (String) mp.get("userId");
        this.userLoginId = (String) mp.get("userLoginId");
        this.userPassword = (String) mp.get("userPassword");
        this.userRole = (Integer) mp.get("userRole");
        this.userName = (String) mp.get("userName");
        this.userPhoneNum = (String) mp.get("userPhoneNum");
        this.userRegisterDate = new Timestamp((Long) mp.get("userRegisterDate"));
        this.userEmail = (String) mp.get("userEmail");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Timestamp getUserRegisterDate() {
        return userRegisterDate;
    }

    public void setUserRegisterDate(Timestamp userRegisterDate) {
        this.userRegisterDate = userRegisterDate;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public User() {
    }

    private String createUserId() {
        return "u_" + (new Date()).getTime();
    }
}
