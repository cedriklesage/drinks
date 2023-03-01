package com.example.drinks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.MonViewHolder> {

    private List<User> listeUser;

    public AdapterList(List<User> l)
    {
        this.listeUser = l;
    }

    @Override
    public MonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user, parent, false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonViewHolder holder, int position) {
        holder.txtFirst_Name.setText(listeUser.get(position).getFirst_name());
    }

    @Override
    public int getItemCount() {
        return listeUser.size();
    }

    public class MonViewHolder extends RecyclerView.ViewHolder {

        TextView txtFirst_Name;

        public MonViewHolder(@NonNull View itemView) {

            super(itemView);

            txtFirst_Name = itemView.findViewById(R.id.txtName);
        }
    }
}
