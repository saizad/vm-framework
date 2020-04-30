package com.saizad.mvvm;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

public abstract class SaizadSharedPreferences {

    private static final String VALUE_PLACE_HOLDER = "empty";
    protected SharedPreferences sharedPreferences;
    private Gson gson;

    public SaizadSharedPreferences(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    final protected void putSharedPrefObject(String prefKey, Object object) {
        sharedPreferences.edit().putString(prefKey, gson.toJson(object)).apply();
    }

    @Nullable
    final protected <T> T getSharedPrefObject(String prefKey, Class<T> classOfT) {
        return getSharedPrefObject(prefKey, classOfT, null);
    }

    final protected <T> T getSharedPrefObject(String prefKey, Class<T> classOfT, @Nullable T defValue) {
        String jsonValue = sharedPreferences.getString(prefKey, null);
        return jsonValue == null ? defValue : gson.fromJson(jsonValue, classOfT);
    }

    @Nullable
    final protected String getValue(String prefKey) {
        final String value = getValue(prefKey, VALUE_PLACE_HOLDER);
        if (value.equalsIgnoreCase(VALUE_PLACE_HOLDER)) {
            return null;
        }
        return value;
    }

    @NonNull
    final protected String getValue(String prefKey, String defValue) {
        return sharedPreferences.getString(prefKey, defValue);
    }

    final protected void putValue(String prefKey, String value) {
        sharedPreferences.edit().putString(prefKey, value).apply();
    }

    final protected void removeValue(String key) {
        sharedPreferences.edit().remove(key).apply();
    }
}
