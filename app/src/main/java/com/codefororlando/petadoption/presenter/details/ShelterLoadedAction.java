package com.codefororlando.petadoption.presenter.details;

import com.codefororlando.petadoption.data.model.Shelter;

import rx.functions.Action1;

class ShelterLoadedAction implements Action1<Shelter> {

    private final DetailsPresenter presenter;

    ShelterLoadedAction(DetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void call(Shelter shelter) {
        presenter.setShelter(shelter);
    }

}
