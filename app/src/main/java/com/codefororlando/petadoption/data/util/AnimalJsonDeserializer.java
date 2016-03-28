package com.codefororlando.petadoption.data.util;

import android.content.Context;
import android.net.Uri;

import com.codefororlando.petadoption.data.IAnimal;
import com.codefororlando.petadoption.data.IRetrievable;
import com.codefororlando.petadoption.data.impl.Animal;
import com.codefororlando.petadoption.data.impl.RetrievableImpl;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnimalJsonDeserializer implements JsonDeserializer<Animal> {

    Context context;

    public AnimalJsonDeserializer(Context context) {
        this.context = context;
    }

    @Override
    public Animal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RetrievableImplJsonDeserializer retrievableDeserialzer = new RetrievableImplJsonDeserializer(this.context);
        JsonObject jsonAnimal = json.getAsJsonObject();
        String name = jsonAnimal.get("name").getAsString();
        String breed = jsonAnimal.get("breed").getAsString();
        String species = jsonAnimal.get("species").getAsString();
        @IAnimal.Gender String gender = parseGender(jsonAnimal.get("gender").getAsString());

        List<IRetrievable> images = new ArrayList<>();
        JsonArray imagesArr = jsonAnimal.getAsJsonArray("images");
        for (JsonElement image : imagesArr) {
            images.add(retrievableDeserialzer.deserialize(image, RetrievableImpl.class, context));
        }
        Animal animal = new Animal(Uri.parse(UUID.randomUUID().toString()), name, gender, species, breed);
        animal.setImages(images);
        return animal;
    }

    private
    @IAnimal.Gender
    String parseGender(String gender) {
        switch (gender) {
            case Animal.MALE:
                return Animal.MALE;
            case Animal.FEMALE:
                return Animal.FEMALE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
