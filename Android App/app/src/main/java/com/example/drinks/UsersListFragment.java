package com.example.drinks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class UsersListFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterList adapterList;

    public UsersListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = getView().findViewById(R.id.rvListUsers);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(this)));

        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
        Call<List<User>> call = serveur.getUsers("getUsers");

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                adapterList = new AdapterList(users, UsersListFragment.this);
                recyclerView.setAdapter(adapterList);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                String message = t.getMessage();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_users_list, container, false);
    }
}