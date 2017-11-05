package com.codefororlando.petadoption.data.provider.petfinder;

import android.support.annotation.NonNull;

import com.codefororlando.petadoption.data.model.Contact;
import com.codefororlando.petadoption.data.model.Location;
import com.codefororlando.petadoption.data.model.Shelter;
import com.codefororlando.petadoption.data.provider.IShelterProvider;
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
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                })
                .map(new Function<PetfinderShelterRecordResponse, Shelter>() {

                    @Override
                    public Shelter apply(PetfinderShelterRecordResponse petfinderShelterRecordResponse) throws Exception {
                        return toShelter(petfinderShelterRecordResponse);
                    }
                })
                .toObservable();
    }

    @NonNull
    private Shelter toShelter(PetfinderShelterRecordResponse response) {
        PetfinderShelter shelter = response.petfinder.shelter;

        Contact contact = new Contact(
                shelter.name.contents,
                shelter.phone.contents,
                shelter.email.contents,
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
