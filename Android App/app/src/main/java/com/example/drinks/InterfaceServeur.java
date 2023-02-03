package com.example.drinks;

import java.util.List;

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


}
