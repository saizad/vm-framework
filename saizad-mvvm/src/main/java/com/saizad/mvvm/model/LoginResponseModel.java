package com.saizad.mvvm.model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel extends UserInfo implements Parcelable {

    @SerializedName("token")
    private Token token;

    @SerializedName("user")
    private UserInfo user;


    public Token getToken() {
        return token;
    }

    public UserInfo getUser() {
        return user;
    }
}
