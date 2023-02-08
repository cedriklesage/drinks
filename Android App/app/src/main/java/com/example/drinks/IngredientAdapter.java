package com.example.drinks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredients;

    public IngredientAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.name.setText(ingredients.get(position).getName());
        holder.type.setText(ingredients.get(position).getType());
        holder.quantite.setText(toString().valueOf("(" + ingredients.get(position).getQuantite() + "ml)"));
        if(ingredients.get(position).getAlcool() == 0.0)
            holder.alcool.setText("");
        else
            holder.alcool.setText(toString().valueOf("(" + ingredients.get(position).getAlcool() + "%)"));
        Picasso.get().load(ingredients.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder
    {

        //If the name of the view is 'drink_details'

        private TextView name;
        private TextView type;
        private TextView alcool;
        private TextView pays;
        private TextView quantite;
        private ImageView image;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ingredientName);
            type = itemView.findViewById(R.id.ingredientType);
            alcool = itemView.findViewById(R.id.ingredientAlcool);
            quantite = itemView.findViewById(R.id.ingredientQuantity);
            image = itemView.findViewById(R.id.ingredientImg);
        }
    }



}
