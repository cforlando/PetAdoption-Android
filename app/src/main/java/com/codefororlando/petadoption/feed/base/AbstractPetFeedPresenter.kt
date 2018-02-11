package com.codefororlando.petadoption.feed.base

import com.codefororlando.petadoption.data.model.Animal
import nucleus.presenter.Presenter

open abstract class AbstractPetFeedPresenter<T : PetFeedView> : Presenter<T>() {
    abstract fun refreshList()
    abstract fun loadMoreFeedItems()
    abstract fun updateFeedOffsetIndex(ignored: List<Animal>)
    abstract fun onLoadSuccess(animals: List<Animal>)
    abstract fun onLoadMoreSuccess(animals: List<Animal>)
    abstract fun onLoadFailure(throwable: Throwable)
}