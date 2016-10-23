package com.codefororlando.petadoption.view;

import android.os.Bundle;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.presenter.DetailsPresenter;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Animal details view
 */
@RequiresPresenter(DetailsPresenter.class)
public class DetailsActivity extends NucleusAppCompatActivity<DetailsPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ((PetApplication) getApplication()).appComponent()
                .inject(this);
    }

}
