package cl.mi.mercado.helpers;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import cl.mi.mercado.interfaces.FirestoreSingleStore;
import cl.mi.mercado.models.MarketModel;

public class FirestoreHelper {

    public static void GetStoreByEmail(String email, FirestoreSingleStore cb){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("markets")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    MarketModel data = new MarketModel();
                    for(DocumentSnapshot item: queryDocumentSnapshots.getDocuments()){
                        try {
                            data.setId(item.getId());
                            data.setEmail(item.getData().get("plan").toString());
                            data.setFirstname(item.getData().get("firstname").toString());
                            data.setLastname(item.getData().get("lastname").toString());
                            data.setCreated_at((Timestamp) item.getData().get("created_at"));
                            data.setPlan(item.getData().get("plan").toString());
                            data.setPlan_from((Timestamp) item.getData().get("plan_start_date"));
                            data.setStorename(item.getData().get("storename").toString());
                            data.setPlan_to((Timestamp) item.getData().get("plan_end_date"));
                        } catch (Exception ignored){}
                    }
                    cb.Ok(data);
                })
                .addOnFailureListener(cb::Error);
    }
}
