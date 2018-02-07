package com.codefororlando.petadoption.about;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class OpenSourceModel {

    @JsonField
    protected String name;
    @JsonField
    protected String description;
    @JsonField
    protected String link;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }
}
