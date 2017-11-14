package com.codefororlando.petadoption.presenter.details;

import com.codefororlando.petadoption.view.DetailsActivity;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by ryan on 11/13/17.
 */

public class ShelterLoadedFailedAction implements Consumer<Throwable> {

    private final DetailsPresenter presenter;

    ShelterLoadedFailedAction(DetailsPresenter presenter) { this.presenter = presenter; }

    @Override
    public void accept(Throwable throwable) throws Exception {
        Timber.e(throwable, "Failed to load shelter");
        DetailsActivity view = presenter.getView();
        if(view != null) {
            view.showShelterLoadFailedError();
        }
    }
}
