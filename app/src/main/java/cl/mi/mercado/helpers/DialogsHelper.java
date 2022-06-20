package cl.mi.mercado.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cl.mi.mercado.R;
import cl.mi.mercado.interfaces.AddProductToCart;
import cl.mi.mercado.models.ProductModel;

public class DialogsHelper {

    public static void Alert(Context context, String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(context.getResources().getString(R.string.accept), (dialog, which) -> dialog.cancel());
        alert.create();
        alert.show();
    }

    public static void ProductToCart(Activity activity, ProductModel data, AddProductToCart call){
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_product_to_cart, null);

        TextView sku = dialogView.findViewById(R.id.sku);
        TextView measure = dialogView.findViewById(R.id.measure);
        TextView price = dialogView.findViewById(R.id.price);
        TextView quantity = dialogView.findViewById(R.id.quantity);
        TextView total = dialogView.findViewById(R.id.total);

        data.setQuantity(1);
        sku.setText(data.getSku());
        measure.setText(data.getMeasure());
        price.setText("$"+data.getPrice());
        quantity.setText("1");
        total.setText("$Total: "+(data.getQuantity()*data.getPrice()));

        dialogView.findViewById(R.id.btnLess).setOnClickListener(view -> {
            if(data.getQuantity() > 1){
                data.setQuantity(data.getQuantity()-1);
                quantity.setText(String.valueOf(data.getQuantity()));
                total.setText("$Total: "+(data.getQuantity()*data.getPrice()));
            }
        });

        dialogView.findViewById(R.id.btnMore).setOnClickListener(view -> {
            data.setQuantity(data.getQuantity()+1);
            quantity.setText(String.valueOf(data.getQuantity()));
            total.setText("$Total: "+(data.getQuantity()*data.getPrice()));
        });

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setNegativeButton(activity.getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
        dialogBuilder.setTitle(data.getName());
        dialogBuilder.create();
        AlertDialog dialog = dialogBuilder.show();

        dialogView.findViewById(R.id.btnAdd).setOnClickListener(view -> {
            dialog.dismiss();
            call.Add(data);
        });
    }

}
