package com.codefororlando.petadoption.presenter;

import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.R;
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

    @Inject
    IAnimalProvider animalProvider;

    @Inject
    AAnimalListAdapter animalListAdapter;

    private Subscription animalLoadSubscription;

    @Override
    protected void onTakeView(ListActivity listActivity) {
        super.onTakeView(listActivity);

        ((PetApplication) listActivity.getApplication()).appComponent()
                .inject(this);

        setAnimalAdapter(listActivity);
        setLayoutManager(listActivity);

        animalLoadSubscription = animalProvider.getAnimals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new AnimalsLoadedAction(),
                        new AnimalsLoadedFailureAction()
                );

        animalListAdapter.setOnItemClickListener(new AAnimalListAdapter.OnAnimalSelectListener() {
            @Override
            public void onSelect(Animal animal) {
                getView().navigateToDetailView(animal);
            }
        });
    }

    @Override
    protected void onDropView() {
        super.onDropView();
        animalLoadSubscription.unsubscribe();
    }

    private void setAnimalAdapter(ListActivity listActivity) {
        listActivity.setAdapter(animalListAdapter);
    }

    private void setLayoutManager(ListActivity listActivity) {
        final int gridSpans = listActivity.getResources()
                .getInteger(R.integer.grid_spans);

        listActivity.setLayoutManager(new GridLayoutManager(listActivity, gridSpans));
    }

    private class AnimalsLoadedAction implements Action1<List<Animal>> {

        @Override
        public void call(List<Animal> animals) {
            animalListAdapter.setAnimals(animals);
            animalListAdapter.notifyDataSetChanged();
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
