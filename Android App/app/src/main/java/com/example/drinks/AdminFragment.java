package com.example.drinks;

import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminFragment extends Fragment {

    Button btnListUsers;
    Button btnAddUser;
    int admin;

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnListUsers = view.findViewById(R.id.listUsersBtn);
        btnAddUser = view.findViewById(R.id.addUserBtn);

        btnListUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = NavHostFragment.findNavController(AdminFragment.this);
                navController.navigate(R.id.action_navAdmin_to_usersListFragment);
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setTitle("Ajouter un utilisateur");

                View view = getLayoutInflater().inflate(R.layout.layout_add, null);
                builder.setView(view);

                AlertDialog alertDialog = builder.create();

                EditText edPrenom = view.findViewById(R.id.edPrenom);
                EditText edNom = view.findViewById(R.id.edNom);
                EditText edEmail = view.findViewById(R.id.edEmail);
                EditText edPassword = view.findViewById(R.id.edPassword);
                RadioButton rbAdmin = view.findViewById(R.id.rbAdmin);
                RadioButton rbUser = view.findViewById(R.id.rbUser);
                Button btValider = view.findViewById(R.id.btValider);
                Button btAnnuler = view.findViewById(R.id.btAnnuler);

                rbAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            admin = 1;
                        }
                        else {
                            admin = 0;
                        }
                    }
                });

                alertDialog.show();

                btValider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String prenom = edPrenom.getText().toString();
                        String nom = edNom.getText().toString();
                        String email = edEmail.getText().toString();
                        String password = edPassword.getText().toString();
                        RadioButton rbAdmin = view.findViewById(R.id.rbAdmin);
                        RadioButton rbUser = view.findViewById(R.id.rbUser);

                        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || password.isEmpty()) {

                            if(prenom.isEmpty())
                            {
                                edPrenom.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg_error));
                            }
                            else{
                                edPrenom.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg));
                            }
                            if(nom.isEmpty())
                            {
                                edNom.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg_error));
                            }
                            else{
                                edNom.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg));
                            }
                            if(email.isEmpty())
                            {
                                edEmail.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg_error));
                            }
                            else{
                                edEmail.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg));
                            }
                            if(password.isEmpty())
                            {
                                edPassword.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg_error));
                            }
                            else{
                                edPassword.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg));
                            }
                        }

                        else {
                            SharedPreferences.Editor register = getSharedPreferences("register", MODE_PRIVATE).edit();
                            register.putString("prenom", prenom);
                            register.putString("nom", nom);
                            register.putString("email", email);
                            register.putString("password", password);
                            register.apply();

                            //Create the user
                            Call<Integer> call = serveur.createUser("createUser", prenom, nom, email, password);
                            call.enqueue(new retrofit2.Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                                    if (response.body() != 0) {
                                        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putInt("id", response.body());
                                        editor.apply();
                                        Intent intent = new Intent(Register.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    System.out.println("Error: " + t.getMessage());
                                }
                            });
                        }
                    }
                });
                btAnnuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }
}