package com.example.drinks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.drinks.databinding.ActivityLoginBinding;

import retrofit2.Call;

public class Login extends AppCompatActivity {

    TextView mailTxt, passwordTxt, backButton;
    Button loginBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);


        mailTxt = findViewById(R.id.login_EmailTxt);
        passwordTxt = findViewById(R.id.login_PasswordTxt);
        loginBtn = findViewById(R.id.loginBtn);
        backButton = findViewById(R.id.register_BackButton);

        loginBtn.setOnClickListener(v -> {
            String email = mailTxt.getText().toString();
            String password = passwordTxt.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {

                if(email.isEmpty())
                {
                    mailTxt.setBackground(getDrawable(R.drawable.layout_input_bg_error));
                }
                else{
                    mailTxt.setBackground(getDrawable(R.drawable.layout_input_bg));
                }
                if(password.isEmpty())
                {
                    passwordTxt.setBackground(getDrawable(R.drawable.layout_input_bg_error));
                }
                else{
                    passwordTxt.setBackground(getDrawable(R.drawable.layout_input_bg));
                }
            }
            else{
                Call<Integer> call = serveur.login("login", email, password);
                call.enqueue(new retrofit2.Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, retrofit2.Response<Integer> response) {
                        if(response.body() != 0)
                        {
                            SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("id", response.body());
                            editor.apply();
                            Intent intent = new Intent(Login.this, HomeFragment.class);
                            finish();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Snackbar.make(v, "Erreur de connexion", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}