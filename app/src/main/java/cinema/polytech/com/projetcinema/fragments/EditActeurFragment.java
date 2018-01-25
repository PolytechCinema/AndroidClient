package cinema.polytech.com.projetcinema.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import cinema.polytech.com.projetcinema.MainActivity;
import cinema.polytech.com.projetcinema.R;
import cinema.polytech.com.projetcinema.metier.Acteur;
import cinema.polytech.com.projetcinema.service.CinemaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by coloc on 25/01/2018.
 */

public class EditActeurFragment extends AbstractAddEditActeurFragment {

    private int id = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_acteur, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CinemaWS cinemaWS = new Retrofit.Builder().baseUrl(MainActivity.CINEMA_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(CinemaWS.class);

        id = getArguments().getInt("id");

        Call<Acteur> call = cinemaWS.getActeur(id);
        call.enqueue(new Callback<Acteur>() {
            @Override
            public void onResponse(Call<Acteur> call, Response<Acteur> response) {
                if (response.isSuccessful()){
                    fillForm(response.body());
                    initButtons();
                }
            }

            @Override
            public void onFailure(Call<Acteur> call, Throwable t) {
                Log.d(getString(R.string.error_fetching), t.getMessage());
            }
        });
    }

    private void initButtons() {
        // Send button
        getActivity().findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEditRequest(id, createActeur());
            }
        });

        // Delete button
        getActivity().findViewById(R.id.deleteActor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.delete)
                        .setMessage(R.string.delete_question)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sendDeleteRequest(id);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void fillForm(Acteur acteur){
        EditText nameEditText = getActivity().findViewById(R.id.editName);
        EditText firstnameEditText = getActivity().findViewById(R.id.editFirstname);
        EditText birthdayEditText = getActivity().findViewById(R.id.editBirthday);
        EditText deathEditText = getActivity().findViewById(R.id.editDeath);
        nameEditText.setText(acteur.getNom());
        firstnameEditText.setText(acteur.getPrenom());
        birthdayEditText.setText(acteur.getDateNaiss());
        deathEditText.setText(acteur.getDateDeces());
    }


}
