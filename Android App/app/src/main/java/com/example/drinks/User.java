package com.example.drinks;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    int id;

    @SerializedName("first_name")
    String first_name;

    @SerializedName("last_name")
    String last_name;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    @SerializedName("admin")
    int admin;

    public User(int id, String first_name, String last_name, String email, String password, int admin) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public User(String first_name, String last_name, String email, String password, int admin) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public User(String first_name, String last_name, String email, int admin) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.admin = admin;
    }

    public User(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAdmin() {
        return admin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

}
