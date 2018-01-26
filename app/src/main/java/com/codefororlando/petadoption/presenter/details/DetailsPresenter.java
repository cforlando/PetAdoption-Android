package com.codefororlando.petadoption.presenter.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.data.AnimalViewModel;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.data.model.Shelter;
import com.codefororlando.petadoption.data.provider.IShelterProvider;
import com.codefororlando.petadoption.helper.ContactFormatter;
import com.codefororlando.petadoption.helper.ContactParser;
import com.codefororlando.petadoption.view.DetailsActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;

/**
 * Created by tencent on 10/8/16.
 */
@SuppressWarnings("WeakerAccess")
public class DetailsPresenter extends Presenter<DetailsActivity> {

    /**
     * Parcelable extra representing an {@link com.codefororlando.petadoption.data.model.Animal}.
     */
    public static final String EXTRA_ANIMAL = "EXTRA_ANIMAL";

    @Inject
    IShelterProvider shelterProvider;

    private Animal animal;

    private Disposable shelterSubscription;

    @Override
    protected void onTakeView(DetailsActivity detailsActivity) {
        super.onTakeView(detailsActivity);

        ((PetApplication) detailsActivity.getApplication()).appComponent()
                .inject(this);

        Intent intent = detailsActivity.getIntent();
        animal = intent.getParcelableExtra(EXTRA_ANIMAL);
        detailsActivity.setAnimal(new AnimalViewModel(animal));

        shelterSubscription = shelterProvider.getShelter(animal.getShelterId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ShelterLoadedAction(this),
                        new ShelterLoadedFailedAction(this));
    }

    @Override
    protected void onDropView() {
        super.onDropView();
        shelterSubscription.dispose();
    }

    private View.OnClickListener getActionClickListener(Shelter shelter) {
        return new ActionClickListener(this, shelter);
    }

    void setShelter(Shelter shelter) {
        DetailsActivity view = getView();
        if (view != null) {
            view.setShelter(shelter);
            view.setActionClickListener(getActionClickListener(shelter));

            if (!TextUtils.isEmpty(shelter.getContact().getPhoneNumber()) ||
                    !ContactParser.Companion.findPhoneNumber(animal.getDescription()).equals(ContactParser.EMPTY)) {
                view.showCallAction();
            }

            if (!TextUtils.isEmpty(shelter.getContact().getEmailAddress()) ||
                    !ContactParser.Companion.findEmail(animal.getDescription()).equals(ContactParser.EMPTY))
                view.showEmailAction();

            if (!TextUtils.isEmpty(shelter.getContact().getWebsite()) ||
                    !ContactParser.Companion.findWeblink(animal.getDescription()).equals(ContactParser.EMPTY))
                view.showWebAction();
        }
    }

    void performViewDialer(@NonNull Shelter shelter) {
        Uri phoneNumberUri = Uri.parse(getPhoneNumber(shelter));
        DetailsActivity view = getView();
        if (view != null) {
            view.call(phoneNumberUri);
        }
    }

    void performViewWebsite(@NonNull Shelter shelter) {
        Uri website = Uri.parse(getWebsiteLink(shelter));
        DetailsActivity view = getView();
        if (view != null) {
            view.openWebsite(website);
        }
    }

    void performViewEmail(@NonNull Shelter shelter) {
        Bundle extras = new Bundle();
        extras.putStringArray(Intent.EXTRA_EMAIL, new String[]{getEmailAddress(shelter)});
        extras.putString(Intent.EXTRA_SUBJECT, "Request Information on " + animal.getName());
        DetailsActivity view = getView();
        if (view != null) {
            view.email(extras);
        }
    }

    private String getPhoneNumber(Shelter shelter) {
        String phoneNumber;
        if (!TextUtils.isEmpty(shelter.getContact().getPhoneNumber())) {
            phoneNumber = shelter.getContact().getPhoneNumber();
        } else {
            phoneNumber = ContactFormatter.Companion.formatPhoneNumber(
                    ContactParser.Companion.findPhoneNumber(animal.getDescription()));
        }
        return phoneNumber;
    }

    private String getWebsiteLink(Shelter shelter) {
        String url;
        if (!TextUtils.isEmpty(shelter.getContact().getWebsite())) {
            url = shelter.getContact().getWebsite();
        } else {
            url = ContactFormatter.Companion.formatWebLink(
                    ContactParser.Companion.findWeblink(animal.getDescription()));
        }
        return url;
    }

    private String getEmailAddress(Shelter shelter) {
        String emailAddress;
        if (!shelter.getContact().getEmailAddress().isEmpty()) {
            emailAddress = shelter.getContact().getEmailAddress();
        } else {
            emailAddress = ContactParser.Companion.findEmail(animal.getDescription());
        }
        return emailAddress;
    }
}
