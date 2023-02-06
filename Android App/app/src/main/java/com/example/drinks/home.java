package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends AppCompatActivity {

    DrinkAdapter weeklyDrinkAdapter;
    RecyclerView weekDrinkRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
        weekDrinkRV = findViewById(R.id.weekDrinks);

        //Get the drink of the day
        Call<Drink> call = serveur.getDailyDrink("getDailyDrink");
        call.enqueue(new Callback<Drink>() {
            @Override
            public void onResponse(Call<Drink> call, Response<Drink> response) {
                Drink drink = response.body();
                TextView drinkName = findViewById(R.id.drinkName);
                drinkName.setText(drink.getName());

                ImageView drinkImage = findViewById(R.id.dotd_image);
                Picasso.get().load(drink.getImage()).into(drinkImage);
            }

            @Override
            public void onFailure(Call<Drink> call, Throwable t) {
                Toast.makeText(home.this, "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });

        //Get the drink of the week
        Call<List<Drink>> call2 = serveur.getDrinks("getWeeklyDrinks");
        call2.enqueue(new Callback<List<Drink>>() {
            @Override
            public void onResponse(Call<List<Drink>> call, Response<List<Drink>> response) {
                //Put in recycler view
                List<Drink> drinks = response.body();
                weekDrinkRV.setHasFixedSize(true);
                weekDrinkRV.setLayoutManager(new LinearLayoutManager(home.this, LinearLayoutManager.HORIZONTAL, false));
                weeklyDrinkAdapter = new DrinkAdapter(drinks);
                weekDrinkRV.setAdapter(weeklyDrinkAdapter);
            }

            @Override
            public void onFailure(Call<List<Drink>> call, Throwable t) {
                Toast.makeText(home.this, "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });
    }

}