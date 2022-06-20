package cl.mi.mercado.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.mi.mercado.R;
import cl.mi.mercado.pages.CartActivity;

public class SalesFragment extends Fragment {

    public SalesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales, container, false);
        view.findViewById(R.id.btnNewSell).setOnClickListener(v -> startActivity(new Intent(getContext(), CartActivity.class)));

        return view;
    }
}