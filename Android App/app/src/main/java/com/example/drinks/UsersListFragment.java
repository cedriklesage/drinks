package com.example.drinks;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersListFragment extends Fragment implements InterfaceClick {

    private RecyclerView recyclerView;
    private AdapterList adapterList;
    int admin;

    TextView listBackButton;

    public UsersListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_users_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.rvListUsers);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
        Call<List<User>> call = serveur.getUsers("getUsers");

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                adapterList = new AdapterList(users, UsersListFragment.this, getActivity());
                recyclerView.setAdapter(adapterList);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                String message = t.getMessage();
            }
        });
    }

    @Override
    public void gestionClick(User u, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View customTitle = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alertdialog_header, null);
        builder.setCustomTitle(customTitle);
        builder.setCancelable(false);
        builder.setTitle("Modifier");

        View view = getLayoutInflater().inflate(R.layout.layout_edit, null);
        builder.setView(view);

        AlertDialog alertDialog1 = builder.create();

        EditText upPrenom = view.findViewById(R.id.upPrenom);
        EditText upNom = view.findViewById(R.id.upNom);
        EditText upEmail = view.findViewById(R.id.upEmail);
        RadioButton rbUpAdmin = view.findViewById(R.id.rbUpAdmin);
        RadioButton rbUpUser = view.findViewById(R.id.rbUpUser);
        Button btUpValider = view.findViewById(R.id.btUpValider);
        Button btUpAnnuler = view.findViewById(R.id.btUpAnnuler);

        upPrenom.setText(u.getFirst_name());
        upNom.setText(u.getLast_name());
        upEmail.setText(u.getEmail());

        if(u.getAdmin() == 1){
            rbUpAdmin.setChecked(true);
            admin = 1;
        }
        else{
            rbUpUser.setChecked(true);
            admin = 0;
        }

        rbUpAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    admin = 1;
                }
                else {
                    admin = 0;
                }
            }
        });

        alertDialog1.show();

        btUpValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(upPrenom.getText().toString(), upNom.getText().toString(), upEmail.getText().toString(), admin);
                user.setId(u.getId());

                InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
                Call<Boolean> call = serveur.updateUser("updateUser", u.getId(), upPrenom.getText().toString(), upNom.getText().toString(), upEmail.getText().toString(), admin);

                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        boolean b = response.body();
                        if(b == true){
                            adapterList.updateUser(user, position);
                            alertDialog1.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        String message = t.getMessage();
                    }
                });
            }
        });

        btUpAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
    }

    @Override
    public void gestionLongClick(int position, int id) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Supprimer");
        builder.setMessage("Voulez-vous supprimer cet usager ?");
        builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapterList.deleteUser(position);

                InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
                Call<Boolean> call = serveur.deleteUser("deleteUser", id);

                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        boolean b = response.body();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        String message = t.getMessage();
                    }
                });
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}