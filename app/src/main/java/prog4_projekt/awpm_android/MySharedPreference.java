package prog4_projekt.awpm_android;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by florianduenow on 01.05.16.
 */
public class MySharedPreference {

    public static final String tokenValue = "Token";
    public static final String isLoged = "isLoged";


    public static void saveStringToken(SharedPreferences pref, String text) {

        SharedPreferences.Editor editor = pref.edit();

        editor.putString(tokenValue, text);
        editor.commit();

        Log.i("002", "savedStringToken= " + text);


    }

    public static void saveBooleanIsLoged(SharedPreferences pref, boolean status) {

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(isLoged, status);

        editor.commit();

    }

    public static String getStringToken(SharedPreferences pref) {

        return pref.getString(tokenValue, null);
    }

    public static boolean getBooleanIsLoged(SharedPreferences pref) {

        return pref.getBoolean(isLoged, false);

    }
}