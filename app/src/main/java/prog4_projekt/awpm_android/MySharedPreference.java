package prog4_projekt.awpm_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by florianduenow on 01.05.16.
 */
public class MySharedPreference {

    public static final String valueFile = "MySavedData";
    public static final String tokenValue = "Token";
    public static boolean isLoged;
    public static boolean hasToken;
    public MySharedPreference(){
        super();
    }

    public void saveStringToken(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(valueFile, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(tokenValue, text);


        editor.commit();
    }
    public void saveBooleanIsLoged(Context context, boolean status){
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(valueFile, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean(String.valueOf(isLoged), status);
        editor.commit();

    }
    public void saveBooleanHasToken (Context context, boolean status){
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(valueFile, Context.MODE_PRIVATE);
        editor = settings.edit();
        Log.i("200", "Token status saved = "+ status);
        editor.putBoolean(String.valueOf(hasToken), status);
        editor.commit();

    }
    public boolean getBooleanHasToken(Context context){
        SharedPreferences settings;
        boolean status;
        settings = context.getSharedPreferences(valueFile, Context.MODE_PRIVATE);
        status = settings.getBoolean(String.valueOf(hasToken), false);
        Log.i("200", "Token status returned = "+ status);

        return status;
    }

    public String getStringToken(Context context) {
        SharedPreferences settings;
        String text;

        settings = context.getSharedPreferences(valueFile, Context.MODE_PRIVATE);
        text = settings.getString(tokenValue, null);
        return text;
    }
    public boolean getBoleanIsLoged(Context context){
        SharedPreferences settings;
        boolean status;

        settings = context.getSharedPreferences(valueFile, Context.MODE_PRIVATE);
        status = settings.getBoolean(String.valueOf(isLoged), false);
        return status;
    }



    public void removeValue(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(valueFile, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(tokenValue);
        editor.commit();
    }
}
