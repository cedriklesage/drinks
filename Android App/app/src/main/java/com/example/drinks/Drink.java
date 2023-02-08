package com.example.drinks;

import com.google.gson.annotations.SerializedName;

import kotlin.jvm.internal.SerializedIr;

public class Drink {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("temps")
    private String temps;

    @SerializedName("etapes")
    private String etapes;

    @SerializedName("image")
    private String image;

    public Drink(int id, String name, String description, String temps, String etapes, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.temps = temps;
        this.etapes = etapes;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTemps() {
        return temps;
    }

    public String getEtapes() {
        return etapes;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public void setEtapes(String etapes) {
        this.etapes = etapes;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
