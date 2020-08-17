package com.saizad.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class NameModel extends IdModel implements Parcelable  {

    @SerializedName("name")
    public @NonNull String name;

    public NameModel(@NonNull String name) {
        this.name = name;
    }

    public NameModel(int id, @NonNull String name) {
        super(id);
        this.name = name;
    }

    protected NameModel(Parcel in) {
        super(in);
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NameModel> CREATOR = new Creator<NameModel>() {
        @Override
        public NameModel createFromParcel(Parcel in) {
            return new NameModel(in);
        }

        @Override
        public NameModel[] newArray(int size) {
            return new NameModel[size];
        }
    };
}