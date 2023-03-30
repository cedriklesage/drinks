package com.example.drinks;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    Button btnLogout;
    ImageView ivEditEmail;
    ImageView ivEditPassword;

    private TextView tvUsername, tvEmail;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setContentView(R.layout.fragment_account);

        btnLogout = view.findViewById(R.id.logoutBtn);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getActivity(), Onboarding.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);

        SharedPreferences preferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        int id = preferences.getInt("id", 1);
        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
        Call<User> call = serveur.getUser("getUserInfo", id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String name = response.body().getFirst_name() + " " + response.body().getLast_name();
                tvUsername.setText(name);
                tvEmail.setText(response.body().getEmail());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Une erreur est survenue, veuillez réessayer.", Toast.LENGTH_SHORT).show();
            }
        });

        ivEditEmail = view.findViewById(R.id.ivEditEmail);
        ivEditPassword = view.findViewById(R.id.ivEditPassword);

        ivEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View customTitle = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alertdialog_header, null);
                builder.setCustomTitle(customTitle);
                builder.setCancelable(false);
                builder.setTitle("Modifier");

                View view = getLayoutInflater().inflate(R.layout.layout_edit_email, null);
                builder.setView(view);

                AlertDialog alertDialog = builder.create();

                EditText editEmail = view.findViewById(R.id.editEmail);
                Button btEdEmailValider = view.findViewById(R.id.btEdEmailValider);
                Button btEdEmailAnnuler = view.findViewById(R.id.btEdEmailAnnuler);

                editEmail.setText(tvEmail.getText().toString());

                alertDialog.show();

                btEdEmailValider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        User user = new User(editEmail.getText().toString());
                        user.setId(user.getId());

                        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
                        Call<Boolean> call = serveur.updateEmail("updateEmail", user.getId(), editEmail.getText().toString());

                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                boolean b = response.body();
                                if(b == true){
                                    alertDialog.dismiss();
                                }
                            }
                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                String message = t.getMessage();
                            }
                        });
                    }
                });
                btEdEmailAnnuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        ivEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View customTitle = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alertdialog_header, null);
                builder.setCustomTitle(customTitle);
                builder.setCancelable(false);
                builder.setTitle("Modifier");

                View view = getLayoutInflater().inflate(R.layout.layout_edit_password, null);
                builder.setView(view);

                AlertDialog alertDialog2 = builder.create();

                Button btEdPasswordValider = view.findViewById(R.id.btEdPasswordValider);
                Button btEdPasswordAnnuler = view.findViewById(R.id.btEdPasswordAnnuler);

                alertDialog2.show();

                btEdPasswordValider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

/*                        User user = new User(newPassword.getText().toString());
                        user.setId(user.getId());

                        InterfaceServeur serveur = RetrofitInstance.getRetrofitInstance().create(InterfaceServeur.class);
                        Call<Boolean> call = serveur.updateEmail("updatePassword", user.getId(), newPassword.getText().toString());

                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                boolean b = response.body();
                                if(b == true){
                                    alertDialog2.dismiss();
                                }
                            }
                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                String message = t.getMessage();
                            }
                        });*/
                    }
                });
                btEdPasswordAnnuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog2.dismiss();
                    }
                });
            }
        });
    }
}