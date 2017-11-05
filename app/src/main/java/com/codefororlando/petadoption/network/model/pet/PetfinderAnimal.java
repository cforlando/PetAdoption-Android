package com.codefororlando.petadoption.network.model.pet;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by ryan on 10/26/17.
 */

@JsonObject
public class PetfinderAnimal {

    @JsonField
    public Age age;

    @JsonField
    public Size size;

    @JsonField
    public Media media;

    @JsonField
    public Id id;

    @JsonField
    public Breeds breeds;

    @JsonField
    public Name name;

    @JsonField(name = "sex")
    public Gender gender;

    @JsonField
    public Description description;

    @JsonField
    public ShelterId shelterId;

    @JsonField(name = "animal")
    public Species species;

    @JsonObject
    public static class Name {

        @JsonField(name = "$t")
        public String content;
    }

    @JsonObject
    public static class Age {

        @JsonField(name = "$t")
        public String content;
    }

    @JsonObject
    public static class Gender {

        @JsonField(name = "$t")
        public String content;
    }

    @JsonObject
    public static class Size {

        @JsonField(name = "$t")
        public String content;
    }

    @JsonObject
    public static class Media {

        @JsonField
        public Photos photos;

        @JsonObject
        public static class Photos {

            @JsonField(name = "photo")
            public List<Photo> photoList;

            @JsonObject
            public static class Photo {

                @JsonField(name = "@size")
                public String size;

                @JsonField(name = "$t")
                public String url;

                @JsonField(name = "@id")
                public int id;
            }
        }
    }

    @JsonObject
    public static class Id {

        @JsonField(name = "$t")
        public String content;
    }

    @JsonObject
    public static class ShelterId {

        @JsonField(name = "$t")
        public String content;
    }

    @JsonObject
    public static class Species {

        @JsonField(name = "$t")
        public String content;
    }

    @JsonObject
    public static class Breeds {

        @JsonField
        public Breed breed;

        @JsonObject
        public static class Breed {

            @JsonField(name = "$t")
            public String content;
        }
    }

    @JsonObject
    public static class Description {

        @JsonField(name = "$t")
        public String content;
    }

}
