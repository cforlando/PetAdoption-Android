package com.codefororlando.petadoption.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.AnimalViewModel;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.data.model.Location;
import com.codefororlando.petadoption.data.model.Shelter;
import com.codefororlando.petadoption.presenter.details.DetailsPresenter;
import com.squareup.picasso.Picasso;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Animal details view
 */
@RequiresPresenter(DetailsPresenter.class)
public class DetailsActivity extends NucleusAppCompatActivity<DetailsPresenter> {

    private ImageView imageViewAnimal;
    private TextView textViewGender;
    private TextView textViewAge;
    private TextView textViewSize;
    private TextView textViewLocation;
    private TextView textViewDescription;
    private TextView textViewLocationName;
    private TextView textViewLocationStreet;
    private TextView textViewCityStateZip;
    private LinearLayout callActionView;
    private LinearLayout webActionView;
    private LinearLayout emailActionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ((PetApplication) getApplication()).appComponent()
                .inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        imageViewAnimal = (ImageView) findViewById(R.id.details_animal_image);
        textViewGender = (TextView) findViewById(R.id.details_gender);
        textViewAge = (TextView) findViewById(R.id.details_age);
        textViewSize = (TextView) findViewById(R.id.details_size);
        textViewLocation = (TextView) findViewById(R.id.details_location);
        textViewDescription = (TextView) findViewById(R.id.details_description);
        textViewLocationName = (TextView) findViewById(R.id.details_location_name);
        textViewLocationStreet = (TextView) findViewById(R.id.details_location_street);
        textViewCityStateZip = (TextView) findViewById(R.id.details_location_city_state_zip);
        callActionView = (LinearLayout) findViewById(R.id.details_action_call);
        webActionView = (LinearLayout) findViewById(R.id.details_action_web);
        emailActionView = (LinearLayout) findViewById(R.id.details_action_email);

        setDefaultState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setActionClickListener(View.OnClickListener onClickListener) {
        callActionView.setOnClickListener(onClickListener);
        emailActionView.setOnClickListener(onClickListener);
        webActionView.setOnClickListener(onClickListener);
    }

    public void setAnimal(AnimalViewModel animalViewModel) {
        Animal animal = animalViewModel.getAnimal();
        textViewAge.setText(animal.getAge());
        textViewGender.setText(animal.getGender());
        textViewSize.setText(null);
        textViewDescription.setText(animal.getDescription());

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(animal.getName());
        }

        Picasso.with(this)
                .load(animalViewModel.getDefaultImageUrl())
                .resize(1000, 1000)
                .onlyScaleDown()
                .centerCrop()
                .placeholder(animalViewModel.placeholderImageResource())
                .into(imageViewAnimal);
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

    public void hideCallAction(){
        callActionView.setVisibility(View.INVISIBLE);
    }

    public void showCallAction(){
        callActionView.setVisibility(View.VISIBLE);
    }

    public void hideWebAction(){
        webActionView.setVisibility(View.INVISIBLE);
    }

    public void showWebAction(){
        webActionView.setVisibility(View.VISIBLE);
    }

    public void hideEmailAction(){
        emailActionView.setVisibility(View.INVISIBLE);
    }

    public void showEmailAction(){
        emailActionView.setVisibility(View.VISIBLE);
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

    private void setDefaultState(){
        hideCallAction();
        hideWebAction();
        hideEmailAction();
    }

}
