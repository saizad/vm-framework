package com.saizad.mvvm;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import com.saizad.mvvm.model.Token;
import com.saizad.mvvm.model.UserInfo;

public class CurrentUser extends CurrentUserType<UserInfo> {

    private static final String AUTH_TOKEN = "auth_token";

    public CurrentUser(SharedPreferences sharedPreferences, Gson gson) {
        super(new UserInfo(), sharedPreferences, gson);
    }

    @Override
    protected Class<UserInfo> getClassType() {
        return UserInfo.class;
    }

    public void setToken(@Nullable Token token) {
        putSharedPrefObject(AUTH_TOKEN, token);
    }

    public Token getAuthToken() {
        return getSharedPrefObject(AUTH_TOKEN, Token.class);
    }
}
