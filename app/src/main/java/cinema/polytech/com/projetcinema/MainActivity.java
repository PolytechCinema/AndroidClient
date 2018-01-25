package cinema.polytech.com.projetcinema;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cinema.polytech.com.projetcinema.fragments.HomepageFragment;

public class MainActivity extends AppCompatActivity {

    public static final String CINEMA_BASE_URL = "http://192.168.42.98:8080";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.frag) != null) {
            final HomepageFragment homePage = new HomepageFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.frag, homePage).commit();
        }
    }
}
