package com.codefororlando.petadoption.network;

import com.codefororlando.petadoption.network.model.pet.PetfinderPetRecordResponse;
import com.codefororlando.petadoption.network.model.shelter.PetfinderShelterRecordResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ryan on 10/26/17.
 */

public interface IPetfinderService {

    @GET("pet.find?format=json")
    Observable<PetfinderPetRecordResponse> getAnimals(@Query("location") String cityOrZip, @Query("count") int count, @Query("offset") String lastOffset);

    @GET("shelter.get")
    Single<PetfinderShelterRecordResponse> getShelter(@Query("id") String shelterId);

}
