package com.codefororlando.petadoption.feed

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codefororlando.petadoption.PetApplication
import com.codefororlando.petadoption.R
import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.detail.DetailsActivity
import com.codefororlando.petadoption.detail.DetailsPresenter
import com.codefororlando.petadoption.view.LocationDialogFragment
import kotlinx.android.synthetic.main.fragment_popular_feed.*
import kotlinx.android.synthetic.main.layout_empty_pet_feed.*
import nucleus.factory.RequiresPresenter
import nucleus.view.NucleusSupportFragment


@RequiresPresenter(PetFeedPresenter::class)
class PetFeedFragment : NucleusSupportFragment<PetFeedPresenter>() {

    private lateinit var locationDialog: LocationDialogFragment;

    val lastVisibleItemIndex: Int
        get() = (recycler.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

    private val scrollToEndListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (!recyclerView!!.canScrollVertically(1)) {
                presenter.loadMore()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popular_feed, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationDialog = LocationDialogFragment()

        val gridSpans = resources
                .getInteger(R.integer.grid_spans)

        recycler.layoutManager = GridLayoutManager(context, gridSpans)
        recycler.addOnScrollListener(scrollToEndListener)
        setLocationBtn.setOnClickListener({ showLocationDialog() })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PetApplication.getApp().appComponent()
                .inject(this)
    }

    fun refreshList() {
        presenter.refreshList()
        recycler.scrollToPosition(0)
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recycler.adapter = adapter
    }

    fun navigateToDetailView(animal: Animal) {
        val intent = Intent(context, DetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(DetailsPresenter.EXTRA_ANIMAL, animal)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun scrollToPosition(index: Int) {
        recycler.scrollToPosition(index)
    }

    private fun showLocationDialog() {
        if (!locationDialog.isAdded) {
            locationDialog.show(fragmentManager, "location_dialog")
        }
    }

    fun showEmptyFeedView() {
        empty_feed_view.visibility = View.VISIBLE
    }

    fun hideEmptyFeedView() {
        empty_feed_view.visibility = View.INVISIBLE
    }
}