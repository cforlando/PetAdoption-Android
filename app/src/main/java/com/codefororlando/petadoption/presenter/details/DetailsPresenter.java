package com.codefororlando.petadoption.presenter.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.data.AnimalViewModel;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.data.model.Shelter;
import com.codefororlando.petadoption.data.provider.IShelterProvider;
import com.codefororlando.petadoption.view.DetailsActivity;
import com.codefororlando.petadoption.view.ImageGalleryActivity;

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

        shelterSubscription = shelterProvider.getShelter("arbitrary_id")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ShelterLoadedAction(this));
    }

    @Override
    protected void onDropView() {
        super.onDropView();
        shelterSubscription.dispose();
    }

    View.OnClickListener getActionClickListener(Shelter shelter) {
        return new ActionClickListener(this, shelter);
    }

    void setShelter(Shelter shelter) {
        DetailsActivity view = getView();
        if (view != null) {
            view.setShelter(shelter);
            view.setActionClickListener(getActionClickListener(shelter));
        }
    }

    void performShowImageGallery() {
        DetailsActivity activity = getView();
        if (activity != null) {
            Intent intent = ImageGalleryActivity.createIntent(activity, animal);
            activity.startActivity(intent);
        }
    }

    void performViewDialer(@NonNull Shelter shelter) {
        Uri phoneNumber = Uri.parse(shelter.getContact().getPhoneNumber());
        DetailsActivity view = getView();
        if (view != null) {
            view.call(phoneNumber);
        }
    }

    void performViewWebsite() {
        Uri website = Uri.parse("http://ladylake.org/departments/police-department/animal-control-2");
        DetailsActivity view = getView();
        if (view != null) {
            view.openWebsite(website);
        }
    }

    void performViewEmail(@NonNull Shelter shelter) {
        String emailAddress = shelter.getContact().getEmailAddress();
        Bundle extras = new Bundle();
        extras.putStringArray(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        extras.putString(Intent.EXTRA_SUBJECT, "Request Information on " + animal.getName());

        DetailsActivity view = getView();
        if (view != null) {
            view.email(extras);
        }
    }

}
