package com.codefororlando.petadoption.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by ryan on 11/7/17.
 */

public class LocationManager implements ILocationManager {

    Context context;
    GoogleApiClient client;

    public LocationManager(Context context) {
        this.context = context;
    }

    @Override
    public Single<String> getZipcode() {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(final SingleEmitter<String> e) throws Exception {
                client = new GoogleApiClient.Builder(context)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(createConnectionCallbacksWithEmitter(e))
                        .addOnConnectionFailedListener(createFailedListenerWithEmitter(e))
                        .build();

                client.connect();
            }
        });
    }

    private GoogleApiClient.ConnectionCallbacks createConnectionCallbacksWithEmitter(final SingleEmitter<String> e) {
        return new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                if(isLocationPermissionGranted()) {
                    String zipcode = getLastKnownZipcode();

                    if(zipcode != null) {
                        e.onSuccess(zipcode);
                    }
                }

                client.disconnect();
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.d("LocationManager", "Connection suspended");
                client.disconnect();
                e.onError(new Exception("Connection suspended"));
            }
        };
    }

    private boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private String getLastKnownZipcode() {
        @SuppressLint("MissingPermission") Location location = LocationServices.FusedLocationApi.getLastLocation(client);
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        String zipcode = null;

        if (location != null){
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                zipcode = addresses.get(0).getPostalCode();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return zipcode;
    }

    private GoogleApiClient.OnConnectionFailedListener createFailedListenerWithEmitter(final SingleEmitter<String> e) {
        return new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Log.d("LocationManager", connectionResult.getErrorMessage());
                client.disconnect();
                e.onError(new Exception("Connection Failed"));
            }
        };
    }
}
