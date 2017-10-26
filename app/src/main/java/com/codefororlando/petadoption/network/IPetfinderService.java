package com.codefororlando.petadoption.network;

import com.codefororlando.petadoption.data.model.Animal;
import com.codefororlando.petadoption.network.model.PetfinderResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ryan on 10/26/17.
 */

public interface IPetfinderService {

    @GET("pet.find")
    Observable<PetfinderResponse> getAnimals(@Query("location") String cityOrZip);

}
