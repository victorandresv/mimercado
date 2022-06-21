package cl.mi.mercado.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import cl.mi.mercado.R;
import cl.mi.mercado.helpers.DialogsHelper;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        context = this;
        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setSubtitle(getResources().getString(R.string.create_account));

        TextInputEditText email = findViewById(R.id.email);
        TextInputEditText password = findViewById(R.id.password);

        findViewById(R.id.btnSignup).setOnClickListener(view -> {
            if(email.getText().toString().contains("@")){
                if(password.getText().toString().length() >= 8){
                    mAuth.createUserWithEmailAndPassword(
                            email.getText().toString(),
                            password.getText().toString()
                    ).addOnSuccessListener(authResult -> {
                        finish();
                        startActivity(new Intent(context, SignupAditionalDataActivity.class));
                    }).addOnFailureListener(e -> {
                        switch(e.getMessage()){
                            case "The email address is already in use by another account.":
                                DialogsHelper.Alert(context, "Error", getResources().getText(R.string.signup_recovery_message).toString());
                                break;
                            default:
                                DialogsHelper.Alert(context, "Error", e.getMessage());
                                break;
                        }
                        Log.e("ERROR", e.getMessage());
                    });
                } else {
                    password.setError(getResources().getString(R.string.missed_password));
                }
            } else {
                email.setError(getResources().getString(R.string.missed_email));
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}