package com.codefororlando.petadoption.presenter.details;

import android.support.annotation.NonNull;
import android.view.View;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.model.Shelter;

class ActionClickListener implements View.OnClickListener {

    private final DetailsPresenter presenter;
    private final Shelter shelter;

    ActionClickListener(@NonNull DetailsPresenter presenter,
                        @NonNull Shelter shelter) {

        this.presenter = presenter;
        this.shelter = shelter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.details_action_call:
                presenter.performViewDialer(shelter);
                break;
            case R.id.details_action_email:
                presenter.performViewEmail(shelter);
                break;
            case R.id.details_action_web:
                presenter.performViewWebsite();
                break;
            default:
                throw new IllegalArgumentException("Unhandled click for " + v);
        }
    }

}
