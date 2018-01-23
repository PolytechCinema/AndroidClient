package cinema.polytech.com.projetcinema.service;

import java.util.List;

import cinema.polytech.com.projetcinema.metier.Acteur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by coloc on 23/01/2018.
 */

public interface CinemaWS {
    @GET("/acteur/")
    Call<List<Acteur>> listActeurs();
}
