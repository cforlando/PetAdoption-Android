package com.codefororlando.petadoption.presenter.list;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.helper.ILocationManager;
import com.codefororlando.petadoption.helper.IPreferencesHelper;
import com.codefororlando.petadoption.view.LocationDialogFragment;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ryan on 11/6/17.
 */

public class LocationDialogPresenter {

    @Inject
    IPreferencesHelper preferencesHelper;

    @Inject
    ILocationManager locationManager;

    LocationDialogFragment view;

    public LocationDialogPresenter(LocationDialogFragment view) {
        ((PetApplication) view.getActivity().getApplication()).appComponent()
                .inject(this);

        this.view = view;
    }

    public void setLocation(String zipcode) {
        preferencesHelper.setLocation(zipcode);
    }

    public String getLocation() {
        return preferencesHelper.getLocation();
    }

    public void fetchCurrentLocation() {
        locationManager.getZipcode()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                view.setEnteredZip(s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }



}
