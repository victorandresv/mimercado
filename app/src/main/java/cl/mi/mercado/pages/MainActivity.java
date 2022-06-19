package cl.mi.mercado.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cl.mi.mercado.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        } else {
            setContentView(R.layout.activity_main);

            findViewById(R.id.btnSignup).setOnClickListener(view -> {
                startActivity(new Intent(context, SignupActivity.class));
            });
        }
    }
}