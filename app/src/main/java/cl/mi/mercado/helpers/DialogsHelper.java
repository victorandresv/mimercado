package cl.mi.mercado.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import cl.mi.mercado.R;
import cl.mi.mercado.interfaces.ProductCallback;
import cl.mi.mercado.interfaces.SingleCallback;
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

    public static void Alert(Context context, String title, String message, SingleCallback cb){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(context.getResources().getString(R.string.accept), (dialog, which) -> {
            cb.Ok();
            dialog.cancel();
        });
        alert.create();
        alert.show();

    }

    public static void ProductToCart(Activity activity, ProductModel data, ProductCallback call){
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

    public static void ProductCreate(Activity activity, ProductCallback call){
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_product_create, null);

        TextView sku = dialogView.findViewById(R.id.sku);
        TextView name = dialogView.findViewById(R.id.name);
        TextView measure = dialogView.findViewById(R.id.measure);
        TextView price_buy = dialogView.findViewById(R.id.price_buy);
        TextView price_sell = dialogView.findViewById(R.id.price_sell);
        TextView quantity = dialogView.findViewById(R.id.quantity);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setNegativeButton(activity.getResources().getString(R.string.create_product), (dialogInterface, i) -> {
            dialogInterface.dismiss();
            ProductModel product = new ProductModel(
                    name.getText().toString(),
                    sku.getText().toString(),
                    measure.getText().toString(),
                    Double.parseDouble(price_sell.getText().toString()),
                    Double.parseDouble(price_buy.getText().toString()),
                    Integer.parseInt(quantity.getText().toString())
            );
            call.Add(product);
        });
        dialogBuilder.setPositiveButton(activity.getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
        dialogBuilder.setTitle(activity.getResources().getString(R.string.create_new_product));
        dialogBuilder.create();
        AlertDialog dialog = dialogBuilder.show();


        DecoratedBarcodeView barcodeView = (DecoratedBarcodeView) dialogView.findViewById(R.id.barcode_scanner);
        barcodeView.resume();
        barcodeView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                // Do whatever you want with the result
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
                BarcodeCallback.super.possibleResultPoints(resultPoints);
            }
        });

    }

}
