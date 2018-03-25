package com.codefororlando.petadoption.feed.watching

import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.feed.base.AbstractPetFeedPresenter

class WatchingPetFeedPresenter : AbstractPetFeedPresenter<WatchingPetFeedFragment>() {
    override fun refreshList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadMoreFeedItems() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateFeedOffsetIndex(ignored: List<Animal>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadSuccess(animals: List<Animal>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadMoreSuccess(animals: List<Animal>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadFailure(throwable: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}