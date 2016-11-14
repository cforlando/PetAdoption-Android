package com.codefororlando.petadoption.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.codefororlando.petadoption.PetApplication;
import com.codefororlando.petadoption.data.AnimalViewModel;
import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.data.model.Shelter;
import com.codefororlando.petadoption.data.provider.IShelterProvider;
import com.codefororlando.petadoption.view.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import javax.inject.Inject;

import nucleus.presenter.Presenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by tencent on 10/8/16.
 */
public class DetailsPresenter extends Presenter<DetailsActivity> {

    /**
     * Parcelable extra representing an {@link com.codefororlando.petadoption.data.model.Animal}.
     */
    public static final String EXTRA_ANIMAL = "EXTRA_ANIMAL";

    private Subscription shelterSubscription;

    @Inject
    IShelterProvider shelterProvider;

    Shelter shelter;
    Animal animal;

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
                .subscribe(new ShelterLoadedAction());
    }

    @Override
    protected void onDropView() {
        super.onDropView();
        shelterSubscription.unsubscribe();
    }

    public void initiateCall() {
        if(shelter != null) {
            Uri phoneNumber = Uri.parse(shelter.getContact().getPhoneNumber());
            getView().call(phoneNumber);
        }
    }

    public void initiateOpenWebsite() {
        if(shelter != null) {
            Uri website = Uri.parse("http://ladylake.org/departments/police-department/animal-control-2");
            getView().openWebsite(website);
        }
    }

    public void fetchAnimalImage(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DetailsActivity view = getView();
                    Bitmap image = Picasso.with(view)
                            .load(url)
                            .get();
                    view.setAnimalImage(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void initiateOpenEmail() {
        if(shelter != null) {
            String emailAddress = shelter.getContact().getEmailAddress();
            Bundle extras = new Bundle();
            extras.putStringArray(Intent.EXTRA_EMAIL, new String[]{emailAddress});
            extras.putString(Intent.EXTRA_SUBJECT, "Request Information on " + animal.getName());
            getView().email(extras);
        }
    }

    private class ShelterLoadedAction implements Action1<Shelter> {
        @Override
        public void call(Shelter shelter) {
            DetailsPresenter.this.shelter = shelter;
            getView().setShelter(shelter);
        }
    }
}
