package com.example.straycaregsc.Models;

import java.util.ArrayList;

public class UserModel {
    String userName;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    String userID;
    String userMID;

    public String getUserMID() {
        return userMID;
    }

    public void setUserMID(String userMID) {
        this.userMID = userMID;
    }

    String email;
    String passWord;
    String contactNo;



    public UserModel() {
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
