package com.codefororlando.petadoption.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.presenter.DetailsPresenter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ((PetApplication) getApplication()).appComponent()
                .inject(this);

        imageViewAnimal = (ImageView) findViewById(R.id.details_animal_image);
        textViewGender = (TextView) findViewById(R.id.details_gender);
        textViewAge = (TextView) findViewById(R.id.details_age);
        textViewSize = (TextView) findViewById(R.id.details_size);
        textViewLocation = (TextView) findViewById(R.id.details_location);
        textViewDescription = (TextView) findViewById(R.id.details_description);
        textViewLocationName = (TextView) findViewById(R.id.details_location_name);
        textViewLocationStreet = (TextView) findViewById(R.id.details_location_street);
        textViewCityStateZip = (TextView) findViewById(R.id.details_location_city_state_zip);
    }

    public void setAnimal(Animal animal) {
        textViewAge.setText(animal.age);
        textViewGender.setText(animal.gender);
        textViewSize.setText(null);
        textViewLocation.setText(null);
        textViewDescription.setText(animal.description);
        textViewLocationName.setText(null);
        textViewLocationStreet.setText(null);
        textViewCityStateZip.setText(null);
    }

}
