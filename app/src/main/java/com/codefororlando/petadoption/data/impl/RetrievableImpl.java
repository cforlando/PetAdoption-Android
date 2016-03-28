package com.codefororlando.petadoption.data.impl;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.codefororlando.petadoption.data.IRetrievable;

public class RetrievableImpl implements IRetrievable {

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };
    @NonNull
    private final Uri uri;

    public RetrievableImpl(@NonNull Uri uri) {
        this.uri = uri;
    }

    protected RetrievableImpl(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    @NonNull
    public Uri getUri() {
        return uri;
    }

    @Override
    @NonNull
    public String getTag() {
        return uri.toString();
    }
}
