package com.saizad.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Logout implements Parcelable {

	@SerializedName("refresh")
	private final String refresh;

	public Logout(String refresh) {
		this.refresh = refresh;
	}

	protected Logout(Parcel in) {
		refresh = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(refresh);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Logout> CREATOR = new Creator<Logout>() {
		@Override
		public Logout createFromParcel(Parcel in) {
			return new Logout(in);
		}

		@Override
		public Logout[] newArray(int size) {
			return new Logout[size];
		}
	};
}