package com.codefororlando.petadoption.feed.base

import com.codefororlando.petadoption.data.model.Animal
import io.reactivex.disposables.CompositeDisposable
import nucleus.presenter.Presenter

open abstract class AbstractPetFeedPresenter<T : PetFeedView> : Presenter<T>() {

    lateinit var compositeDisposable : CompositeDisposable

    abstract fun refreshList()
    abstract fun loadMoreFeedItems()
    abstract fun updateFeedOffsetIndex(ignored: List<Animal>)
    abstract fun onLoadSuccess(animals: List<Animal>)
    abstract fun onLoadMoreSuccess(animals: List<Animal>)
    abstract fun onLoadFailure(throwable: Throwable)

    override fun onTakeView(view: T) {
        super.onTakeView(view)
        compositeDisposable = CompositeDisposable()
    }

    override fun onDropView() {
        super.onDropView()
        compositeDisposable.dispose()
    }
}