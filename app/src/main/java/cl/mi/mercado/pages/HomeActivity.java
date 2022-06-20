package cl.mi.mercado.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cl.mi.mercado.R;

public class HomeActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;

        getSupportActionBar().setSubtitle(getResources().getString(R.string.home));

        findViewById(R.id.btnNewSell).setOnClickListener(view -> startActivity(new Intent(context, CartActivity.class)));
    }
}