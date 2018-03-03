package com.codefororlando.petadoption.feed

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.codefororlando.petadoption.PetApplication
import com.codefororlando.petadoption.R
import com.codefororlando.petadoption.about.AboutActivity
import com.codefororlando.petadoption.view.LocationDialogFragment
import kotlinx.android.synthetic.main.activity_list.*
import nucleus.factory.RequiresPresenter
import nucleus.view.NucleusAppCompatActivity
import timber.log.Timber


@RequiresPresenter(ListPresenter::class)
class ListActivity : NucleusAppCompatActivity<ListPresenter>() {

    private lateinit var locationDialog: LocationDialogFragment
    private lateinit var feedPagerAdapter: PetFeedViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        (application as PetApplication).appComponent()
                .inject(this)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        locationDialog = LocationDialogFragment()
        locationDialog.shouldRefreshFeedOnDismissalObservable
                .subscribe({
                    feedPagerAdapter.mCurrentItem?.refreshList()
                }, Timber::d)

        feedPagerAdapter = PetFeedViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = feedPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
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

    private fun showLocationDialog() {
        if (!locationDialog.isAdded) {
            locationDialog.show(supportFragmentManager, "location_dialog")
        }
    }

    private fun goToAboutPage() {
        val aboutPageIntent = Intent(this, AboutActivity::class.java)
        startActivity(aboutPageIntent)
    }

}
