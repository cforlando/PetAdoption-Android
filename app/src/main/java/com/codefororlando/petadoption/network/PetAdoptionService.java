package com.codefororlando.petadoption.network;

import com.codefororlando.petadoption.data.impl.Animal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by john on 4/17/16.
 */
public interface PetAdoptionService {

    @POST("query/{pageNumber}")
    Call<List<Animal>> queryAnimals(@Path("pageNumber") int pageNumber, @Field("pageSize") int pageSize);

    @GET("list/{species}/{pageNumber}")
    Call<List<Animal>> getAnimals(@Path("species") String species,  @Path("pageNumber") int pageNumber);
    @GET("list/{species}")
    Call<List<Animal>> getAnimals(@Path("species") String species);


}
