package com.codefororlando.petadoption.view

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.codefororlando.petadoption.BuildConfig

import com.codefororlando.petadoption.R

class AboutActivity : AppCompatActivity() {

    private val CFO_WEB_LINK : String = "http://www.codefororlando.com"
    private val CFO_TWITTER_LINK : String = "https://twitter.com/CodeForOrlando"
    private val PET_ADOPTION_REPO_LINK : String = "https://github.com/cforlando/PetAdoption-Android"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bind()
    }

    fun bind() {
        val logo = findViewById(R.id.cfo_logo)
        logo.setOnClickListener({ openWebsite() })

        val twitterLabel = findViewById(R.id.twitter_label)
        twitterLabel.setOnClickListener( {openTwitter()})

        val websiteLabel = findViewById(R.id.website_label)
        websiteLabel.setOnClickListener({openWebsite()})

        val rateAppButton = findViewById(R.id.rate_our_app_btn)
        rateAppButton.setOnClickListener({openStoreListing()})

        val openRepoButton = findViewById(R.id.source_code_btn)
        openRepoButton.setOnClickListener({openRepo()})

        val versionLabel : TextView = findViewById(R.id.version_label) as TextView
        versionLabel.text = String.format("Version %s (%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
    }

    private fun openStoreListing() {
        val uri : Uri = Uri.parse(String.format( "market://details?id=%s", BuildConfig.APPLICATION_ID))
        var intent : Intent = Intent(ACTION_VIEW, uri);
        startActivity(intent)
    }

    private fun openWebsite() {
        val uri : Uri = Uri.parse(CFO_WEB_LINK);
        var intent : Intent = Intent(ACTION_VIEW, uri);
        startActivity(intent)
    }

    private fun openTwitter() {
        val uri : Uri = Uri.parse(CFO_TWITTER_LINK);
        var intent : Intent = Intent(ACTION_VIEW, uri);
        startActivity(intent)
    }

    private fun openRepo() {
        val uri : Uri = Uri.parse(PET_ADOPTION_REPO_LINK);
        var intent : Intent = Intent(ACTION_VIEW, uri);
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
