package com.codefororlando.petadoption.presenter;

import android.content.Intent;

import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.view.DetailsActivity;

import nucleus.presenter.Presenter;

/**
 * Created by tencent on 10/8/16.
 */
public class DetailsPresenter extends Presenter<DetailsActivity> {

    /**
     * Parcelable extra representing an {@link com.codefororlando.petadoption.data.model.Animal}.
     */
    public static final String EXTRA_ANIMAL = "EXTRA_ANIMAL";

    @Override
    protected void onTakeView(DetailsActivity detailsActivity) {
        super.onTakeView(detailsActivity);

        Intent intent = detailsActivity.getIntent();
        Animal animal = intent.getParcelableExtra(EXTRA_ANIMAL);
        detailsActivity.setAnimal(animal);
    }

}
