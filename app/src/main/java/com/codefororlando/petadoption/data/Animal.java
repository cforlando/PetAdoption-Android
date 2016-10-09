package com.codefororlando.petadoption.data;

import com.codefororlando.petadoption.data.annotation.Gender;

import java.util.List;

import auto.json.AutoJson;

@AutoJson
public abstract class Animal {

    public static Builder builder() {
        return new AutoJson_Animal.Builder();
    }

    @AutoJson.Field(name = "petId")
    public abstract String id();

    @AutoJson.Field(name = "petName")
    public abstract String name();

    @AutoJson.Field
    public abstract String species();

    @AutoJson.Field
    public abstract String breed();

    @Gender
    @AutoJson.Field
    public abstract String gender();

    @AutoJson.Field
    public abstract String age();

    @AutoJson.Field(name = "adoptable")
    public abstract Boolean adoptable();

    @AutoJson.Field(name = "shouldActQuickly")
    public abstract Boolean shouldActQuickly();

    @AutoJson.Field
    public abstract String color();

    @AutoJson.Field
    public abstract String description();

    @AutoJson.Field
    public abstract String activityLevel();

    @AutoJson.Field
    public abstract String intakeDate();

    @AutoJson.Field
    public abstract String shelterId();

    @AutoJson.Field
    public abstract List<String> images();

    @AutoJson.Builder
    public static abstract class Builder {
        public abstract Builder id(String id);

        public abstract Builder name(String name);

        public abstract Builder species(String species);

        public abstract Builder breed(String breed);

        public abstract Builder gender(String gender);

        public abstract Builder age(String age);

        public abstract Builder adoptable(Boolean adoptable);

        public abstract Builder shouldActQuickly(Boolean shouldActQuickly);

        public abstract Builder color(String color);

        public abstract Builder description(String description);

        public abstract Builder activityLevel(String activityLevel);

        public abstract Builder intakeDate(String intakeDate);

        public abstract Builder shelterId(String shelterId);

        public abstract Builder images(List<String> images);

        public abstract Animal build();
    }

}
