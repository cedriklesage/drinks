package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

public class DoingDrink extends AppCompatActivity {

    ProgressBar bgProgressBar;
    ImageView img;

    ImageButton cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing_drink);

        bgProgressBar = findViewById(R.id.bgProgress);
        img = findViewById(R.id.imgIngredient);
        cancelBtn = findViewById(R.id.cancelBtn);
        Picasso.get().load("http://cours.cegep3r.info/H2023/420606RI/GR06/drinks_images/vodka.png").into(img);

        start();

        cancelBtn.setOnClickListener(v -> cancel());


    }
    public void start()
    {
        bgProgressBar.setProgress(0);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bgProgressBar.setProgress(bgProgressBar.getProgress() + 1);
                if (bgProgressBar.getProgress() < 100) {
                    handler.postDelayed(this, 10);
                }
            }
        }, 10);
    }

    public void cancel()
    {
        finish();
    }
}