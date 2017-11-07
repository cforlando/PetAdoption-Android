package com.codefororlando.petadoption.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import javax.inject.Inject;

/**
 * Created by ryan on 11/7/17.
 */

public class PreferencesHelper implements IPreferencesHelper {

    private static final String LOCATION_KEY = "location";
    private static SharedPreferences sharedPreferences;

    public PreferencesHelper(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public void setLocation(String zipcode) {
        Log.d("Writing", zipcode);
        sharedPreferences.edit()
        .putString(LOCATION_KEY, zipcode)
        .apply();
    }

    @Override
    public String getLocation() {
        String toReturn = sharedPreferences.getString(LOCATION_KEY, "");
        Log.d("Reading", toReturn);
        return toReturn;
    }

}
