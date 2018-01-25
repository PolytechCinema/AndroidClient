package cinema.polytech.com.projetcinema.service;

import java.util.List;

import cinema.polytech.com.projetcinema.metier.Acteur;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by coloc on 23/01/2018.
 */

public interface CinemaWS {
    @GET("/acteur/")
    Call<List<Acteur>> listActeurs();

    @GET("/acteur/{id}")
    Call<Acteur> getActeur(@Path("id") int id);

    @PUT("/acteur/{id}")
    Call<Acteur> editActeur(@Path("id") int id, @Body Acteur acteur);

    @DELETE("/acteur/{id}")
    Call<Void> deleteActeur(@Path("id") int id);

    @POST("/acteur/")
    Call<Void> createActeur(@Body Acteur acteur);
}
