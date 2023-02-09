package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favoris extends AppCompatActivity {

    RecyclerView favorisRV;
    DrinkAdapter favorisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        favorisRV = findViewById(R.id.favorisRV);

        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
        Call<List<Drink>> call = serveur.getFavoriteDrinks("getLikedDrinks", 1);
        call.enqueue(new Callback<List<Drink>>() {
            @Override
            public void onResponse(Call<List<Drink>> call, Response<List<Drink>> response) {
                List<Drink> drinks = response.body();
                favorisAdapter = new DrinkAdapter(drinks);
                favorisRV.setAdapter(favorisAdapter);
                favorisRV.setLayoutManager(new GridLayoutManager(Favoris.this, 2));
            }

            @Override
            public void onFailure(Call<List<Drink>> call, Throwable t) {
                Toast.makeText(Favoris.this, "Erreur de chargement, veuillez réessayer.", Toast.LENGTH_SHORT).show();
            }
        });



    }
}