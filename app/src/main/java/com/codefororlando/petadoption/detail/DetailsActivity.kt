package com.codefororlando.petadoption.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.text.util.Linkify
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.codefororlando.petadoption.PetApplication
import com.codefororlando.petadoption.R
import com.codefororlando.petadoption.data.AnimalViewModel
import com.codefororlando.petadoption.data.model.Shelter
import com.codefororlando.petadoption.recyclerview.HorizontalViewPagerIndicator
import com.codefororlando.petadoption.view.PetImageViewPagerAdapter
import nucleus.factory.RequiresPresenter
import nucleus.view.NucleusAppCompatActivity

/**
 * AnimalEntity details view
 */
@RequiresPresenter(DetailsPresenter::class)
class DetailsActivity : NucleusAppCompatActivity<DetailsPresenter>() {

    private lateinit var textViewGender: TextView
    private lateinit var textViewAge: TextView
    private lateinit var textViewSize: TextView
    private lateinit var textViewLocation: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewLocationName: TextView
    private lateinit var textViewLocationStreet: TextView
    private lateinit var textViewCityStateZip: TextView
    private lateinit var callActionView: LinearLayout
    private lateinit var webActionView: LinearLayout
    private lateinit var emailActionView: LinearLayout
    private lateinit var imageViewPager: ViewPager
    private lateinit var pagerAdapter: PetImageViewPagerAdapter
    private lateinit var pagerIndicator: HorizontalViewPagerIndicator

    private var imageSelectedIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        (application as PetApplication).appComponent()
                .inject(this)

        initToolbar()
        bindViews()

        pagerAdapter = PetImageViewPagerAdapter(this)
        imageViewPager.adapter = pagerAdapter
        pagerIndicator.bind(imageViewPager)
        setDefaultState()
    }

    private fun initToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun bindViews() {
        textViewGender = findViewById(R.id.details_gender) as TextView
        textViewAge = findViewById(R.id.details_age) as TextView
        textViewSize = findViewById(R.id.details_size) as TextView
        textViewLocation = findViewById(R.id.details_location) as TextView
        textViewDescription = findViewById(R.id.details_description) as TextView
        textViewLocationName = findViewById(R.id.details_location_name) as TextView
        textViewLocationStreet = findViewById(R.id.details_location_street) as TextView
        textViewCityStateZip = findViewById(R.id.details_location_city_state_zip) as TextView
        callActionView = findViewById(R.id.details_action_call) as LinearLayout
        webActionView = findViewById(R.id.details_action_web) as LinearLayout
        emailActionView = findViewById(R.id.details_action_email) as LinearLayout
        imageViewPager = findViewById(R.id.image_pager) as ViewPager
        pagerIndicator = findViewById(R.id.pager_indicator) as HorizontalViewPagerIndicator
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_PAGE_INDEX_ARG, imageViewPager.currentItem)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imageSelectedIndex = savedInstanceState.getInt(SELECTED_PAGE_INDEX_ARG)
    }

    override fun onResume() {
        super.onResume()
        setPageSelected(imageSelectedIndex)
    }

    fun setPageSelected(index: Int) {
        imageViewPager.currentItem = index
        pagerIndicator.onPageSelected(index)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setActionClickListener(onClickListener: View.OnClickListener) {
        callActionView.setOnClickListener(onClickListener)
        emailActionView.setOnClickListener(onClickListener)
        webActionView.setOnClickListener(onClickListener)
    }

    fun setAnimal(animalViewModel: AnimalViewModel) {
        val animal = animalViewModel.animal
        textViewAge.text = animal.age
        textViewGender.text = animal.gender
        textViewSize.text = null
        textViewDescription.text = animal.description
        Linkify.addLinks(textViewDescription, Linkify.ALL)

        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.title = animal.name
        }

        pagerAdapter.setImages(animal.images, animalViewModel.placeholderImageResource())
        pagerIndicator.onDataSetChanged(pagerAdapter)
    }

    fun setShelter(shelter: Shelter) {
        val location = shelter.location
        textViewLocation.text = location.city
        textViewLocationName.text = location.addressName
        textViewLocationStreet.text = location.primaryStreetAddress

        val cityStateZip = String.format("%s %s, %s", location.city, location.state, location.zipCode)
        textViewCityStateZip.text = cityStateZip
    }

    fun call(phoneNumber: Uri) {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = phoneNumber
        startActivity(callIntent, R.string.info_intent_error_no_dialer)
    }

    fun email(extras: Bundle) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtras(extras)
        emailIntent.type = "plain/text"
        startActivity(emailIntent, R.string.info_intent_error_no_email)
    }

    fun openWebsite(webAddress: Uri) {
        val webIntent = Intent(Intent.ACTION_VIEW)
        webIntent.data = webAddress
        startActivity(webIntent, R.string.info_intent_error_no_browser)
    }

    fun hideCallAction() {
        callActionView.visibility = View.INVISIBLE
    }

    fun showCallAction() {
        callActionView.visibility = View.VISIBLE
    }

    fun hideWebAction() {
        webActionView.visibility = View.INVISIBLE
    }

    fun showWebAction() {
        webActionView.visibility = View.VISIBLE
    }

    fun hideEmailAction() {
        emailActionView.visibility = View.INVISIBLE
    }

    fun showEmailAction() {
        emailActionView.visibility = View.VISIBLE
    }

    fun showShelterLoadFailedError() {
        Toast.makeText(this, R.string.shelter_load_failed_message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Start an activity or show an error.
     *
     * @param intent         activity intent
     * @param onErrorMessage message to display if starting the activity fails
     */
    private fun startActivity(intent: Intent, @StringRes onErrorMessage: Int) {
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, onErrorMessage, Toast.LENGTH_SHORT).show()
        }

    }

    private fun setDefaultState() {
        hideCallAction()
        hideWebAction()
        hideEmailAction()
    }

    companion object {
        @JvmStatic
        private val SELECTED_PAGE_INDEX_ARG = "SELECTED_PAGE_INDEX_ARG"
    }
}
