package com.example.drinks;

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
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    RecyclerView discoverRV;
    DiscoverAdapter discoverAdapter;
    SearchView searchView;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setContentView(R.layout.fragment_favorites);

        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        discoverRV = view.findViewById(R.id.discoverRV);

        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);

        Call<List<Drink>> call = serveur.getDrinks("getRecommendedDrinks");
        call.enqueue(new Callback<List<Drink>>() {
            @Override
            public void onResponse(Call<List<Drink>> call, Response<List<Drink>> response) {
                List<Drink> drinks = response.body();
                discoverRV.setHasFixedSize(true);
                discoverRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                discoverAdapter = new DiscoverAdapter(drinks);
                discoverRV.setAdapter(discoverAdapter);

            }

            @Override
            public void onFailure(Call<List<Drink>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterList(String text) {
        List<Drink> filteredList = new ArrayList<>();

        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);

        Call<List<Drink>> call = serveur.searchDrinks("searchDrinks", text);
        call.enqueue(new Callback<List<Drink>>() {
            @Override
            public void onResponse(Call<List<Drink>> call, Response<List<Drink>> response) {
                List<Drink> drinks = response.body();
                discoverRV.setAdapter(new DiscoverAdapter(drinks));
            }

            @Override
            public void onFailure(Call<List<Drink>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}