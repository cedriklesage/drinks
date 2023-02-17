package com.example.drinks;

import com.google.gson.annotations.SerializedName;

public class Etape {
    @SerializedName("idStep")
    private int id;
    @SerializedName("titre")
    private String titre;
    @SerializedName("description")
    private String description;
    @SerializedName("stepType")
    private String type;
    @SerializedName("quantite")
    private double quantite;
    @SerializedName("temps")
    private int temps;

    @SerializedName("nomIngredient")
    private String nomIngredient;
    @SerializedName("imageIngredient")
    private String image;

    public Etape(int id, String titre, String description, String type, double quantite, int temps, String nomIngredient, String image) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.quantite = quantite;
        this.temps = temps;
        this.nomIngredient = nomIngredient;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public double getQuantite() {
        return quantite;
    }

    public int getTemps() {
        return temps;
    }

    public String getNomIngredient() {
        return nomIngredient;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public void setNomIngredient(String nomIngredient) {
        this.nomIngredient = nomIngredient;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
