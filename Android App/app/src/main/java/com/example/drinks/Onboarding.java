package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class Onboarding extends AppCompatActivity {

    Button registerBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        registerBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.loginBtn);

        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Onboarding.this, Register.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Onboarding.this, Login.class);
            startActivity(intent);
        });

        // Check if user is already logged in
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        int id = preferences.getInt("id", -1);
        if(id != -1)
        {
            Intent intent = new Intent(Onboarding.this, MainActivity.class);
            finish();
            startActivity(intent);
        }

    }


}