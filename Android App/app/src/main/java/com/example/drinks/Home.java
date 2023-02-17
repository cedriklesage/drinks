package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    DrinkAdapter weeklyDrinkAdapter;
    RecyclerView weekDrinkRV;

    Drink dailyDrink;


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
                dailyDrink = response.body();
                TextView drinkName = findViewById(R.id.drinkName);
                drinkName.setText(dailyDrink.getName());

                ImageView drinkImage = findViewById(R.id.imageDrink);
                Picasso.get().load(dailyDrink.getImage()).into(drinkImage);
            }

            @Override
            public void onFailure(Call<Drink> call, Throwable t) {
                Toast.makeText(Home.this, "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
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
                weekDrinkRV.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
                weeklyDrinkAdapter = new DrinkAdapter(drinks);
                weekDrinkRV.setAdapter(weeklyDrinkAdapter);
            }

            @Override
            public void onFailure(Call<List<Drink>> call, Throwable t) {
                Toast.makeText(Home.this, "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });

        Button favoriBtn = findViewById(R.id.favoriBtn);
        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
            }
        });
        favoriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, FavoritesFragment.class);
                startActivity(intent);
            }
        });
    }

    public void dailyDrinkBtn(View view) {
        Intent intent = new Intent(view.getContext(), DrinkDetails.class);
        intent.putExtra("id", dailyDrink.getId());
        intent.putExtra("name", dailyDrink.getName());
        intent.putExtra("image", dailyDrink.getImage());
        intent.putExtra("description", dailyDrink.getDescription());
        intent.putExtra("temps", dailyDrink.getTemps());
        intent.putExtra("etapes", dailyDrink.getEtapes());
        view.getContext().startActivity(intent);

    }
    @Override
    public void onBackPressed() {



    }

}