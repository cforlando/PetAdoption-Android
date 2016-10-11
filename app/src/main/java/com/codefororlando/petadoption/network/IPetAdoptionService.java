package com.codefororlando.petadoption.network;

import com.codefororlando.petadoption.data.model.Animal;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by john on 4/17/16.
 */
public interface IPetAdoptionService {

    @GET("list")
    Observable<List<Animal>> getAnimals();

}
