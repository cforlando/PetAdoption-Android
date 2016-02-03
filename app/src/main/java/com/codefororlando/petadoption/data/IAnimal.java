package com.codefororlando.petadoption.data;

import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public interface IAnimal extends Parcelable {

    @NonNull
    Uri getUri();

    @NonNull
    String getTag();

}
