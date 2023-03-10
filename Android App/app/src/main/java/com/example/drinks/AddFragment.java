package com.example.drinks;

import static android.app.Activity.RESULT_CANCELED;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import static com.example.drinks.R.drawable.layout_input_bg;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFragment extends Fragment {

    TextView addBackButton;
    EditText edPrenom;
    EditText edNom;
    EditText edEmail;
    EditText edPassword;
    RadioButton rbUser;
    RadioButton rbAdmin;
    int admin;

    Button btValider;
    Button btAnnuler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addBackButton = getActivity().findViewById(R.id.add_BackButton);
        addBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AddFragment.this)
                        .navigate(R.id.action_addFragment_to_navAdmin);
            }
        });

        btValider = view.findViewById(R.id.btValider);
        btValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestionValider(v);
            }
        });
        btAnnuler = view.findViewById(R.id.btAnnuler);
        btAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestionAnnuler(v);
            }
        });
        rbAdmin = view.findViewById(R.id.rbAdmin);
        rbUser = view.findViewById(R.id.rbUser);
        rbAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onRadioButtonClicked(buttonView);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rbUser:
                if (checked)
                    admin = 0;
                break;
            case R.id.rbAdmin:
                if (checked)
                    admin = 1;
                break;
        }
    }

    public void gestionValider(View view) {
        edPrenom = getActivity().findViewById(R.id.edPrenom);
        edNom = getActivity().findViewById(R.id.edNom);
        edEmail = getActivity().findViewById(R.id.edEmail);
        edPassword = getActivity().findViewById(R.id.edPassword);
        rbUser = getActivity().findViewById(R.id.rbUser);
        rbAdmin = getActivity().findViewById(R.id.rbAdmin);

        if (edPrenom.getText().toString().equals("") || edNom.getText().toString().equals("")|| edEmail.getText().toString().equals("") || edPassword.getText().toString().equals("")) {
            if(edPrenom.getText().toString().equals(""))
            {
                edPrenom.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg_error));
            }
            else{
                edPrenom.setBackground(getDrawable(getActivity(), layout_input_bg));
            }
            if(edNom.getText().toString().equals(""))
            {
                edNom.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg_error));
            }
            else{
                edNom.setBackground(getDrawable(getActivity(), layout_input_bg));
            }
            if(edEmail.getText().toString().equals(""))
            {
                edEmail.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg_error));
            }
            else{
                edEmail.setBackground(getDrawable(getActivity(), layout_input_bg));
            }
            if(edPassword.getText().toString().equals(""))
            {
                edPassword.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg_error));
            }
            else{
                edPassword.setBackground(getDrawable(getActivity(), R.drawable.layout_input_bg));
            }
        }

        else {
            InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
            Call<Integer> call = serveur.createUserAdmin("createUserAdmin", edPrenom.getText().toString(), edNom.getText().toString(), edEmail.getText().toString(), edPassword.getText().toString(), admin);
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    int b = response.body();
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    String message = t.getMessage();
                }
            });
            NavController navController = NavHostFragment.findNavController(AddFragment.this);
            navController.navigate(R.id.action_addFragment_to_navAdmin);
            Toast.makeText(getActivity(), "Utilisateur ajouté avec succès!", Toast.LENGTH_LONG).show();
        }
    }

    public void gestionAnnuler(View view) {
        NavController navController = NavHostFragment.findNavController(AddFragment.this);
        navController.navigate(R.id.action_addFragment_to_navAdmin);
    }
}