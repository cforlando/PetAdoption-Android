package com.codefororlando.petadoption.network.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by ryan on 10/26/17.
 */

@JsonObject
public class PetfinderBase {

    @JsonField
    PetfinderOffset lastOffset;

    @JsonField
    public PetfinderPets pets;
}
