package com.example.drinks;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    DrinkAdapter weeklyDrinkAdapter;
    RecyclerView weekDrinkRV;

    Drink dailyDrink;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
        weekDrinkRV = view.findViewById(R.id.weekDrinks);



        //Get the drink of the day
        Call<Drink> call = serveur.getDailyDrink("getDailyDrink");
        call.enqueue(new Callback<Drink>() {
            @Override
            public void onResponse(Call<Drink> call, Response<Drink> response) {
                dailyDrink = response.body();
                TextView drinkName = view.findViewById(R.id.drinkName);
                drinkName.setText(dailyDrink.getName());

                ImageView drinkImage = view.findViewById(R.id.imageDrink);
                Picasso.get().load(dailyDrink.getImage()).into(drinkImage);
            }

            @Override
            public void onFailure(Call<Drink> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
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
                weekDrinkRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                weeklyDrinkAdapter = new DrinkAdapter(drinks);
                weekDrinkRV.setAdapter(weeklyDrinkAdapter);
            }

            @Override
            public void onFailure(Call<List<Drink>> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });


    }
}