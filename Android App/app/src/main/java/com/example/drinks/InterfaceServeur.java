package com.example.drinks;

import android.content.SharedPreferences;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InterfaceServeur {


    //Get the drink of the day
    @POST("drinks.php")
    @FormUrlEncoded
    Call<Drink> getDailyDrink(@Field("requete") String action);

    //Get the drink of the week
    @POST("drinks.php")
    @FormUrlEncoded
    Call<List<Drink>> getDrinks(@Field("requete") String action);

    // Get the categories of the drink
    @POST("categories.php")
    @FormUrlEncoded
    Call<List<Category>> getDrinkCategories(@Field("requete") String action, @Field("id") int id);

    // Get the ingredients of the drink
    @POST("drinks.php")
    @FormUrlEncoded
    Call<List<Ingredient>> getDrinkIngredients(@Field("requete") String action, @Field("id") int id);

    // Check if the drink is in the favorites
    @POST("drinks.php")
    @FormUrlEncoded
    Call<Boolean> isFavorite(@Field("requete") String action, @Field("idDrink") int id, @Field("idUser") int idUser);

    // Change the status of the like button
    @POST("drinks.php")
    @FormUrlEncoded
    Call<Boolean> changeLike(@Field("requete") String action, @Field("idDrink") int id, @Field("idUser") int idUser);

    // Get the favorite drinks of the user

    // Get ID of user from shared preferences
    @POST("drinks.php")
    @FormUrlEncoded
    Call<List<Drink>> getFavoriteDrinks(@Field("requete") String action, @Field("idUser") int idUser);

    // Get steps of the drink
    @POST("drinks.php")
    @FormUrlEncoded
    Call<List<Etape>> loadSteps(@Field("requete") String action, @Field("idDrink") int id);

    // Search Drinks
    @POST("drinks.php")
    @FormUrlEncoded
    Call<List<Drink>> searchDrinks(@Field("requete") String action,
                                   @Field("search") String search);


    // USER //

    //Create a new user
    @POST("users.php")
    @FormUrlEncoded
    Call<Integer> createUser(@Field("requete") String action,
                             @Field("prenom") String prenom,
                             @Field("nom") String nom,
                             @Field("email") String email,
                             @Field("password") String password);

    //Login
    @POST("users.php")
    @FormUrlEncoded
    Call<Integer> login(@Field("requete") String action,
                     @Field("email") String email,
                     @Field("password") String password);

    //Get list of users
    @POST("users.php")
    @FormUrlEncoded
    Call<List<User>> getUsers(@Field("requete") String action);

    //Delete user
    @POST("users.php")
    @FormUrlEncoded
    Call<Boolean> deleteUser(@Field("requete") String action,
                                  @Field("id") int id);

    //Update user
    @POST("users.php")
    @FormUrlEncoded
    Call<Boolean> updateUser(@Field("requete") String action,
                             @Field("id") int id,
                             @Field("prenom") String prenom,
                             @Field("nom") String nom,
                             @Field("email") String email,
                             @Field("admin") int admin);

    //Create a new user admin side
    @POST("users.php")
    @FormUrlEncoded
    Call<Integer> createUserAdmin(@Field("requete") String action,
                                  @Field("prenom") String prenom,
                                  @Field("nom") String nom,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("admin") int admin);
}
