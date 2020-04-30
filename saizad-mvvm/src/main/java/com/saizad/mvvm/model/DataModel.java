package com.saizad.mvvm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel<M> {
    @Expose
    @SerializedName("data")
    public M data;
    @Expose
    @SerializedName("status")
    public int status;
}
