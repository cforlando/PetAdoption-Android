package com.codefororlando.petadoption.view;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.presenter.list.LocationDialogPresenter;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ryan on 11/6/17.
 */

public class LocationDialogFragment extends DialogFragment  {

    private EditText locationEditText;
    private ImageButton findLocationButton;

    private LocationDialogPresenter presenter;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private PublishSubject dismissalSubject = PublishSubject.create();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
        presenter = new LocationDialogPresenter(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View layout = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_location, null);

        locationEditText = (EditText) layout.findViewById(R.id.location_edit_text);
        findLocationButton = (ImageButton) layout.findViewById(R.id.location_button);

        locationEditText.setText(presenter.getLocation());
        int textLength = locationEditText.getText().toString().length();
        locationEditText.setSelection(textLength);

        findLocationButton.setOnClickListener(v -> {
            if (isLocationPermissionGranted()) {
                populateWithCurrentLocation();
            } else {
                requestLocationPermission();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setTitle("Set Location")
                .setPositiveButton("OK",
                        (dialog, which) -> {
                            presenter.setLocation(getEnteredZip());
                            dismissalSubject.onNext(true);
                            dialog.dismiss();
                        })
                .setNegativeButton("Cancel",
                        (dialog, which) -> dialog.dismiss())
                .setView(layout)
                .create();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if(isLocationPermissionGranted()) {
                    populateWithCurrentLocation();
                }
            }
        }
    }

    @NonNull
    private String getEnteredZip() {
        return locationEditText.getText().toString();
    }

    private boolean isLocationPermissionGranted() {
        int check = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);

        return check == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void populateWithCurrentLocation(){
        presenter.fetchCurrentLocation();
    }

    public void setEnteredZip(String zip) {
        locationEditText.setText(zip);
    }

    public Observable<Boolean> getShouldRefreshFeedOnDismissalObservable() {
        return dismissalSubject;
    }

}
