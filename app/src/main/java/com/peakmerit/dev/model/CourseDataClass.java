package com.peakmerit.dev.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CourseDataClass {
    private ArrayList<String> tags;
    private String name, description, charge;
    @SerializedName("author")
    private AuthorDataClass author;

    @SerializedName("_id")
    private String id;

    public CourseDataClass(ArrayList<String> tags, String name, AuthorDataClass author, String id) {
        this.tags = tags;
        this.name = name;
        this.author = author;
        this.id = id;
    }

    public CourseDataClass(ArrayList<String> tags, String name, String description, AuthorDataClass author, String id, String charge) {
        this.tags = tags;
        this.name = name;
        this.description = description;
        this.author = author;
        this.id = id;
        this.charge = charge;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public CourseDataClass(String id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author.name;
    }

    public void setAuthor(AuthorDataClass author) {
        this.author = author;
    }
}
