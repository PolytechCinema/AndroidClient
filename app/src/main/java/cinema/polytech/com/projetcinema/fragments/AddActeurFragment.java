package cinema.polytech.com.projetcinema.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cinema.polytech.com.projetcinema.R;
import cinema.polytech.com.projetcinema.metier.Acteur;

/**
 * Created by coloc on 25/01/2018.
 */

public class AddActeurFragment extends AbstractAddEditActeurFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeView(getActivity().findViewById(R.id.deleteActor));
        return inflater.inflate(R.layout.edit_acteur, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Button requestButton = getActivity().findViewById(R.id.send);
        requestButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Acteur acteur = createActeur();
                sendAddRequest(acteur);
            }
        });

    }



}
