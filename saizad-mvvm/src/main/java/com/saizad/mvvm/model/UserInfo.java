package com.saizad.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserInfo extends BaseModel implements Parcelable {

    @Expose
    @SerializedName("mobile")
    public String mobile;
    @Expose
    @SerializedName("email")
    public String email;
    @Expose
    @SerializedName("user_name")
    public String userName;

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        super(in);
        mobile = in.readString();
        email = in.readString();
        userName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(userName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
