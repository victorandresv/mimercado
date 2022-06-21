package cl.mi.mercado.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import cl.mi.mercado.R;
import cl.mi.mercado.pages.SaleModel;

public class SaleRecyclerViewAdapter extends RecyclerView.Adapter<SaleRecyclerViewAdapter.ViewHolder> {

    private ArrayList<SaleModel> mData;
    private LayoutInflater mInflater;

    public SaleRecyclerViewAdapter(Context context, ArrayList<SaleModel> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_sale_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.client.setText(mData.get(position).getClient());

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a", Locale.getDefault());
        holder.timestamp.setText(sdf.format(mData.get(position).getCreated_at().toDate()));
    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView client;
        TextView timestamp;

        ViewHolder(View itemView) {
            super(itemView);
            client = itemView.findViewById(R.id.client);
            timestamp = itemView.findViewById(R.id.timestamp);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}


