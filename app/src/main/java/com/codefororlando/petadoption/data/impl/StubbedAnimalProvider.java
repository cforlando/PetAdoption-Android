package com.codefororlando.petadoption.data.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.IAnimal;
import com.codefororlando.petadoption.data.IAnimalProvider;
import com.codefororlando.petadoption.data.util.AnimalJsonDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class StubbedAnimalProvider implements IAnimalProvider {

    private Context context;
    private static ArrayList<IAnimal> stubbedAnimalList;

    public StubbedAnimalProvider(@NonNull Context context) {
        this.context = context;
        if (stubbedAnimalList == null) {
            initStubbedAnimalList();
        }
    }

    void initStubbedAnimalList() {
        stubbedAnimalList = new ArrayList<>();
        Reader reader = null;
        InputStream stream = context.getResources()
                .openRawResource(R.raw.stubbed_animals);
        reader = new BufferedReader(new InputStreamReader(stream), 8092);

        JsonParser parser = new JsonParser();
        JsonObject jsonObj = (JsonObject) parser.parse(reader);
        JsonArray animalArr = jsonObj.getAsJsonArray("animals");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Animal.class, new AnimalJsonDeserializer(context));
        Gson gson = gsonBuilder.create();
        for (JsonElement element : animalArr) {
            Animal animal = gson.fromJson(element, Animal.class);
            stubbedAnimalList.add(animal);
        }
    }

    @Override
    public List<IAnimal> getAnimals() {
        return stubbedAnimalList;
    }
}
