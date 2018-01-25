package cinema.polytech.com.projetcinema.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

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

public class HomepageFragment extends Fragment implements View.OnClickListener
{
    private Button ajoutActeur;
    private HashMap<Button, Integer> editButtons;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle saveInstanceState)
    {
        View v = layoutInflater.inflate(R.layout.homepage, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        editButtons = new HashMap<>();
        Button addActeur = getActivity().findViewById(R.id.ajoutActeur);
        Button.OnClickListener answerListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddActeurFragment ffrag = new AddActeurFragment();
                fragmentTransaction.replace(R.id.frag, ffrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
        addActeur.setOnClickListener(answerListener);
        showTable();
    }

    private void showTable() {
        CinemaWS cinemaWS = new Retrofit.Builder().baseUrl(MainActivity.CINEMA_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(CinemaWS.class);
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
        TableLayout actorsTable = (TableLayout) getActivity().findViewById(R.id.table);
        TableRow tableRow = new TableRow(getActivity());
        ViewGroup.LayoutParams tableLayout = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        actorsTable.addView(tableRow, tableLayout);
        tableRow.setLayoutParams(new TableRow.LayoutParams(actors.size()));

        for (int i=0; i < 5; i++) { //colonnes
            TextView text = new TextView(getActivity());
            switch (i){
                case 0:
                    text.setText(R.string.id);
                    break;
                case 1:
                    text.setText(R.string.name);
                    break;
                case 2:
                    text.setText(R.string.firstname);
                    break;
                case 3:
                    text.setText(R.string.birthday);
                    break;
                case 4:
                    text.setText(R.string.death);
                    break;
            }
            text.setTextColor(Color.parseColor("#5b1865"));
            text.setTextSize(15);
            text.setGravity(Gravity.CENTER);
            tableRow.addView(text);
        }

        for (int i=0; i < actors.size(); i++){
            tableRow = new TableRow(getActivity());
            final int id = actors.get(i).getId();
            tableRow.setId(id);
            tableRow.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    EditActeurFragment ffrag = new EditActeurFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    ffrag.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frag, ffrag);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            actorsTable.addView(tableRow, tableLayout);
            for (int j=0; j < 5; j++){
                TextView text = new TextView(getActivity());
                switch (j) {
                    case 0:
                        text.setText(String.valueOf(id));
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

    @Override
    public void onClick(View view) {
        showTable();
    }
}