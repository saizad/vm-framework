package com.saizad.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.CallSuper;

import com.google.gson.annotations.SerializedName;

public class IdModel implements Parcelable {
    @SerializedName("id")
    public int id;

    public IdModel(int id) {
        this.id = id;
    }

    protected IdModel(){}

    protected IdModel(Parcel in) {
        id = in.readInt();
    }

    @Override
    @CallSuper
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IdModel> CREATOR = new Creator<IdModel>() {
        @Override
        public IdModel createFromParcel(Parcel in) {
            return new IdModel(in);
        }

        @Override
        public IdModel[] newArray(int size) {
            return new IdModel[size];
        }
    };
}