package com.example.events.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Group {

    public int id;
    public String title;

    public Group() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Group(String title) {
        this.title = title;
    }

}