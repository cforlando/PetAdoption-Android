package com.codefororlando.petadoption.data.provider.petfinder;

import android.support.annotation.NonNull;

import com.codefororlando.petadoption.data.model.Contact;
import com.codefororlando.petadoption.data.model.Location;
import com.codefororlando.petadoption.data.model.Shelter;
import com.codefororlando.petadoption.data.provider.IShelterProvider;
import com.codefororlando.petadoption.helper.ContactFormatter;
import com.codefororlando.petadoption.network.IPetfinderService;
import com.codefororlando.petadoption.network.model.shelter.PetfinderShelter;
import com.codefororlando.petadoption.network.model.shelter.PetfinderShelterRecordResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by ryan on 11/4/17.
 */

public class PetfinderShelterProvider implements IShelterProvider {

    private final IPetfinderService petfinderService;

    @Inject
    public PetfinderShelterProvider(IPetfinderService petfinderService) {
        this.petfinderService = petfinderService;
    }

    @Override
    public Observable<Shelter> getShelter(String id) {
        return petfinderService.getShelter(id)
                .doOnError(throwable -> throwable.printStackTrace())
                .map(petfinderShelterRecordResponse -> toShelter(petfinderShelterRecordResponse))
                .toObservable();
    }

    @NonNull
    private Shelter toShelter(PetfinderShelterRecordResponse response) {
        PetfinderShelter shelter = response.petfinder.shelter;
        String formattedNumber = "";
        String email = "";
        if(shelter.phone.contents != null)
            formattedNumber = ContactFormatter.Companion.formatPhoneNumber(shelter.phone.contents);
        if(shelter.email.contents != null)
            email = shelter.email.contents;


        Contact contact = new Contact(
                shelter.name.contents,
                formattedNumber,
                email,
                "" // Petfinder API doesn't give us shelter websites
        );

        Location location = new Location(
                shelter.id.contents,
                shelter.name.contents,
                shelter.primaryAddress.contents,
                shelter.secondaryAddress.contents,
                shelter.city.contents,
                shelter.state.contents,
                shelter.zip.contents
        );

        return new Shelter(shelter.id.contents, contact, location);
    }
}
