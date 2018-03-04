package com.codefororlando.petadoption.presenter.list;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.data.provider.IAnimalProvider;
import com.codefororlando.petadoption.persistence.dao.AnimalDao;
import com.codefororlando.petadoption.persistence.mapper.AnimalMapper;
import com.codefororlando.petadoption.recyclerview.AAnimalListAdapter;
import com.codefororlando.petadoption.view.ListActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;
import timber.log.Timber;

public class ListPresenter extends Presenter<ListActivity> {

    private static final int ANIMAL_COUNT = 30;
    private static final String DEFAULT_OFFSET = "0";

    @Inject
    IAnimalProvider animalProvider;

    @Inject
    AAnimalListAdapter animalListAdapter;

    @Inject
    AnimalDao animalDao;

    private Disposable animalLoadSubscription;

    private String offset = DEFAULT_OFFSET;

    private int lastVisibleIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        PetApplication.getApp().appComponent()
                .inject(this);
    }

    @Override
    protected void onTakeView(ListActivity listActivity) {
        super.onTakeView(listActivity);
        animalListAdapter.setOnItemClickListener(animal -> {
            ListActivity view = getView();
            if (view != null) {
                view.navigateToDetailView(animal);
            }
        });

        listActivity.setAdapter(animalListAdapter);
        listActivity.scrollToPosition(lastVisibleIndex);

        if (offset.equals(DEFAULT_OFFSET)) {
            loadMore();
        }
    }

    @Override
    protected void onDropView() {
        super.onDropView();
        animalLoadSubscription.dispose();
        lastVisibleIndex = getView().getLastVisibleItemIndex();
    }

    public void refreshList() {
        offset = DEFAULT_OFFSET;
        animalLoadSubscription = animalProvider.getAnimals(ANIMAL_COUNT, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::updateOffset)
                .subscribe(this::onLoadSuccess, this::onLoadFailure);
    }

    public void loadMore() {
        animalLoadSubscription = animalProvider.getAnimals(ANIMAL_COUNT, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::updateOffset)
                .subscribe(this::onLoadMoreSuccess, this::onLoadFailure);
    }

    private void updateOffset(List<Animal> ignored) {
        offset = String.format("%d", Integer.valueOf(offset) + ANIMAL_COUNT);
    }

    private void onLoadSuccess(List<Animal> animals) {
        animalListAdapter.setAnimals(animals);
        animalListAdapter.notifyDataSetChanged();
        AnimalMapper animalMapper = new AnimalMapper();
        animalDao.insertAll(animalMapper.map(animals));
        if(animalListAdapter.getItemCount() > 0) {
            getView().hideEmptyFeedView();
        } else {
            getView().showEmptyFeedView();
        }
    }

    private void onLoadMoreSuccess(List<Animal> animals) {
        animalListAdapter.addAnimals(animals);
        animalListAdapter.notifyDataSetChanged();
    }

    private void onLoadFailure(Throwable throwable) {
        Timber.e(throwable, "Failed to get animals");
        if (getView() != null) {
            if(animalListAdapter.getItemCount() == 0) {
                getView().showEmptyFeedView();
            }
        }
    }
}
