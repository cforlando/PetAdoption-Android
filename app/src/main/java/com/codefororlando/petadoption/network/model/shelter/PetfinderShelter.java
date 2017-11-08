package com.codefororlando.petadoption.network.model.shelter;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.codefororlando.petadoption.network.model.PetfinderContainer;

/**
 * Created by ryan on 11/4/17.
 */

@JsonObject
public class PetfinderShelter {

    @JsonField
    public PetfinderContainer name;

    @JsonField
    public PetfinderContainer phone;

    @JsonField
    public PetfinderContainer email;

    @JsonField
    public PetfinderContainer id;

    @JsonField(name = "address1")
    public PetfinderContainer primaryAddress;

    @JsonField(name = "address2")
    public PetfinderContainer secondaryAddress;

    @JsonField
    public PetfinderContainer city;

    @JsonField
    public PetfinderContainer state;

    @JsonField
    public PetfinderContainer zip;
}
