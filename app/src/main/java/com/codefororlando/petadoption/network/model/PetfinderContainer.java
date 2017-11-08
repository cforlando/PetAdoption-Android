package com.codefororlando.petadoption.network.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by ryan on 11/4/17.
 */

@JsonObject
public class PetfinderContainer {

    @JsonField(name = "$t")
    public String contents;
}
