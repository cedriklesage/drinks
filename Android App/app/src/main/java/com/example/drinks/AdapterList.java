package com.example.drinks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.MonViewHolder> {

    private List<User> listeUser;
    InterfaceClick gestionClick;
    Context context;

    public AdapterList(List<User> l, InterfaceClick InterfaceClick, Context context) {
        gestionClick = InterfaceClick;
        this.listeUser = l;
    }

    @Override
    public MonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user, parent, false);
        return new MonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonViewHolder holder, int position) {
        holder.firstNameTxt.setText(listeUser.get(position).getFirst_name() + " " + listeUser.get(position).getLast_name());
        holder.emailTxt.setText(listeUser.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return listeUser.size();
    }

    public void deleteUser(int position) {
        listeUser.remove(position);
        notifyItemRemoved(position);
    }

    public void addUser(User u) {
        listeUser.add(u);
        notifyItemInserted(listeUser.size() - 1);
    }

    public void updateUser(User u, int position) {
        listeUser.set(position, u);
        notifyItemChanged(position);
    }

    public class MonViewHolder extends RecyclerView.ViewHolder {

        TextView firstNameTxt;
        TextView emailTxt;

        public MonViewHolder(@NonNull View itemView) {

            super(itemView);

            firstNameTxt = itemView.findViewById(R.id.txtName);
            emailTxt = itemView.findViewById(R.id.txtEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    User u = listeUser.get(position);

                    gestionClick.gestionClick(u, position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    int id = listeUser.get(position).getId();
                    gestionClick.gestionLongClick(position, id);
                    return true;
                }
            });
        }

    }
}
