package cl.mi.mercado.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import cl.mi.mercado.R;
import cl.mi.mercado.adapters.SaleRecyclerViewAdapter;
import cl.mi.mercado.helpers.SessionHelper;
import cl.mi.mercado.pages.CartActivity;
import cl.mi.mercado.pages.SaleModel;

public class SalesFragment extends Fragment {

    public SalesFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales, container, false);
        view.findViewById(R.id.btnNewSell).setOnClickListener(v -> startActivity(new Intent(getContext(), CartActivity.class)));

        ArrayList<SaleModel> list = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.disableNetwork();

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SaleRecyclerViewAdapter adapter = new SaleRecyclerViewAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        db.collection("markets")
                .document(SessionHelper.getData(getContext(), "MarketId"))
                .collection("sales")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for(DocumentSnapshot item: queryDocumentSnapshots.getDocuments()){
                        list.add(
                                new SaleModel(
                                    item.getId(),
                                    item.getData().get("client").toString(),
                                    (Timestamp)item.getData().get("created_at"),
                                    item.getData().get("status").toString()
                                )
                        );
                    }
                    adapter.notifyDataSetChanged();
                });


        return view;
    }
}