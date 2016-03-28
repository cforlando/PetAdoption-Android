package com.codefororlando.petadoption.data.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.codefororlando.petadoption.data.impl.RetrievableImpl;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class RetrievableImplJsonDeserializer implements JsonDeserializer<RetrievableImpl> {

    private static final String DRAWABLE_COMPARE_PREFIX = "R.drawable.";
    Context context;

    public RetrievableImplJsonDeserializer(Context context) {
        this.context = context;
    }

    @Override
    public RetrievableImpl deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String uri = json.getAsString();
        RetrievableImpl retrievable = null;
        if(uri.contains(DRAWABLE_COMPARE_PREFIX)) {
            String imageName = uri.substring(DRAWABLE_COMPARE_PREFIX.length());
            int picId =  this.context.getResources().getIdentifier(imageName, "drawable", this.context.getPackageName());
            retrievable = new RetrievableImpl(getUriForDrawable(this.context, picId));
        } else {
            retrievable = new RetrievableImpl(Uri.parse(uri));
        }
        return retrievable;
    }

    private static Uri getUriForDrawable(@NonNull Context context, @NonNull int drawable) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + drawable);
    }
}
