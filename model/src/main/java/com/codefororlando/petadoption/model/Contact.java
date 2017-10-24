package com.codefororlando.petadoption.model;

/**
 * Created by johnli on 11/13/16.
 */

public class Contact {
    private final String name;
    private final String phoneNumber;
    private final String emailAddress;
    private final String website;

    public Contact(String name, String phoneNumber, String emailAddress, String website) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getWebsite() {
        return website;
    }
}
