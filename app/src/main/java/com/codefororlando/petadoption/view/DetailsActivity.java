package com.codefororlando.petadoption.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.data.model.Location;
import com.codefororlando.petadoption.data.model.Shelter;
import com.codefororlando.petadoption.presenter.DetailsPresenter;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Animal details view
 */
@RequiresPresenter(DetailsPresenter.class)
public class DetailsActivity extends NucleusAppCompatActivity<DetailsPresenter> implements View.OnClickListener {

    private ImageView imageViewAnimal;
    private TextView textViewGender;
    private TextView textViewAge;
    private TextView textViewSize;
    private TextView textViewLocation;
    private TextView textViewDescription;
    private TextView textViewLocationName;
    private TextView textViewLocationStreet;
    private TextView textViewCityStateZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ((PetApplication) getApplication()).appComponent()
                .inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageViewAnimal = (ImageView) findViewById(R.id.details_animal_image);
        textViewGender = (TextView) findViewById(R.id.details_gender);
        textViewAge = (TextView) findViewById(R.id.details_age);
        textViewSize = (TextView) findViewById(R.id.details_size);
        textViewLocation = (TextView) findViewById(R.id.details_location);
        textViewDescription = (TextView) findViewById(R.id.details_description);
        textViewLocationName = (TextView) findViewById(R.id.details_location_name);
        textViewLocationStreet = (TextView) findViewById(R.id.details_location_street);
        textViewCityStateZip = (TextView) findViewById(R.id.details_location_city_state_zip);
        bindActions();
    }

    private void bindActions() {
        findViewById(R.id.details_action_call)
                .setOnClickListener(this);
        findViewById(R.id.details_action_email)
                .setOnClickListener(this);
        findViewById(R.id.details_action_web)
                .setOnClickListener(this);
    }

    public void setAnimal(Animal animal) {
        textViewAge.setText(animal.getAge());
        textViewGender.setText(animal.getGender());
        textViewSize.setText(null);
        textViewDescription.setText(animal.getDescription());
        getSupportActionBar().setTitle(animal.getName());
    }

    public void setShelter(Shelter shelter) {
        Location location = shelter.getLocation();
        textViewLocation.setText(location.getCity());
        textViewLocationName.setText(location.getAddressName());
        textViewLocationStreet.setText(location.getPrimaryStreetAddress());

        String cityStateZip = String.format("%s %s, %s", location.getCity(), location.getState(), location.getZipCode());
        textViewCityStateZip.setText(cityStateZip);
    }

    public void call(Uri phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(phoneNumber);
        startActivity(callIntent, R.string.info_intent_error_no_dialer);
    }

    public void email(Bundle extras) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtras(extras);
        emailIntent.setType("plain/text");
        startActivity(emailIntent, R.string.info_intent_error_no_email);
    }

    public void openWebsite(Uri webAddress) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(webAddress);
        startActivity(webIntent, R.string.info_intent_error_no_browser);
    }

    /**
     * Start an activity or show an error.
     *
     * @param intent         activity intent
     * @param onErrorMessage message to display if starting the activity fails
     */
    private void startActivity(Intent intent, @StringRes int onErrorMessage) {
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, onErrorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        DetailsPresenter presenter = getPresenter();
        switch (v.getId()) {
            case R.id.details_action_call:
                presenter.initiateCall();
                break;
            case R.id.details_action_email:
                presenter.initiateOpenEmail();
                break;
            case R.id.details_action_web:
                presenter.initiateOpenWebsite();
                break;
            default:
                throw new IllegalArgumentException("Unhandled click for " + v);
        }
    }
}
