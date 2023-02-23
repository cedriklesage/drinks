package com.example.drinks;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {

    RecyclerView favorisRV;
    DiscoverAdapter favorisAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setContentView(R.layout.fragment_favorites);

        favorisRV = view.findViewById(R.id.discoverRV);

        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);

        SharedPreferences preferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        Integer userId = Integer.valueOf(preferences.getInt("id", 0));

        Call<List<Drink>> call = serveur.getFavoriteDrinks("getLikedDrinks", userId);
        call.enqueue(new Callback<List<Drink>>() {
            @Override
            public void onResponse(Call<List<Drink>> call, Response<List<Drink>> response) {
                List<Drink> drinks = response.body();
                favorisAdapter = new DiscoverAdapter(drinks);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(favorisRV.getContext(), DividerItemDecoration.HORIZONTAL);
                dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.hor_divider_lv1));
                favorisRV.addItemDecoration(dividerItemDecoration);
                favorisRV.setAdapter(favorisAdapter);
                favorisRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }

            @Override
            public void onFailure(Call<List<Drink>> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur de chargement, veuillez réessayer.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}