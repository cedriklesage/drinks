package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.security.MessageDigest;

import retrofit2.Call;

public class Register extends AppCompatActivity {

    TextView prenomTxt, nomTxt, emailTxt, passwordTxt, backButton;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);

        prenomTxt = findViewById(R.id.register_PrenomTxt);
        nomTxt = findViewById(R.id.register_NomTxt);
        emailTxt = findViewById(R.id.register_EmailTxt);
        passwordTxt = findViewById(R.id.register_PasswordTxt);
        nextBtn = findViewById(R.id.nextBtn);
        backButton = findViewById(R.id.register_BackButton);

        nextBtn.setOnClickListener(v -> {
            String prenom = prenomTxt.getText().toString();
            String nom = nomTxt.getText().toString();
            String email = emailTxt.getText().toString();
            String password = passwordTxt.getText().toString();

            if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || password.isEmpty()) {

                if(prenom.isEmpty())
                {
                    prenomTxt.setBackground(getDrawable(R.drawable.layout_input_bg_error));
                }
                else{
                    prenomTxt.setBackground(getDrawable(R.drawable.layout_input_bg));
                }
                if(nom.isEmpty())
                {
                    nomTxt.setBackground(getDrawable(R.drawable.layout_input_bg_error));
                }
                else{
                    nomTxt.setBackground(getDrawable(R.drawable.layout_input_bg));
                }
                if(email.isEmpty())
                {
                    emailTxt.setBackground(getDrawable(R.drawable.layout_input_bg_error));
                }
                else{
                    emailTxt.setBackground(getDrawable(R.drawable.layout_input_bg));
                }
                if(password.isEmpty())
                {
                    passwordTxt.setBackground(getDrawable(R.drawable.layout_input_bg_error));
                }
                else{
                    passwordTxt.setBackground(getDrawable(R.drawable.layout_input_bg));
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
                Call<Boolean> call = serveur.createUser("createUser", prenom, nom, email, password);
                call.enqueue(new retrofit2.Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                        if (response.body()) {
                            Intent intent = new Intent(Register.this, Home.class);
                            finish();
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        System.out.println("Error: " + t.getMessage());
                    }
                });

            }
        });

        backButton.setOnClickListener(v -> {
            finish();
        });

    }
}