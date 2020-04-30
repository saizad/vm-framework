package com.saizad.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Token implements Parcelable {

	@SerializedName("access")
	private String access;

	@SerializedName("refresh")
	private String refresh;

	protected Token(Parcel in) {
		access = in.readString();
		refresh = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(access);
		dest.writeString(refresh);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Token> CREATOR = new Creator<Token>() {
		@Override
		public Token createFromParcel(Parcel in) {
			return new Token(in);
		}

		@Override
		public Token[] newArray(int size) {
			return new Token[size];
		}
	};

	public String getAccess() {
		return access;
	}

	public String getRefresh() {
		return refresh;
	}

}