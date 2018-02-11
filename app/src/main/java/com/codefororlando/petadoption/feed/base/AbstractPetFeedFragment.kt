package com.codefororlando.petadoption.feed.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.detail.DetailsActivity
import com.codefororlando.petadoption.detail.DetailsPresenter
import kotlinx.android.synthetic.main.fragment_popular_feed.*
import nucleus.view.NucleusSupportFragment


open abstract class AbstractPetFeedFragment<V : PetFeedView, P : AbstractPetFeedPresenter<V>> : NucleusSupportFragment<P>(), PetFeedView {

    protected val scrollToEndListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (!recyclerView!!.canScrollVertically(1)) {
                presenter.loadMoreFeedItems()
            }
        }
    }

    override fun refreshList() {
        presenter.refreshList()
        recyclerView.scrollToPosition(0)
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

    override fun navigateToDetailView(animal: Animal) {
        val intent = Intent(context, DetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(DetailsPresenter.EXTRA_ANIMAL, animal)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun scrollToPosition(index: Int) {
        recyclerView.scrollToPosition(index)
    }
}