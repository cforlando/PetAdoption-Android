package com.codefororlando.petadoption.data.model;

/**
 * Created by johnli on 11/13/16.
 */

public final class Shelter {

    private final String id;
    private final Contact contact;
    private final Location location;

    public Shelter(String id, Contact contact, Location location) {
        this.id = id;
        this.contact = contact;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Contact getContact() {
        return contact;
    }

}
