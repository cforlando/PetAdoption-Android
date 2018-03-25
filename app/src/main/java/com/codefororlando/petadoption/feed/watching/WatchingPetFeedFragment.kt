package com.codefororlando.petadoption.feed.watching

import com.codefororlando.petadoption.feed.base.AbstractPetFeedFragment
import nucleus.factory.RequiresPresenter

@RequiresPresenter(WatchingPetFeedPresenter::class)
class WatchingPetFeedFragment : AbstractPetFeedFragment<WatchingPetFeedFragment, WatchingPetFeedPresenter>() {
    override fun scrollToTop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLastVisibleItemIndex(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideEmptyView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showContentView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideContentView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}