package com.saizad.mvvm;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;

public class ObjectPreference extends SaizadSharedPreferences {


    public ObjectPreference(SharedPreferences sharedPreferences, Gson gson) {
        super(sharedPreferences, gson);
    }

    public void saveDraft(@Nullable Object object, @NonNull String key){
        putSharedPrefObject(key, object);
    }

    public <T> T getDraft(@NonNull String key, Class<T> type){
        return getSharedPrefObject(key, type);
    }

    public void remove(@NonNull String key){
        removeValue(key);
    }
}
