package com.codefororlando.petadoption.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View

import com.codefororlando.petadoption.PetApplication
import com.codefororlando.petadoption.data.AnimalViewModel
import com.codefororlando.petadoption.data.model.Animal
import com.codefororlando.petadoption.data.model.Shelter
import com.codefororlando.petadoption.data.provider.IShelterProvider

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import nucleus.presenter.Presenter

class DetailsPresenter : Presenter<DetailsActivity>() {

    @Inject
    internal lateinit var shelterProvider: IShelterProvider

    private lateinit var animal: Animal

    private lateinit var shelterSubscription: Disposable

    override fun onTakeView(detailsActivity: DetailsActivity) {
        super.onTakeView(detailsActivity)

        (detailsActivity.application as PetApplication).appComponent()
                .inject(this)

        val intent = detailsActivity.intent
        animal = intent.getParcelableExtra(EXTRA_ANIMAL)
        detailsActivity.setAnimal(AnimalViewModel(animal))

        shelterSubscription = shelterProvider.getShelter(animal.shelterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ShelterLoadedAction(this),
                        ShelterLoadedFailedAction(this))
    }

    override fun onDropView() {
        super.onDropView()
        shelterSubscription.dispose()
    }

    private fun getActionClickListener(shelter: Shelter): View.OnClickListener {
        return ActionClickListener(this, shelter)
    }

    fun setShelter(shelter: Shelter) {
        val view = view
        if (view != null) {
            view.setShelter(shelter)
            view.setActionClickListener(getActionClickListener(shelter))

            if (isShelterResourcePresent(shelter.contact.phoneNumber))
                view.showCallAction()

            if (isShelterResourcePresent(shelter.contact.emailAddress))
                view.showEmailAction()

            if (isShelterResourcePresent(shelter.contact.website))
                view.showWebAction()
        }
    }

    fun performViewDialer(shelter: Shelter) {
        val phoneNumber = Uri.parse(shelter.contact.phoneNumber)
        val view = view
        view?.call(phoneNumber)
    }

    fun performViewWebsite(shelter: Shelter) {
        val website = Uri.parse(shelter.contact.website)
        val view = view
        view?.openWebsite(website)
    }

    fun performViewEmail(shelter: Shelter) {
        val emailAddress = shelter.contact.emailAddress
        val extras = Bundle()
        extras.putStringArray(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        extras.putString(Intent.EXTRA_SUBJECT, "Request Information on " + animal.name)

        val view = view
        view?.email(extras)
    }

    private fun isShelterResourcePresent(resource: String): Boolean {
        return resource != ""
    }

    companion object {

        /**
         * Parcelable extra representing an [com.codefororlando.petadoption.data.model.Animal].
         */
        val EXTRA_ANIMAL = "EXTRA_ANIMAL"
    }

}
