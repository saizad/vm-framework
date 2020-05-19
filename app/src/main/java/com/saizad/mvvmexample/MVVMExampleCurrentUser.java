package com.saizad.mvvmexample;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.saizad.mvvm.CurrentUserType;
import com.saizad.mvvm.model.UserInfo;

public class MVVMExampleCurrentUser extends CurrentUserType<UserInfo> {

    public MVVMExampleCurrentUser(SharedPreferences sharedPreferences, Gson gson) {
        super(new UserInfo(), sharedPreferences, gson);
    }

    @Override
    protected Class<UserInfo> getClassType() {
        return UserInfo.class;
    }

    @Nullable
    @Override
    public String getToken() {
        return null;
    }
}
