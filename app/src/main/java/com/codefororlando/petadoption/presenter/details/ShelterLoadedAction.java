package com.codefororlando.petadoption.presenter.details;

import com.codefororlando.petadoption.data.model.Shelter;

import io.reactivex.functions.Consumer;

class ShelterLoadedAction implements Consumer<Shelter> {

    private final DetailsPresenter presenter;

    ShelterLoadedAction(DetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void accept(Shelter shelter) {
        presenter.setShelter(shelter);
    }

}
