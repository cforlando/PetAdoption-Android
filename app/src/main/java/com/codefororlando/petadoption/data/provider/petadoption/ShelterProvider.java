package com.codefororlando.petadoption.data.provider.petadoption;

import com.codefororlando.petadoption.data.model.Contact;
import com.codefororlando.petadoption.data.model.Location;
import com.codefororlando.petadoption.data.model.Shelter;
import com.codefororlando.petadoption.data.provider.IShelterProvider;

import io.reactivex.Observable;

/**
 * Created by johnli on 11/13/16.
 */


// Currently returns a default stubbed shelter.
public class ShelterProvider implements IShelterProvider {

    //Stubbed shelter.  To be re-worked later.
    private Shelter stubShelter;

    public ShelterProvider() {
        //Temporary implementation.
        createStubShelter();
    }

    private void createStubShelter() {
        Contact stubContact = new Contact("Lady Lake Animal Control",
                "tel:3527511530",
                "info@ladylake.org",
                "http://ladylake.org/departments/police-department/animal-control-2");
        Location stubLocation = new Location("0",
                "Lady Lake Animal Control",
                "423 Fennell Blvd",
                "",
                "Lady Lake",
                "Florida",
                "32159");

        stubShelter = new Shelter("0", stubContact, stubLocation);
    }

    @Override
    public Observable<Shelter> getShelter(String id) {
        return Observable.just(stubShelter);
    }
}
