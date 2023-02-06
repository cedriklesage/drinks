package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkDetails extends AppCompatActivity {

    private int id;

    CategoryAdapter categoryAdapter;

    RecyclerView categoryRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_details);

        id = getIntent().getIntExtra("id", 2);
        categoryRV = findViewById(R.id.drinkCat);
        categoryRV.setHasFixedSize(true);
        categoryRV.setLayoutManager(new GridLayoutManager(this, 2));

        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
        Call<List<Category>> categoryCall = serveur.getDrinkCategories("getDrinkCategories", id);

        categoryCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categories = response.body();
                //Put in recycler view
                categoryAdapter = new CategoryAdapter(categories);
                categoryRV.setAdapter(categoryAdapter);


            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(DrinkDetails.this, "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });

    }
}