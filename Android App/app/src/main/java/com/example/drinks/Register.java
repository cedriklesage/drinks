package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
        emailTxt = findViewById(R.id.login_EmailTxt);
        passwordTxt = findViewById(R.id.login_PasswordTxt);
        nextBtn = findViewById(R.id.loginBtn);
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
                Call<Integer> call = serveur.createUser("createUser", prenom, nom, email, password);
                call.enqueue(new retrofit2.Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                        if (response.body() != 0) {
                            SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("id", response.body());
                            editor.apply();
                            Intent intent = new Intent(Register.this, Home.class);
                            finish();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
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