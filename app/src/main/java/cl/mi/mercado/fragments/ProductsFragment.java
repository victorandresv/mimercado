package cl.mi.mercado.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.mi.mercado.R;
import cl.mi.mercado.helpers.DialogsHelper;
import cl.mi.mercado.interfaces.ProductCallback;
import cl.mi.mercado.models.ProductModel;

public class ProductsFragment extends Fragment {
    public ProductsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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