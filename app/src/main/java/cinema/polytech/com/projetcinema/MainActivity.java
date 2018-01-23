package cinema.polytech.com.projetcinema;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import cinema.polytech.com.projetcinema.metier.Acteur;
import cinema.polytech.com.projetcinema.service.CinemaWS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String CINEMA_BASE_URL = "http://192.168.42.98:8080";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CinemaWS cinemaWS = new Retrofit.Builder().baseUrl(CINEMA_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(CinemaWS.class);
        Call<List<Acteur>> call = cinemaWS.listActeurs();
        call.enqueue(new Callback<List<Acteur>>() {
            @Override
            public void onResponse(Call<List<Acteur>> call, Response<List<Acteur>> response) {
                if (response.isSuccessful()){
                    showActors(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Acteur>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    private void showActors(List<Acteur> actors) {
        TableLayout actorsTable = (TableLayout) findViewById(R.id.table);
        TableRow tableRow = new TableRow(this);
        ViewGroup.LayoutParams tableLayout = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        actorsTable.addView(tableRow, tableLayout);
        tableRow.setLayoutParams(new TableRow.LayoutParams(actors.size()));

        for (int i=0; i < 5; i++) { //colonnes
            TextView text = new TextView(this);
            switch (i){
                case 0:
                    text.setText("ID");
                    break;
                case 1:
                    text.setText("Nom");
                    break;
                case 2:
                    text.setText("Prénom");
                    break;
                case 3:
                    text.setText("Date naiss");
                    break;
                case 4:
                    text.setText("Date décès");
                    break;
            }
            text.setTextColor(Color.parseColor("#260096"));
            text.setTextSize(15);
            text.setGravity(Gravity.CENTER);
            tableRow.addView(text);
        }

        for (int i=0; i < actors.size(); i++){
            tableRow = new TableRow(this);
            actorsTable.addView(tableRow, tableLayout);
            for (int j=0; j < 5; j++){
                TextView text = new TextView(this);
                switch (j) {
                    case 0:
                        text.setText(String.valueOf(actors.get(i).getId()));
                        break;
                    case 1:
                        text.setText(String.valueOf(actors.get(i).getNom()));
                        break;
                    case 2:
                        text.setText(String.valueOf(actors.get(i).getPrenom()));
                        break;
                    case 3:
                        text.setText(String.valueOf(actors.get(i).getDateNaiss()));
                        break;
                    case 4:
                        String dateDeces = String.valueOf(actors.get(i).getDateDeces());
                        if (!dateDeces.equals("null")) {
                            text.setText(dateDeces);
                        }
                        break;
                }

                tableRow.setWeightSum(5);
                tableRow.addView(text);
                text.setGravity(Gravity.CENTER);

                tableRow.setPadding(0,20,0,20);
            }
        }
    }
}
