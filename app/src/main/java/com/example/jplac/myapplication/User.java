package com.example.jplac.myapplication;

public class User {

    public String firstName;
    public String email;
    public String lastName;
    public String imageRef;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstName, String email, String lastName) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        imageRef = "";
    }
}
