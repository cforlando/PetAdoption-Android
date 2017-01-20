package com.codefororlando.petadoption.presenter.list;

import com.codefororlando.petadoption.view.ListActivity;

import rx.functions.Action1;
import timber.log.Timber;

class AnimalsLoadedFailureAction implements Action1<Throwable> {

    private final ListPresenter presenter;

    AnimalsLoadedFailureAction(ListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void call(Throwable throwable) {
        Timber.e(throwable, "Failed to get animals");
        ListActivity view = presenter.getView();
        if (view != null) {
            view.notifyAnimalLoadingFailed();
        }
    }

}
