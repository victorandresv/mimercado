package cl.mi.mercado.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import cl.mi.mercado.R;
import cl.mi.mercado.helpers.DialogsHelper;
import cl.mi.mercado.helpers.FirestoreOnlineHelper;
import cl.mi.mercado.helpers.SessionHelper;
import cl.mi.mercado.interfaces.FirestoreSingleStore;
import cl.mi.mercado.models.MarketModel;

public class SigninActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        context = this;
        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setSubtitle(getResources().getString(R.string.start_session));

        TextInputEditText email = findViewById(R.id.email);
        TextInputEditText password = findViewById(R.id.password);

        findViewById(R.id.btnSignin).setOnClickListener(view -> {
            if(email.getText().toString().contains("@")){
                if(password.getText().toString().length() >= 8){
                    mAuth.signInWithEmailAndPassword(
                            email.getText().toString(),
                            password.getText().toString()
                    ).addOnSuccessListener(authResult -> {

                        FirestoreOnlineHelper.GetStoreByEmail(email.getText().toString(), new FirestoreSingleStore() {
                            @Override
                            public void Ok(MarketModel data) {
                                SessionHelper.addData(context, "MarketId", data.getId());
                                SessionHelper.addData(context, "Storename", data.getStorename());
                                finishAffinity();
                                startActivity(new Intent(context, HomeActivity.class));
                            }

                            @Override
                            public void Error(Exception e) {
                                DialogsHelper.Alert(context, "Error", e.getMessage());
                            }
                        });

                    }).addOnFailureListener(e -> {
                        switch(e.getMessage()){
                            case "There is no user record corresponding to this identifier. The user may have been deleted.":
                                DialogsHelper.Alert(context, "Error", getResources().getText(R.string.signin_no_account_message).toString());
                                break;
                            case "The password is invalid or the user does not have a password.":
                                DialogsHelper.Alert(context, "Error", getResources().getText(R.string.signin_invalid_pass_message).toString());
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