package cl.mi.mercado.fragments;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

import cl.mi.mercado.R;
import cl.mi.mercado.helpers.DialogsHelper;
import cl.mi.mercado.interfaces.ProductCallback;
import cl.mi.mercado.models.ProductModel;

public class ProductsFragment extends Fragment {
    public ProductsFragment() { }

    public DecoratedBarcodeView barcodeView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products, container, false);

        view.findViewById(R.id.btnAdd).setOnClickListener(view1 -> {
            DialogsHelper.ProductCreate(getActivity(), new ProductCallback() {
                @Override
                public void Add(ProductModel data) {
                    Log.e("ERR", data.toString());
                }
            });
        });

        return view;
    }
}