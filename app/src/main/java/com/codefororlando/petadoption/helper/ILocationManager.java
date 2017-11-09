package com.codefororlando.petadoption.helper;

import io.reactivex.Single;

/**
 * Created by ryan on 11/7/17.
 */

public interface ILocationManager {
    Single<String> getZipcode();
}
