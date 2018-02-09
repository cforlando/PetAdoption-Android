package com.codefororlando.petadoption.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.codefororlando.petadoption.PetApplication
import com.codefororlando.petadoption.R
import com.codefororlando.petadoption.about.AboutActivity
import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.detail.DetailsActivity
import com.codefororlando.petadoption.detail.DetailsPresenter
import com.codefororlando.petadoption.presenter.list.ListPresenter
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.layout_empty_pet_feed.*
import nucleus.factory.RequiresPresenter
import nucleus.view.NucleusAppCompatActivity


@RequiresPresenter(ListPresenter::class)
class ListActivity : NucleusAppCompatActivity<ListPresenter>() {

    private lateinit var recyclerView: RecyclerView;
    private lateinit var locationDialog: LocationDialogFragment;

    val lastVisibleItemIndex: Int
        get() = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

    private val scrollToEndListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            if (!recyclerView!!.canScrollVertically(1)) {
                presenter.loadMore()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        (application as PetApplication).appComponent()
                .inject(this)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recycler) as RecyclerView
        locationDialog = LocationDialogFragment()

        val gridSpans = resources
                .getInteger(R.integer.grid_spans)

        recyclerView.layoutManager = GridLayoutManager(this, gridSpans)

        recyclerView.addOnScrollListener(scrollToEndListener)

        setLocationBtn.setOnClickListener({ showLocationDialog() })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_location, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                Snackbar.make(findViewById(android.R.id.content), "This feature is coming soon", Snackbar.LENGTH_SHORT).show()
                true
            }
            R.id.menu_location -> {
                showLocationDialog()
                true
            }
            R.id.menu_info -> {
                goToAboutPage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun refreshList() {
        presenter.refreshList()
        recyclerView.scrollToPosition(0)
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

    fun navigateToDetailView(animal: Animal) {
        val intent = Intent(this, DetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(DetailsPresenter.EXTRA_ANIMAL, animal)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun scrollToPosition(index: Int) {
        recyclerView.scrollToPosition(index)
    }

    private fun showLocationDialog() {
        if (!locationDialog.isAdded) {
            locationDialog.show(supportFragmentManager, "location_dialog")
        }
    }

    fun goToAboutPage() {
        val aboutPageIntent = Intent(this, AboutActivity::class.java)
        startActivity(aboutPageIntent)
    }

    fun showEmptyFeedView() {
        empty_feed_view.visibility = View.VISIBLE
    }

    fun hideEmptyFeedView() {
        empty_feed_view.visibility = View.INVISIBLE
    }
}
