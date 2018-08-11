package com.codefororlando.petadoption.about

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.codefororlando.petadoption.BuildConfig

import com.codefororlando.petadoption.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    private val CFO_WEB_LINK: String = "http://www.codefororlando.com"
    private val CFO_TWITTER_LINK: String = "https://twitter.com/CodeForOrlando"
    private val PET_ADOPTION_REPO_LINK: String = "https://github.com/cforlando/PetAdoption-Android"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bind()
    }

    fun bind() {
        cfo_logo.setOnClickListener({ openWebsite() })
        twitter_label.setOnClickListener({ openTwitter() })
        website_label.setOnClickListener({ openWebsite() })
        rate_our_app_btn.setOnClickListener({ openStoreListing() })
        source_code_btn.setOnClickListener({ openRepo() })
        licenses_btn.setOnClickListener({ goToLicensesActivity() })
        version_label.text = String.format("Version %s (%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }

    private fun openStoreListing() {
        val uri: Uri = Uri.parse(String.format("market://details?id=%s", BuildConfig.APPLICATION_ID))
        var intent = Intent(ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun openWebsite() {
        val uri: Uri = Uri.parse(CFO_WEB_LINK)
        var intent = Intent(ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun openTwitter() {
        val uri: Uri = Uri.parse(CFO_TWITTER_LINK)
        var intent = Intent(ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun openRepo() {
        val uri: Uri = Uri.parse(PET_ADOPTION_REPO_LINK)
        var intent = Intent(ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun goToLicensesActivity() {
        val intent = Intent(this, OpenSourceActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
