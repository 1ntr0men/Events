package com.example.events.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Event {


    public int id;
    public String title;
    public int of_group;
    public String date;
    public String time;
    public String description;
    public String tags;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Event(String title, String description, String tags, String date, String time) {
        this.title = title;
        this.of_group = of_group;
        this.date = date;
        this.time = time;
        this.description = description;
        this.tags = tags;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("of_group", of_group);
        result.put("date", date);
        result.put("time", time);
        result.put("description", description);
        result.put("tags", tags);

        return result;

    }

}