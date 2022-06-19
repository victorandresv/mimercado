package cl.mi.mercado.helpers;

import android.app.AlertDialog;
import android.content.Context;

import cl.mi.mercado.R;

public class DialogsHelper {

    public static void Alert(Context context, String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(context.getResources().getString(R.string.accept), (dialog, which) -> dialog.cancel());
        alert.create();
        alert.show();
    }

}
