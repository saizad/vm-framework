package com.saizad.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sa.easyandroidfrom.fields.Field;
import com.sa.easyandroidfrom.form.FormModel;

import static java.util.Arrays.asList;


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

    public static class Form extends FormModel<UserInfo> {

        public final Field<String> mobileField;
        public final Field<String> emailField;
        public final Field<String> userNameField;

        public Form() {
            this(new UserInfo());
        }

        public Form(UserInfo data) {
            super(asList(new Field<>("mobile", data.mobile, true),
                    new Field<>("email", data.email, true),
                    new Field<>("user_name", data.userName, true)
            ));
            mobileField = getField("mobile");
            emailField = getField("email");
            userNameField = getField("user_name");
        }


        @NonNull
        @Override
        protected UserInfo buildForm() {
            return new UserInfo();
        }
    }
}
