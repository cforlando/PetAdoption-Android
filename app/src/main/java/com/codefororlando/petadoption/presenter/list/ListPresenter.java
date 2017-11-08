package com.codefororlando.petadoption.presenter.list;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.data.provider.IAnimalProvider;
import com.codefororlando.petadoption.recyclerview.AAnimalListAdapter;
import com.codefororlando.petadoption.view.ListActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;

/**
 * Created by tencent on 10/8/16.
 */
public class ListPresenter extends Presenter<ListActivity> {

    @Inject
    IAnimalProvider animalProvider;

    @Inject
    AAnimalListAdapter animalListAdapter;

    private Disposable animalLoadSubscription;

    @Override
    protected void onTakeView(ListActivity listActivity) {
        super.onTakeView(listActivity);

        ((PetApplication) listActivity.getApplication()).appComponent()
                .inject(this);

        animalListAdapter.setOnItemClickListener(new AAnimalListAdapter.OnAnimalSelectListener() {
            @Override
            public void onSelect(Animal animal) {
                ListActivity view = getView();
                if (view != null) {
                    view.navigateToDetailView(animal);
                }
            }
        });

        listActivity.setAdapter(animalListAdapter);

        refreshList();
    }

    @Override
    protected void onDropView() {
        super.onDropView();
        animalLoadSubscription.dispose();
    }

    public void refreshList() {
        animalLoadSubscription = animalProvider.getAnimals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new AnimalsLoadedAction(animalListAdapter),
                        new AnimalsLoadedFailureAction(this)
                );
    }

}
