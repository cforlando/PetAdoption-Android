package com.codefororlando.petadoption.network.model.pet;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by ryan on 10/26/17.
 */

@JsonObject
public class PetfinderPets {

    @JsonField(name = "pet")
    public List<PetfinderAnimal> petList;
}
