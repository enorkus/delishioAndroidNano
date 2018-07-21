package com.enorkus.delishio.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Picture implements Parcelable {

    private String largeImageURL;
    private String webformatURL;

    protected Picture(Parcel in) {
        largeImageURL = in.readString();
        webformatURL = in.readString();
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(largeImageURL);
        parcel.writeString(webformatURL);
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public String getWebformatURL() {
        return webformatURL;
    }
}
