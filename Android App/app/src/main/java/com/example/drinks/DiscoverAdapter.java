package com.example.drinks;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {

    private List<Drink> drinks;

    public DiscoverAdapter(List<Drink> drinks) {
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public DiscoverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_drink_discover, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverAdapter.ViewHolder holder, int position) {
        holder.txtName.setText(drinks.get(position).getName());
        Picasso.get().load(drinks.get(position).getImage()).into(holder.imgDrink);
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private int id;
        private TextView txtName;
        private ImageView imgDrink;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtDrinkName);
            imgDrink = itemView.findViewById(R.id.drinkImage);

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DrinkDetails.class);
                    intent.putExtra("id", drinks.get(getAdapterPosition()).getId());
                    intent.putExtra("name", drinks.get(getAdapterPosition()).getName());
                    intent.putExtra("image", drinks.get(getAdapterPosition()).getImage());
                    intent.putExtra("description", drinks.get(getAdapterPosition()).getDescription());
                    intent.putExtra("temps", drinks.get(getAdapterPosition()).getTemps());
                    intent.putExtra("etapes", drinks.get(getAdapterPosition()).getEtapes());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
