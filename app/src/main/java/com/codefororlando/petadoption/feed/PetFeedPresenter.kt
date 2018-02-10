package com.codefororlando.petadoption.feed

import android.os.Bundle
import com.codefororlando.petadoption.PetApplication
import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.data.provider.IAnimalProvider
import com.codefororlando.petadoption.recyclerview.AnimalListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import nucleus.presenter.Presenter
import timber.log.Timber
import javax.inject.Inject

class PetFeedPresenter : Presenter<PetFeedFragment>() {

    private val ANIMAL_COUNT = 30
    private val DEFAULT_OFFSET = "0"

    @Inject
    lateinit var animalProvider: IAnimalProvider

    @Inject
    lateinit var animalListAdapter: AnimalListAdapter

    private val compositeDisposable = CompositeDisposable()

    private var offset = DEFAULT_OFFSET

    private var lastVisibleIndex = 0

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        PetApplication.getApp().appComponent()
                .inject(this)
    }

    override fun onTakeView(petFeedFragment: PetFeedFragment) {
        super.onTakeView(petFeedFragment)
        animalListAdapter.setOnItemClickListener { animal ->
            view?.navigateToDetailView(animal)
        }

        petFeedFragment.setAdapter(animalListAdapter)
        petFeedFragment.scrollToPosition(lastVisibleIndex)

        if (offset == DEFAULT_OFFSET) {
            loadMore()
        }
    }

    override fun onDropView() {
        super.onDropView()
        compositeDisposable.dispose()
        lastVisibleIndex = view!!.lastVisibleItemIndex
    }

    fun refreshList() {
        offset = DEFAULT_OFFSET
        compositeDisposable.add(animalProvider.getAnimals(ANIMAL_COUNT, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext({ this.updateOffset(it) })
                .subscribe(Consumer<List<Animal>> { this.onLoadSuccess(it) }, Consumer<Throwable> { this.onLoadFailure(it) }))
    }

    fun loadMore() {
        compositeDisposable.add(animalProvider.getAnimals(ANIMAL_COUNT, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext({ this.updateOffset(it) })
                .subscribe(Consumer<List<Animal>> { this.onLoadMoreSuccess(it) }, Consumer<Throwable> { this.onLoadFailure(it) }))
    }

    private fun updateOffset(ignored: List<Animal>) {
        offset = String.format("%d", Integer.valueOf(offset) + ANIMAL_COUNT)
    }

    private fun onLoadSuccess(animals: List<Animal>) {
        animalListAdapter.setAnimals(animals)
        animalListAdapter.notifyDataSetChanged()
        if (animalListAdapter.itemCount > 0) {
            view?.hideEmptyFeedView()
        } else {
            view?.showEmptyFeedView()
        }
    }

    private fun onLoadMoreSuccess(animals: List<Animal>) {
        animalListAdapter.addAnimals(animals)
        animalListAdapter.notifyDataSetChanged()
    }

    private fun onLoadFailure(throwable: Throwable) {
        Timber.e(throwable)
        if (animalListAdapter.itemCount == 0) {
            view!!.showEmptyFeedView()
        }
    }
}