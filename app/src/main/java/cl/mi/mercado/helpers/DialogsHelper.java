package cl.mi.mercado.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;

import cl.mi.mercado.R;
import cl.mi.mercado.interfaces.FirestoreSingleProduct;
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

    public static void ProductCreate(Activity activity, FirestoreSingleProduct call){
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
            call.Ok(product);
        });
        dialogBuilder.setPositiveButton(activity.getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
        dialogBuilder.setTitle(activity.getResources().getString(R.string.create_new_product));
        dialogBuilder.create();
        AlertDialog dialog = dialogBuilder.show();

        BeepManager beepManager = new BeepManager(activity);
        final String[] lastStringScanned = {""};

        DecoratedBarcodeView barcodeView = (DecoratedBarcodeView) dialogView.findViewById(R.id.barcode_scanner);
        Collection<BarcodeFormat> formats = Arrays.asList(
                BarcodeFormat.EAN_8,
                BarcodeFormat.EAN_13,
                BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E,
                BarcodeFormat.UPC_EAN_EXTENSION
        );
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.resume();
        barcodeView.decodeContinuous(result -> {
            if(!lastStringScanned[0].equals(result.getText())){
                barcodeView.pause();
                beepManager.playBeepSound().setOnCompletionListener(mediaPlayer -> {
                    lastStringScanned[0] = result.getText();
                    sku.setText(result.getText());
                    barcodeView.resume();

                    FirestoreOnlineHelper.GetProductBySku(activity.getApplicationContext(), result.getText(), call);
                });
            }
        });

    }

}
