package com.codefororlando.petadoption.feed.popular

import android.os.Bundle
import com.codefororlando.petadoption.PetApplication
import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.data.provider.IAnimalProvider
import com.codefororlando.petadoption.feed.base.AbstractPetFeedPresenter
import com.codefororlando.petadoption.recyclerview.AnimalListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PopularPetFeedPresenter : AbstractPetFeedPresenter<PopularPetFeedFragment>() {

    private val ANIMAL_COUNT = 30
    private val DEFAULT_OFFSET = "0"

    @Inject
    lateinit var animalProvider: IAnimalProvider

    @Inject
    lateinit var animalListAdapter: AnimalListAdapter

    private var offset = DEFAULT_OFFSET

    private var lastVisibleIndex = 0

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        PetApplication.getApp().appComponent()
                .inject(this)
    }

    override fun onTakeView(petFeedFragment: PopularPetFeedFragment) {
        super.onTakeView(petFeedFragment)
        animalListAdapter.setOnItemClickListener { animal ->
            view?.navigateToDetailView(animal)
        }

        petFeedFragment.setAdapter(animalListAdapter)
        petFeedFragment.scrollToPosition(lastVisibleIndex)

        view?.apply {
            compositeDisposable.add(getOnScrollEndObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .subscribe({ loadMoreFeedItems() }, Timber::d))
        }

        if (offset == DEFAULT_OFFSET) {
            loadMoreFeedItems()
        }
        present()
    }

    override fun onDropView() {
        super.onDropView()
        lastVisibleIndex = view!!.getLastVisibleItemIndex()
    }

    private fun present() {
        view?.apply {
            if (animalListAdapter.itemCount == 0) {
                showEmptyView()
                hideContentView()
            } else {
                hideEmptyView()
                showContentView()
            }
        }
    }

    override fun refreshList() {
        offset = DEFAULT_OFFSET
        compositeDisposable.add(animalProvider.getAnimals(ANIMAL_COUNT, offset)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext({ this.updateFeedOffsetIndex(it) })
                .subscribe(Consumer<List<Animal>> { this.onLoadSuccess(it) }, Consumer<Throwable> { this.onLoadFailure(it) }))
    }

    override fun loadMoreFeedItems() {
        compositeDisposable.add(animalProvider.getAnimals(ANIMAL_COUNT, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext({ this.updateFeedOffsetIndex(it) })
                .subscribe(Consumer<List<Animal>> { this.onLoadMoreSuccess(it) }, Consumer<Throwable> { this.onLoadFailure(it) }))
    }

    override fun updateFeedOffsetIndex(ignored: List<Animal>) {
        offset = String.format("%d", Integer.valueOf(offset) + ANIMAL_COUNT)
    }

    override fun onLoadSuccess(animals: List<Animal>) {
        animalListAdapter.setAnimals(animals)
        animalListAdapter.notifyDataSetChanged()
        present()
        view?.scrollToTop()
    }

    override fun onLoadMoreSuccess(animals: List<Animal>) {
        animalListAdapter.addAnimals(animals)
        animalListAdapter.notifyDataSetChanged()
        present()
    }

    override fun onLoadFailure(throwable: Throwable) {
        Timber.e(throwable)
        present()
    }
}