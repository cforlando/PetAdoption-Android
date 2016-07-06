package com.codefororlando.petadoption.data.impl;

import android.content.Context;

import com.codefororlando.petadoption.data.IAnimal;
import com.codefororlando.petadoption.data.IAnimalProvider;
import com.codefororlando.petadoption.network.PetAdoptionService;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by john on 4/18/16.
 */

//TODO seperate cache logic from provider
public class PetAdoptionProvider implements IAnimalProvider {

    private static final String BASE_URL = "http://cfo-pet-adoption-server.eastus.cloudapp.azure.com";
    private static final String API_URL = BASE_URL + "/api/v2/";
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;
    private static final long CACHE_SIZE = 4 * 1024 * 1024; // 10 MB

    List<? extends IAnimal> animals;
    PetAdoptionService service;

    public PetAdoptionProvider(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "cached_responses");
        Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(API_URL)
                .client(client)
                .build();

        service = retrofit.create(PetAdoptionService.class);
    }

    public List<? extends IAnimal> getAnimals() {
        return animals;
    }

    @Override
    public void getAnimals(final AnimalHandler handler) {
        if(animals != null) {
            handler.onResult(true, animals);
        } else {
            Call<List<Animal>> getAnimalsCall = service.getAnimals("dog");
            getAnimalsCall.enqueue(new Callback<List<Animal>>() {
                @Override
                public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                    animals = response.body();
                    handler.onResult(true, animals);
                }

                @Override
                public void onFailure(Call<List<Animal>> call, Throwable t) {
                    handler.onResult(false, null);
                }
            });
        }
    }

    @Override
    public List<String> getQualifiedImagePaths(IAnimal animal) {
        List<String> images = animal.getImages();
        List<String> outputs = new LinkedList<>();
        for (String image : images) {
            outputs.add(BASE_URL + image);
        }
        return outputs;
    }

}
