package com.example.jplac.myapplication;

import java.util.ArrayList;

public class User {

    public String firstName;
    public String email;
    public String lastName;
    public String imageRef;
    public String authenticationID;
    public String userID;
    public ArrayList<String> courses;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstName, String email, String lastName,String authenticationID,String userID) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.authenticationID = authenticationID;
        this.userID = userID;
        imageRef = "";
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getImageRef() {
        return imageRef;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public String getLastName() {
        return lastName;
    }
    public String getUserID() {
        return userID;
    }

    public String getAuthenticationID() {
        return authenticationID;
    }
}
