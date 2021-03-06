package com.example.events.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public int group_id;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.group_id = 0;
    }

}
