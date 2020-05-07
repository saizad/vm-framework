package com.saizad.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sa.easyandroidfrom.fields.MandatoryStringField;
import com.sa.easyandroidfrom.fields.PasswordField;
import com.sa.easyandroidfrom.form.FormModel;

import static java.util.Arrays.asList;

public class LoginBody implements Parcelable {

    @Expose
    @SerializedName("password")
    public final String password;
    @Expose
    @SerializedName("username")
    public final String username;


    public LoginBody(String password, String username) {
        this.password = password;
        this.username = username;
    }

    protected LoginBody(Parcel in) {
        password = in.readString();
        username = in.readString();
    }

    public static final Creator<LoginBody> CREATOR = new Creator<LoginBody>() {
        @Override
        public LoginBody createFromParcel(Parcel in) {
            return new LoginBody(in);
        }

        @Override
        public LoginBody[] newArray(int size) {
            return new LoginBody[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(password);
        dest.writeString(username);
    }

    public static class Form extends FormModel<LoginBody> {

        public final PasswordField passwordField;
        public final MandatoryStringField usernameField;

        public Form() {
            super(asList(new MandatoryStringField("username"), new PasswordField("password")));

            passwordField = getField("password");
            usernameField = getField("username");
        }

        @NonNull
        @Override
        protected LoginBody buildForm() {
            return new LoginBody(passwordField.getField(), usernameField.getField());
        }
    }
}
