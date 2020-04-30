package com.saizad.mvvm.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FCMTokenBody extends NameModel {

    @Expose
    @SerializedName("type")
    private final String type = "android";
    @Expose
    @SerializedName("active")
    private final boolean active;
    @Expose
    @SerializedName("device_id")
    private final String deviceId;
    @Expose
    @SerializedName("registration_id")
    private final String registrationId;

    public FCMTokenBody(@NonNull String name, String deviceId, String registrationId) {
        this(name, deviceId, registrationId, true);
    }

    public FCMTokenBody(@NonNull String name, String deviceId, String registrationId, boolean active) {
        super(name);
        this.active = active;
        this.deviceId = deviceId;
        this.registrationId = registrationId;
    }
}
