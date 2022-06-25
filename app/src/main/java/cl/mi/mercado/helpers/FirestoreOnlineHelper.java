package cl.mi.mercado.helpers;

import android.content.Context;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import cl.mi.mercado.interfaces.FirestoreSingleProduct;
import cl.mi.mercado.interfaces.FirestoreSingleStore;
import cl.mi.mercado.models.MarketModel;
import cl.mi.mercado.models.ProductModel;

public class FirestoreOnlineHelper {

    public static void GetStoreByEmail(String email, FirestoreSingleStore cb){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.enableNetwork().addOnSuccessListener(unused -> db.collection("markets")
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
                            data.setStorename(item.getData().get("storename").toString());
                        } catch (Exception ignored){}
                    }
                    cb.Ok(data);
                })
                .addOnFailureListener(cb::Error));

    }

    public static void GetProductBySku(Context context, String sku, FirestoreSingleProduct cb){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.enableNetwork().addOnSuccessListener(unused -> {
            db.collection("markets")
                    .document(SessionHelper.getData(context, "MarketId"))
                    .collection("stock")
                    .whereEqualTo("sku", sku)
                    .get()
                    .addOnSuccessListener(result -> {
                        ProductModel productModel = new ProductModel();
                        for(DocumentSnapshot item: result.getDocuments()){
                            productModel.setSku(sku);
                            productModel.setPrice(Double.parseDouble(item.get("price").toString()));
                            productModel.setMeasure(item.get("measure").toString());
                            productModel.setPrice_buy(Double.parseDouble(item.get("price_buy").toString()));
                            productModel.setName(item.get("name").toString());
                            productModel.setQuantity(Integer.parseInt(item.get("quantity").toString()));
                            productModel.setId(item.getId());
                        }
                        cb.Ok(productModel);
                    });
        }).addOnFailureListener(cb::Error);
    }
}
