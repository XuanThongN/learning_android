package com.xuanthongn.hellobaby.models;

public class Contact {
private String name;
    private String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return phone;
    }
}