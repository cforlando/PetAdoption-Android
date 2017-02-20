package com.codefororlando.petadoption.presenter.list;

import com.codefororlando.petadoption.view.ListActivity;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

class AnimalsLoadedFailureAction implements Consumer<Throwable> {

    private final ListPresenter presenter;

    AnimalsLoadedFailureAction(ListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void accept(Throwable throwable) {
        Timber.e(throwable, "Failed to get animals");
        ListActivity view = presenter.getView();
        if (view != null) {
            view.notifyAnimalLoadingFailed();
        }
    }

}
