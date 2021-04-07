package com.vm.framework;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class BaseNotificationModel implements Parcelable {

    @SerializedName("timestamp")
    private String timeStamp;

    @SerializedName("created_timestamp")
    private String createdTimestamp;

    @SerializedName("type")
    private String type;

    @SerializedName("notification_image")
    private String imageUrl;

    @SerializedName("body")
    private String body;

    @SerializedName("title")
    private String title;

    @SerializedName("read")
    private @Nullable
    Boolean read;

    protected BaseNotificationModel(Parcel in) {
        timeStamp = in.readString();
        createdTimestamp = in.readString();
        type = in.readString();
        imageUrl = in.readString();
        body = in.readString();
        title = in.readString();
        byte tmpRead = in.readByte();
        read = tmpRead == 0 ? null : tmpRead == 1;
    }

    @CallSuper
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timeStamp);
        dest.writeString(createdTimestamp);
        dest.writeString(type);
        dest.writeString(imageUrl);
        dest.writeString(body);
        dest.writeString(title);
        dest.writeByte((byte) (read == null ? 0 : read ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseNotificationModel> CREATOR = new Creator<BaseNotificationModel>() {
        @Override
        public BaseNotificationModel createFromParcel(Parcel in) {
            return new BaseNotificationModel(in);
        }

        @Override
        public BaseNotificationModel[] newArray(int size) {
            return new BaseNotificationModel[size];
        }
    };

    public String getType() {
        return type;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    public Boolean isRead() {
        return read;
    }

    public int id(){
        return 0;
    }
}
