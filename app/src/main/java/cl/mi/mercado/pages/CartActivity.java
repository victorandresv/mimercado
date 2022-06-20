
package cl.mi.mercado.pages;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cl.mi.mercado.R;
import cl.mi.mercado.helpers.DialogsHelper;
import cl.mi.mercado.helpers.SessionHelper;
import cl.mi.mercado.interfaces.AddProductToCart;
import cl.mi.mercado.models.ProductModel;

public class CartActivity extends AppCompatActivity {

    private Context context;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        context = this;

        db = FirebaseFirestore.getInstance();
        db.disableNetwork();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setSubtitle(getResources().getString(R.string.state_account));

        findViewById(R.id.btnScan).setOnClickListener(view -> {
            //barcodeLauncher.launch(new ScanOptions());
            DialogsHelper.ProductToCart(this, new ProductModel("Producto", "000000000000", "1k", 1500), new AddProductToCart() {
                @Override
                public void Add(ProductModel data) {
                    addItemToCart(data);
                }
            });
        });

    }

    private void addItemToCart(ProductModel cart){
        Map<String, Object> data = new HashMap<>();
        data.put("name", cart.getName());
        data.put("measure", cart.getMeasure());
        data.put("price", cart.getPrice());
        data.put("quantity", cart.getQuantity());
        data.put("sku", cart.getSku());



        if(SessionHelper.getData(context, "MarketId").equals("")){
            SessionHelper.addData(context, "MarketId", "p3kHSmxekZF5WKCOvJbM");
        }
        if(SessionHelper.getData(context, "SaleId").equals("")){
            Map<String, Object> sale = new HashMap<>();
            sale.put("created_at", new Timestamp(new Date()));
            sale.put("status", "DRAFT");
            sale.put("client", "Pepe");
            db.collection("markets")
                    .document(SessionHelper.getData(context, "MarketId"))
                    .collection("sales")
                    .add(sale)
                    .addOnSuccessListener(documentReference -> {
                        SessionHelper.addData(context, "SaleId", documentReference.getId());
                        db.collection("markets")
                                .document(SessionHelper.getData(context, "MarketId"))
                                .collection("sales")
                                .document(SessionHelper.getData(context, "SaleId"))
                                .collection("products")
                                .add(data);
                    })
                    .addOnFailureListener(e -> DialogsHelper.Alert(context, "Error", e.getMessage()));

        } else {
            db.collection("markets")
                    .document(SessionHelper.getData(context, "MarketId"))
                    .collection("sales")
                    .document(SessionHelper.getData(context, "SaleId"))
                    .collection("products")
                    .add(data);
        }
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
        result -> {
            if(result.getContents() == null) {
                Toast.makeText(context, "Escaneo de codigo de barras cancelado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        });

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