package cl.mi.mercado.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionHelper {

    public static void addData(Context context, String Key, String Value){
        SharedPreferences Session = context.getSharedPreferences("Session Mi Mercado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Session.edit();
        editor.putString(Key, Value);
        editor.apply();
    }

    public static String getData(Context context, String Key){
        SharedPreferences Session = context.getSharedPreferences("Session Mi Mercado", Context.MODE_PRIVATE);
        return Session.getString(Key, "");
    }

}
