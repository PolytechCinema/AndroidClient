package cinema.polytech.com.projetcinema.fragments;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cinema.polytech.com.projetcinema.R;

/**
 * Created by coloc on 25/01/2018.
 */

public class AddActeurFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_acteur, container, false);
    }

}
