package cl.mi.mercado.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

import cl.mi.mercado.R;
import cl.mi.mercado.helpers.DialogsHelper;
import cl.mi.mercado.helpers.SessionHelper;
import cl.mi.mercado.interfaces.SignupFirestoreCallback;

public class SignupAditionalDataActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_aditional_data);

        context = this;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        getSupportActionBar().setSubtitle(getResources().getString(R.string.aditional_data));

        TextInputEditText firstname = findViewById(R.id.firstname);
        TextInputEditText lastname = findViewById(R.id.lastname);
        TextInputEditText storename = findViewById(R.id.storename);

        findViewById(R.id.btnSignup).setOnClickListener(view -> {
            if(!firstname.getText().toString().equals("")){
                if(!lastname.getText().toString().equals("")){
                    if(!storename.getText().toString().equals("")){

                        updateOrCreateData(
                                currentUser.getEmail(),
                                firstname.getText().toString(),
                                lastname.getText().toString(),
                                storename.getText().toString(),
                                () -> {
                                    UserProfileChangeRequest.Builder upcr = new UserProfileChangeRequest.Builder();
                                    upcr.setDisplayName(firstname.getText().toString()+" "+lastname.getText().toString());
                                    try {
                                        currentUser.updateProfile(upcr.build())
                                                .addOnCompleteListener(task -> {
                                                    SessionHelper.addData(context, "MarketId", currentUser.getEmail());
                                                    SessionHelper.addData(context, "Storename", storename.getText().toString());
                                                    finish();
                                                    startActivity(new Intent(context, HomeActivity.class));
                                                });

                                    } catch (Exception err){
                                        Log.e("Error", err.getMessage());
                                        DialogsHelper.Alert(context, "Error", err.getMessage());
                                    }
                                }
                        );
                    } else {
                        storename.setError(getResources().getString(R.string.missed_storename));
                    }
                } else {
                    lastname.setError(getResources().getString(R.string.missed_lastname));
                }
            } else {
                firstname.setError(getResources().getString(R.string.missed_firstname));
            }
        });
    }


    private void updateOrCreateData(String email, String firstname, String lastname, String storename, SignupFirestoreCallback cb){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.enableNetwork().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Map<String, Object> data = new HashMap<>();
                data.put("email", email);
                data.put("firstname", firstname);
                data.put("lastname", lastname);
                data.put("storename", storename);
                data.put("plan", "Free");

                db.collection("markets")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                int count = 0;
                                String documentId = "";
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    documentId = document.getId();
                                    count++;
                                }
                                data.put("created_at", Timestamp.now());
                                if(count > 0){
                                    // Update market
                                    db.collection("markets").document(documentId).set(data);
                                } else {
                                    // Create market
                                    db.collection("markets").add(data);
                                }
                                cb.Ok();
                            } else {
                                DialogsHelper.Alert(context, "Error", getResources().getString(R.string.unknown_error));
                            }
                        }).addOnFailureListener(err -> DialogsHelper.Alert(context, "Error", err.getMessage()));
            }
        });
    }
}