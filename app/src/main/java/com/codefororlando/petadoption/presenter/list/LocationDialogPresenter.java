package com.codefororlando.petadoption.presenter.list;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.helper.IPreferencesHelper;
import com.codefororlando.petadoption.view.LocationDialogFragment;

import javax.inject.Inject;

/**
 * Created by ryan on 11/6/17.
 */

public class LocationDialogPresenter {

    @Inject
    IPreferencesHelper preferencesHelper;

    @Inject
    ILocationManager locationManager;

    public LocationDialogPresenter(LocationDialogFragment view) {
        ((PetApplication) view.getActivity().getApplication()).appComponent()
                .inject(this);
    }

    public void setLocation(String zipcode) {
        preferencesHelper.setLocation(zipcode);
    }

    public String getLocation() {
        return preferencesHelper.getLocation();
    }

    public String fetchLocation() {

    }
}
