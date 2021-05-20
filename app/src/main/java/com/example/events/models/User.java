package com.example.events.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public int id;
    public String name;
    public int group_id;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, int group_id) {
        this.name = name;
        this.group_id = group_id;
    }

}
