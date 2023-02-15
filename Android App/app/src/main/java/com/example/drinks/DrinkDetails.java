package com.example.drinks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinkDetails extends AppCompatActivity {

    private int id;
    private String name, description , temps, etapes, image;
    private Boolean isFavorite;
    CategoryAdapter categoryAdapter;
    IngredientAdapter ingredientAdapter;
    RecyclerView categoryRV, ingredientsRV;

    Button startBtn;
    ImageButton likeBtn;
    public InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_details);

        startBtn = findViewById(R.id.btnStart);
        likeBtn = findViewById(R.id.btnLike);

        id = getIntent().getIntExtra("id", 0);
        temps = getIntent().getStringExtra("temps");
        etapes = getIntent().getStringExtra("etapes");
        image = getIntent().getStringExtra("image");

        categoryRV = findViewById(R.id.drinkCat);
        categoryRV.setHasFixedSize(true);
        categoryRV.setLayoutManager(new GridLayoutManager(this, 3));

        ingredientsRV = findViewById(R.id.drinkIng);
        ingredientsRV.setHasFixedSize(true);
        ingredientsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        // Set the details of the drink

        TextView txtName = findViewById(R.id.drinkName);
        TextView txtDescription = findViewById(R.id.txtDescription);
        Button btnStart = findViewById(R.id.btnStart);

        txtName.setText(getIntent().getStringExtra("name"));
        txtDescription.setText(getIntent().getStringExtra("description"));
        btnStart.setText("Commencer (" + etapes + " étapes)");
        ImageView imgDrink = findViewById(R.id.imageDrink);
        Picasso.get().load(image).into(imgDrink);


        // Get the categories of the drink
        Call<List<Category>> categoryCall = serveur.getDrinkCategories("getDrinkCategories", id);
        categoryCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categories = response.body();
                //Put in recycler view
                categoryAdapter = new CategoryAdapter(categories);
                categoryRV.setAdapter(categoryAdapter);
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(DrinkDetails.this, "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<Ingredient>> ingredientCall = serveur.getDrinkIngredients("getDrinkIngredients", id);
        ingredientCall.enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                List<Ingredient> ingredients = response.body();
                //Put in recycler view
                ingredientAdapter = new IngredientAdapter(ingredients);
                ingredientsRV.setAdapter(ingredientAdapter);
            }
            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                Toast.makeText(DrinkDetails.this, "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        Integer userId = Integer.valueOf(preferences.getInt("id", 0));
        // Check if the drink is in the favorites
        Call<Boolean> favoriteCall = serveur.isFavorite("checkLike", id, userId);
        favoriteCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean checkLike = response.body();
                if(checkLike){
                    ImageButton btnLike = findViewById(R.id.btnLike);
                    btnLike.setImageDrawable(getDrawable(R.drawable.ic_like_on));
                    isFavorite = true;
                }
                else {
                    ImageButton btnLike = findViewById(R.id.btnLike);
                    btnLike.setImageDrawable(getDrawable(R.drawable.ic_like_off));
                    isFavorite = false;
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(DrinkDetails.this, "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });

        startBtn.setOnClickListener(v -> StartDrink());
        likeBtn.setOnClickListener(v -> likeButton());


    }

    public void StartDrink()
    {
        Intent intent = new Intent(this, DoingDrink.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    public void likeButton()
    {
        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        Integer userId = Integer.valueOf(preferences.getInt("id", 0));
        Call<Boolean> changeLikeCall = serveur.changeLike("switchLike", id, userId);
        changeLikeCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean changeLike = response.body();
                if(changeLike){
                    ImageButton btnLike = findViewById(R.id.btnLike);
                    btnLike.setImageDrawable(getDrawable(R.drawable.ic_like_on));
                    isFavorite = true;
                }
                else {
                    ImageButton btnLike = findViewById(R.id.btnLike);
                    btnLike.setImageDrawable(getDrawable(R.drawable.ic_like_off));
                    isFavorite = false;
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(DrinkDetails.this, "Erreur de chargement, veuillez réessayer", Toast.LENGTH_SHORT).show();
            }
        });

    }

}