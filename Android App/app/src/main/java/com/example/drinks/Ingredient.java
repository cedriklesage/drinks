package com.example.drinks;

public class Ingredient {

    private int id;
    private String nom;
    private String type;
    private double alcool;
    private String pays;
    private String image;
    private double quantite;

    // Constructeur avec ingredient
    public Ingredient(int id, String name, String type, double alcool, String pays, String image, double quantite) {
        this.id = id;
        this.nom = name;
        this.type = type;
        this.alcool = alcool;
        this.pays = pays;
        this.image = image;
        this.quantite = quantite;
    }

    // Constructeur sans ingredient
    public Ingredient(int id, String name, String type, double alcool, String pays, String image) {
        this.id = id;
        this.nom = name;
        this.type = type;
        this.alcool = alcool;
        this.pays = pays;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public double getAlcool() {
        return alcool;
    }

    public String getPays() {
        return pays;
    }

    public String getImage() {
        return image;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.nom = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAlcool(double alcool) {
        this.alcool = alcool;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

}
