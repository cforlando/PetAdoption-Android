package com.codefororlando.petadoption.view;

import android.os.Bundle;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.presenter.DetailsPresenter;

import nucleus.view.NucleusAppCompatActivity;

/**
 * Animal details view
 */
public class DetailsActivity extends NucleusAppCompatActivity<DetailsPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

}
