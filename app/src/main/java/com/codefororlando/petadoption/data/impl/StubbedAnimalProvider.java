package com.codefororlando.petadoption.data.impl;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.codefororlando.petadoption.R;
import com.codefororlando.petadoption.data.IAnimal;
import com.codefororlando.petadoption.data.IAnimalProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
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
        gsonBuilder.registerTypeAdapter(Uri.class, new UriDeserializer(this.context));
        Gson gson = gsonBuilder.create();
        for (JsonElement element : animalArr) {
            Animal animal = gson.fromJson(element, Animal.class);
            stubbedAnimalList.add(animal);
        }
    }

    public class UriDeserializer implements JsonDeserializer<Uri> {

        private static final String DRAWABLE_COMPARE_PREFIX = "R.drawable.";

        Context context;

        public UriDeserializer(Context context) {
            this.context = context;
        }

        @Override
        public Uri deserialize(final JsonElement src, final Type srcType,
                               final JsonDeserializationContext dontUseThisContext) throws JsonParseException {
            String uriStr = src.getAsString();
            Uri uri = null;
            //NOTE: We don't have to parse from disk to get drawables in the near future.  Remove when we
            //          are not using any placeholder images. - JLi
            if (uriStr.contains(DRAWABLE_COMPARE_PREFIX)) {
                String imageName = uriStr.substring(DRAWABLE_COMPARE_PREFIX.length());
                int picId = this.context.getResources().getIdentifier(imageName, "drawable", this.context.getPackageName());
                uri = getUriForDrawable(this.context, picId);
            } else {
                uri = Uri.parse(uriStr);
            }
            return uri;
        }

        //Note: Remove when we aren't using any local placeholder images. - JLi
        private Uri getUriForDrawable(@NonNull Context context, @NonNull int drawable) {
            return Uri.parse("android.resource://" + context.getPackageName() + "/" + drawable);
        }
    }

    @Override
    public List<IAnimal> getAnimals() {
        return stubbedAnimalList;
    }
}
