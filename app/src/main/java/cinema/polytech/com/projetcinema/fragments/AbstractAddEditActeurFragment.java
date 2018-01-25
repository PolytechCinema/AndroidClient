package cinema.polytech.com.projetcinema.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

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

public abstract class AbstractAddEditActeurFragment extends Fragment{

    protected void sendAddRequest(Acteur acteur){
        CinemaWS cinemaWS = new Retrofit.Builder().baseUrl(MainActivity.CINEMA_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(CinemaWS.class);
        Call<Void> call = cinemaWS.createActeur(acteur);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), R.string.acteur_added,Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();
                } else {
                    Toast.makeText(getActivity(), R.string.acteur_adding_error,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(getString(R.string.error_adding), t.getMessage());
            }
        });
    }

    protected void sendEditRequest(int id, Acteur acteur){
        CinemaWS cinemaWS = new Retrofit.Builder().baseUrl(MainActivity.CINEMA_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(CinemaWS.class);
        Call<Acteur> call = cinemaWS.editActeur(id, acteur);
        call.enqueue(new Callback<Acteur>() {
            @Override
            public void onResponse(Call<Acteur> call, Response<Acteur> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), R.string.acteur_edited,Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();
                } else {
                    Toast.makeText(getActivity(), R.string.acteur_edition_error,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Acteur> call, Throwable t) {
                Log.d(getString(R.string.error_editing), t.getMessage());
            }
        });
    }

    protected void sendDeleteRequest(int id){
        CinemaWS cinemaWS = new Retrofit.Builder().baseUrl(MainActivity.CINEMA_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(CinemaWS.class);
        Call<Void> call = cinemaWS.deleteActeur(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), R.string.acteur_deleted,Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStackImmediate();
                } else {
                    Toast.makeText(getActivity(), R.string.acteur_deletion_error,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(getString(R.string.error_deleting), t.getMessage());
            }
        });
    }

    protected Acteur createActeur(){
        EditText nameEditText = getActivity().findViewById(R.id.editName);
        final String name = nameEditText.getText().toString();
        EditText firstnameEditText = getActivity().findViewById(R.id.editFirstname);
        final String firstname = firstnameEditText.getText().toString();
        EditText birthdayEditText = getActivity().findViewById(R.id.editBirthday);
        final String birthday = birthdayEditText.getText().toString();
        EditText deathEditText = getActivity().findViewById(R.id.editDeath);
        final String death = deathEditText.getText().toString();

        return new Acteur(name, firstname, birthday, death);
    }
}
