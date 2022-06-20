package cl.mi.mercado.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cl.mi.mercado.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setSubtitle("Home");
    }
}