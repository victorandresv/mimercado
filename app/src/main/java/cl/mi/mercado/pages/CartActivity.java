
package cl.mi.mercado.pages;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import cl.mi.mercado.R;
import cl.mi.mercado.helpers.DialogsHelper;
import cl.mi.mercado.interfaces.AddProductToCart;
import cl.mi.mercado.models.ProductModel;

public class CartActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        context = this;
        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setSubtitle(getResources().getString(R.string.state_account));

        findViewById(R.id.btnScan).setOnClickListener(view -> {
            //barcodeLauncher.launch(new ScanOptions());

            DialogsHelper.ProductToCart(this, new ProductModel("Producto", "000000000000", "1k", 1500), new AddProductToCart() {
                @Override
                public void Add(ProductModel data) {
                    Log.e("DATA", data.toString());
                }
            });
        });

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