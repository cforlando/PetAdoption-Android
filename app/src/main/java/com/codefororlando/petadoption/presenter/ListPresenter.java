package com.codefororlando.petadoption.presenter;

import android.widget.Toast;

import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.data.provider.IAnimalProvider;
import com.codefororlando.petadoption.recyclerview.AAnimalListAdapter;
import com.codefororlando.petadoption.view.ListActivity;

import java.util.List;

import javax.inject.Inject;

import nucleus.presenter.Presenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by tencent on 10/8/16.
 */
public class ListPresenter extends Presenter<ListActivity> {

    private final IAnimalProvider animalProvider;

    private AAnimalListAdapter animalListAdapter;
    private Subscription animalSubscription;

    @Inject
    ListPresenter(IAnimalProvider animalProvider,
                  AAnimalListAdapter animalListAdapter) {

        this.animalProvider = animalProvider;
        this.animalListAdapter = animalListAdapter;
    }

    @Override
    protected void onTakeView(ListActivity listActivity) {
        super.onTakeView(listActivity);

        listActivity.setAdapter(animalListAdapter);

        animalSubscription = animalProvider.getAnimals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new AnimalsLoadedAction(),
                        new AnimalsLoadedFailureAction()
                );
    }

    @Override
    protected void onDropView() {
        super.onDropView();

        animalSubscription.unsubscribe();
    }

    private class AnimalsLoadedAction implements Action1<List<Animal>> {

        @Override
        public void call(List<Animal> animals) {
            animalListAdapter.setAnimals(animals);
        }

    }

    private class AnimalsLoadedFailureAction implements Action1<Throwable> {

        @Override
        public void call(Throwable throwable) {
            Timber.e(throwable, "Failed to get animals");
            Toast.makeText(getView(), "Failed to get animals", Toast.LENGTH_SHORT)
                    .show();
        }

    }

}
