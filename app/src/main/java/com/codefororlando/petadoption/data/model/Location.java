package com.codefororlando.petadoption.data.model;

/**
 * Created by johnli on 11/13/16.
 */

public class Location {

    private final String id;
    private final String addressName;
    private final String primaryStreetAddress;
    private final String secondaryStreetAddress;
    private final String city;
    private final String state;
    private final String zipCode;

    public Location(String id, String addressName, String primaryStreetAddress, String secondaryStreetAddress, String city, String state, String zipCode) {
        this.id = id;
        this.addressName = addressName;
        this.primaryStreetAddress = primaryStreetAddress;
        this.secondaryStreetAddress = secondaryStreetAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getId() {
        return id;
    }

    public String getAddressName() {
        return addressName;
    }

    public String getPrimaryStreetAddress() {
        return primaryStreetAddress;
    }

    public String getSecondaryStreetAddress() {
        return secondaryStreetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }
}
