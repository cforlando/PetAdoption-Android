package com.codefororlando.petadoption.network.model.shelter;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by ryan on 11/4/17.
 */

@JsonObject
public class PetfinderShelterRecordResponse {

    @JsonField
    public PetfinderBase petfinder;

}
