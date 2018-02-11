package com.codefororlando.petadoption.feed.popular

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codefororlando.petadoption.R
import com.codefororlando.petadoption.feed.base.AbstractPetFeedFragment
import com.codefororlando.petadoption.view.LocationDialogFragment
import kotlinx.android.synthetic.main.fragment_popular_feed.*
import kotlinx.android.synthetic.main.layout_empty_pet_feed.*
import nucleus.factory.RequiresPresenter

@RequiresPresenter(PopularPetFeedPresenter::class)
class PopularPetFeedFragment : AbstractPetFeedFragment<PopularPetFeedFragment, PopularPetFeedPresenter>() {

    private lateinit var locationDialog: LocationDialogFragment;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popular_feed, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationDialog = LocationDialogFragment()

        val gridSpans = resources
                .getInteger(R.integer.grid_spans)

        recyclerView.layoutManager = GridLayoutManager(context, gridSpans)
        recyclerView.addOnScrollListener(scrollToEndListener)
        setLocationBtn.setOnClickListener({ showLocationDialog() })
    }

    override fun getLastVisibleItemIndex(): Int {
        return (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
    }

    private fun showLocationDialog() {
        if (!locationDialog.isAdded) {
            locationDialog.show(fragmentManager, "location_dialog")
        }
    }

    override fun showEmptyView() {
        empty_feed_view.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        empty_feed_view.visibility = View.INVISIBLE
    }

    override fun showContentView() {
        recyclerView.visibility = View.VISIBLE
    }

    override fun hideContentView() {
        recyclerView.visibility = View.INVISIBLE
    }

}